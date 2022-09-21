package com.obeast.oss.service.impl;

import cn.hutool.core.util.StrUtil;
import com.obeast.oss.config.MinioConfig;
import com.obeast.oss.domain.MergeShardArgs;
import com.obeast.oss.domain.MinioTemplateResult;
import com.obeast.oss.domain.PageObjects;
import com.obeast.oss.domain.ResponseEntry;
import com.obeast.oss.enumration.ShardFileStatusCode;
import com.obeast.oss.service.SseEmitterService;
import com.obeast.oss.template.MinioTemplate;
import com.obeast.oss.utils.PageQueryUtils;
import com.obeast.oss.utils.ShardFileUtils;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.obeast.oss.dao.OssDao;
import com.obeast.oss.entity.OssEntity;
import com.obeast.oss.excel.OssExcel;
import com.obeast.oss.service.OssService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author obeast-dragon
 * Date 2022-09-21 15:19:09
 * @version 1.0
 * Description:
 */
@Service("ossService")
public class OssServiceImpl extends ServiceImpl<OssDao, OssEntity> implements OssService {


    @Autowired
    private MinioTemplate minioTemplate;

    @Autowired
    private MinioConfig minioConfig;


    @Autowired
    OssDao ossDao;

    @Autowired
    SseEmitterService sseEmitterService;

    private static final Logger log = LoggerFactory.getLogger(OssServiceImpl.class);

    private static ExecutorService executorService;

    /**
     * @return ExecutorService 线程池
     * description:因此IO密集型的任务，可大致设为： N(threads) = 2N(cpu) ->我的cpu是4核
     * @author wxl
     * date 2022/7/20 23:41
     **/
    private static ExecutorService createThreadPool(int sizePool) {
        return new ThreadPoolExecutor(
                sizePool,
                sizePool,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024));
    }


    /**
     * @param file     multipartFile
     * @param res      全局共享消息体
     * @param fileType 文件类型
     * description: 大文件上传大于5MB分片文件上传
     * @author wxl
     * date 2022/7/20 23:51
     **/
    @SneakyThrows
    @Override
    public OssEntity uploadShard(MultipartFile file, ResponseEntry res, String fileType, String userUuid) {
        executorService = createThreadPool(8);
        // 上传过程中出现异常，状态码设置为50000
        boolean stopStatus = true;
        if (file == null) {
            res.get(userUuid).put("status", ShardFileStatusCode.FAILURE);
            throw new Exception(ShardFileStatusCode.FAILURE.getMessage());
        }
        Long fileSize = file.getSize();
        String md5BucketName = userUuid + "-" + StrUtil.uuid();
        String fileName = file.getOriginalFilename();
        res.get(userUuid).put("md5BucketName", md5BucketName);
        //分片大小
        long shardSize = 5 * 1024 * 1024L;
        //开始分片
        List<InputStream> inputStreams = ShardFileUtils.splitMultipartFileInputStreams(file, shardSize);
        long shardCount = inputStreams.size(); //总片数
        //封装合并参数
        MergeShardArgs mergeShardArgs = new MergeShardArgs((int) shardCount, fileName, md5BucketName, fileType, fileSize);
        boolean fileExists = isFileExists(md5BucketName);
        boolean bucketExists = minioTemplate.bucketExists(md5BucketName);
        //当前文件不存在DB和minio  可以正常分片上传
        if (!fileExists && !bucketExists) {
            try {
                //创建临时桶
                minioTemplate.makeBucket(md5BucketName);
                uploadJob(shardCount, inputStreams, res, stopStatus, md5BucketName, fileName, userUuid);
                //开始合并
                OssEntity OssEntity = mergeShard(mergeShardArgs, userUuid, res);
                //上传完成清空当前用户数据
                res.get(userUuid).clear();
                log.info("文件上传成功 {} ", file.getOriginalFilename());
                res.get(userUuid).put("status", ShardFileStatusCode.ALL_CHUNK_SUCCESS.getCode());
                return OssEntity;
            } catch (Exception e) {
                log.error("分片合并失败：{}", e.getMessage());
                throw new Exception("分片合并失败");
            }
        }

        /*
          如果文件存在;
           1、存在DB minio == null 上传完成秒传
           2、存在minio  DB == null
          先看临时桶在不在
           1、在;断点上传
           2、在;没合并
          */
        else if (fileExists && !bucketExists) {
            //1、存在DB minio == null
            ossDao.delete(new QueryWrapper<OssEntity>()
                    .eq("md5_file_name", md5BucketName)
            );
            throw new Exception("请重新上传文件");

        } else if (!fileExists) {
            //2、存在minio  DB == null
//         *  1、在;断点上传
//         *  2、在;没合并
            List<String> objectNames = minioTemplate.listObjectNames(md5BucketName);
            OssEntity OssEntity;
            //临时桶在; 断点上传
            if (objectNames.size() == shardCount) {
                //设置百分比
                res.get(userUuid).put("uploadPercent", 100);
                log.info("uploadPercent:{}", 100);
                //设置上传文件大小
                res.get(userUuid).put("uploadSize", fileSize);
                //没有合并: 合并秒传
                OssEntity = mergeShard(mergeShardArgs, userUuid, res);
                log.info("uploadSize：{}", fileSize);
                log.info("{} 秒传成功", fileName);
                //上传完成清空当前用户数据
                res.get(userUuid).clear();
            } else {
                //断点上传
                log.info("开始断点上传>>>>>>");
                List<String> containStr = containList(objectNames, shardCount);
                log.info("上传过的分片:" + containStr);
                CountDownLatch countDownLatch = new CountDownLatch(containStr.size());
                try {
                    log.info("开始断点分片上传:" + fileName);
                    for (String s : containStr) {
                        stopStatus = (boolean) res.get("userUuid").get("stopStatus");
                        if (stopStatus) {
                            int c = Integer.parseInt(s);
                            executorService.execute(new BranchThread(inputStreams.get(c - 1), md5BucketName, c, res, countDownLatch, shardCount, stopStatus, userUuid, minioTemplate, sseEmitterService));
                        } else {
                            executorService.shutdown();
                            break;
                        }
                    }
                } catch (Exception e) {
                    log.error("断点上传出现异常{}", e.getMessage());
                    throw new Exception("断点上传出现异常");
                } finally {
                    //关闭线程池
                    executorService.shutdown();
                }
                countDownLatch.await();
                log.info("所有分片上传完成");
                res.get(userUuid).put("status", ShardFileStatusCode.ALL_CHUNK_UPLOAD_SUCCESS.getCode());

                OssEntity = mergeShard(mergeShardArgs, userUuid, res);
                log.info("文件上传成功：{} ", fileName);
                //上传完成清空当前用户数据
                res.get(userUuid).clear();
            }
            res.get(userUuid).put("status", ShardFileStatusCode.ALL_CHUNK_MERGE_SUCCESS.getCode());
            return OssEntity;

        } else {
            //出现异常
            log.error("出现异常: {}", ShardFileStatusCode.FOUND.getMessage());
            throw new Exception("文件上传出现异常");
        }
    }

    /**
     * @param mergeShardArgs 合并文件参数实体
     * @param userUuid       user id
     * @return OssEntity
     * description:
     * @author wxl
     * date 2022/7/22 22:49
     **/
    @Transactional
    @Override
    public OssEntity mergeShard(MergeShardArgs mergeShardArgs, String userUuid, ResponseEntry res) {
        Integer shardCount = mergeShardArgs.getShardCount();
        String fileName = (String) res.get(userUuid).get("fileName");
        String md5 = mergeShardArgs.getMd5();
        try {
            List<String> objectNameList = minioTemplate.listObjectNames(md5);
            //查询的服务器的分片和传入的分片不同
            if (shardCount != objectNameList.size()) {
                // 失败
                log.error("服务器的分片{}和传入的分片不同{}", shardCount, objectNameList.size());
                throw new Exception("服务器的分片和传入的分片不同");
            } else {
                // 开始合并请求
                String targetBucketName = minioConfig.getBucketName();
                //拼接合并之后的文件名称
                String objectName = userUuid + "-" + fileName;
                //合并
                minioTemplate.composeObject(md5, targetBucketName, objectName);

                log.info("桶：{} 中的分片文件，已经在桶：{},文件 {} 合并成功", md5, targetBucketName, objectName);
                String url = minioTemplate.getPresignedObjectUrl(targetBucketName, objectName);
                // 合并成功之后删除对应的临时桶
                minioTemplate.removeBucket(md5, true);
                log.info("删除桶 {} 成功", md5);
                // 存入DB中
                OssEntity OssEntity = new OssEntity();
                OssEntity.setMd5FileName(md5);
                OssEntity.setUrl(url);
                OssEntity.setCreateTime(new Date());
                OssEntity.setUpdateTime(new Date());
                ossDao.insert(OssEntity);
                log.info("文件合并成{}并存入DB", OssEntity);
                return OssEntity;
            }
        } catch (Exception e) {
            // 失败
            log.error("合并失败:{}", e.getMessage());
        }
        return null;

    }

    /**
     * 根据文件大小和文件的md5校验文件是否存在
     * 暂时使用Redis实现，后续需要存入数据库
     * 实现秒传接口
     *
     * @param md5 文件的md5
     * @return 操作是否成功
     */
    @SneakyThrows
    @Override
    public boolean isFileExists(String md5) {
        if (ObjectUtils.isEmpty(md5)) {
            log.error("参数md5为空");
            throw new Exception("参数md5为空");
        }
        // 查询
        OssEntity OssEntity = ossDao.selectOne(new QueryWrapper<OssEntity>()
                .eq("md5_file_name", md5)
        );
        /*
          文件不存在 false
          文件存在 true
          */
        return OssEntity != null;
    }


    /**
     * @param multipartFile file
     * @param userUuid      user id
     * @param voiceTimeSize 声音的长度
     * @return OssEntity
     * description: 小文件上传 0-5MB
     * @author wxl
     * date 2022/7/22 22:48
     **/
    @Override
    public OssEntity upload(MultipartFile multipartFile, String userUuid, ResponseEntry res, int voiceTimeSize) throws Exception {
        if (multipartFile == null) {
            throw new Exception(ShardFileStatusCode.FAILURE.getMessage());
        }
        if (userUuid == null) {
            throw new Exception(ShardFileStatusCode.FILE_IS_NULL.getMessage());
        }
        log.info(multipartFile.getName());
        String fileName = (String) res.get(userUuid).get("fileName");
        String targetBucketName = minioConfig.getBucketName();
        String targetName;
        if (voiceTimeSize <= 0) {
            targetName = userUuid + "-" + fileName;
        } else {
            targetName = userUuid + "-" + voiceTimeSize + "-" + fileName;
        }
        if (isFileExists(targetName)) {
            log.warn("File is exist already");
            throw new Exception("File is exist already");
        } else {
            MinioTemplateResult result = minioTemplate.putSimpleObject(multipartFile.getInputStream(), targetBucketName, targetName);
            log.info("小文件上传成功");
            String url = minioTemplate.getPresignedObjectUrl(targetBucketName, result.getOriginalFileName());
            OssEntity OssEntity = new OssEntity();
            OssEntity.setMd5FileName(result.getOriginalFileName());
            OssEntity.setUrl(url);
            OssEntity.setCreateTime(new Date());
            OssEntity.setUpdateTime(new Date());
            ossDao.insert(OssEntity);
            log.info("小文件插入DB");
            return OssEntity;
        }

    }

    /**
     * @param oleName oleName
     * @return String
     * description: 0-6位取文件名
     * @author wxl
     * date 2022/7/22 22:50
     **/
    @Override
    public String rename(String oleName) {
        return oleName.substring(0, 6);
    }

    /**
     * Description: 查询上传过的分片
     *
     * @param objNames   object names
     * @param shardCount shardCount
     * @return java.util.List<java.lang.String>
     * @author wxl
     * Date: 2022/9/21 15:46
     */
    private List<String> containList(List<String> objNames, long shardCount) {
        List<String> containList = new ArrayList<>();
        for (int i = 1; i <= shardCount; i++) {
            String str = String.valueOf(i);
            if (!objNames.contains(str)) {
                containList.add(str);
            }
        }
        return containList;
    }


    /**
     * @param shardCount    number of shards
     * @param inputStreams  files stream
     * @param res           全局共享消息体
     * @param stopStatus    暂停状态
     * @param md5BucketName md5 bucket name
     * @param fileName      file name
     * description:分片任务
     * @author wxl
     * date 2022/7/20 23:54
     **/
    private void uploadJob(long shardCount, List<InputStream> inputStreams, ResponseEntry res, Boolean stopStatus, String md5BucketName, String fileName, String userUuid) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch((int) shardCount);
        if (shardCount > 10000) {
            throw new RuntimeException("Total parts count should not exceed 10000");
        }
        log.info("文件分得总片数：" + shardCount);
        try {
            log.info("开始分片上传:" + fileName);
            for (int i = 0; i < shardCount; i++) {

                stopStatus = (Boolean) res.get(userUuid).get("stopStatus");
                if (stopStatus) {
                    executorService.execute(new BranchThread(inputStreams.get(i), md5BucketName, i + 1, res, countDownLatch, shardCount, stopStatus, userUuid, minioTemplate, sseEmitterService));
                } else {
                    executorService.shutdown();
                    break;
                }
            }
        } catch (Exception e) {
            log.error("线程上传出现异常:{}", e.getMessage());
        } finally {
            //关闭线程池
            executorService.shutdown();
        }
        log.info(">>>>>>>>>>等待分片上传");
        countDownLatch.await();

        log.info(">>>>>>>>>>所有分片上传完成");
        res.get(userUuid).put("status", ShardFileStatusCode.ALL_CHUNK_UPLOAD_SUCCESS.getCode());
    }

    /**
     * @author wxl
     * date 2022/7/20 23:57
     * description: 分片上传内部类
     **/
    private static class BranchThread implements Runnable {
        /**
         * 文件流
         */
        private final InputStream inputStream;

        /**
         * md5名
         */
        private final String md5BucketName;

        /**
         * 当前片数
         */
        private final Integer curIndex;

        /**
         * 返回给前端的res
         */
        private final ResponseEntry res;

        /**
         * 计数等待线程执行完成
         */
        private final CountDownLatch countDownLatch;

        /**
         * 总片数
         */
        private final long shardCount;

        /**
         * 暂停状态
         */
        private final boolean stopStatus;

        /**
         * 用户id
         */
        private final String userUuid;


        /**
         * template
         */
        private final MinioTemplate minioTemplate;
        /**
         * sse发给前端的服务
         */
        private final SseEmitterService sseEmitterService;

        public BranchThread(InputStream inputStream, String md5BucketName, Integer curIndex, ResponseEntry res, CountDownLatch countDownLatch, long shardCount, boolean stopStatus, String userUuid, MinioTemplate minioTemplate, SseEmitterService sseEmitterService) {
            this.inputStream = inputStream;
            this.md5BucketName = md5BucketName;
            this.curIndex = curIndex;
            this.res = res;
            this.countDownLatch = countDownLatch;
            this.shardCount = shardCount;
            this.stopStatus = stopStatus;
            this.userUuid = userUuid;
            this.minioTemplate = minioTemplate;
            this.sseEmitterService = sseEmitterService;
        }


        @SneakyThrows
        @Override
        public void run() {
            try {
                if (stopStatus) {
                    Long uploadPercent = ((curIndex * 100) / shardCount);
                    String curIndexName = String.valueOf(curIndex);
                    //设置百分比
                    res.get(userUuid).put("uploadPercent", uploadPercent);
                    log.info("uploadPercent:{}", uploadPercent);
                    //设置上传文件大小
                    res.get(userUuid).put("uploadSize", inputStream.available());
                    log.info("uploadSize：{}", inputStream.available());
//                    sseEmitterService.sendResMapToOneClient(userUuid, res);
                    MinioTemplateResult minioTemplateResult = minioTemplate.putChunkObject(inputStream, md5BucketName, curIndexName);
                    log.info("分片上传成功： {}", minioTemplateResult);
                } else {
                    executorService.shutdown();
                }

            } catch (Exception e) {
                log.error("线程上传分片异常：{}", e.getMessage());
            } finally {
                countDownLatch.countDown();
            }
        }
    }


}
