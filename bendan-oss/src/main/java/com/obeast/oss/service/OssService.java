package com.obeast.oss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.obeast.business.entity.OssEntity;
import com.obeast.oss.domain.MergeShardArgs;
import com.obeast.oss.domain.FlyweightRes;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author obeast-dragon
 * Date 2022-09-21 15:19:09
 * @version 1.0
 * Description: 
 */
public interface OssService extends IService<OssEntity> {

    /**
     * 文件上传，适合大于5M的大文件，集成了分片上传
     *
     * @param file     前台文件；
     * @param res      前后端共享数据
     * @param fileType 文件类型
     * @param userUuid user id
     */
    OssEntity uploadShard(MultipartFile file, FlyweightRes res, String fileType, String userUuid);

    /**
     * 文件合并
     *
     * @param mergeShardArgs 合并参数
     */
    OssEntity mergeShard(MergeShardArgs mergeShardArgs, String userUuid, FlyweightRes res);


    /**
     * 根据文件大小和文件的md5校验文件是否存在
     * 暂时使用Redis实现，后续需要存入数据库
     * 实现秒传接口
     *
     * @param md5 文件的md5
     * @return 操作是否成功
     */
    boolean isFileExists(String md5);



    /**
     * description: 0-5M小文件上传
     * @author wxl
     * date 2022/7/21 16:47
     * @param multipartFile     前台文件
     * @param userUuid user id
     * @param voiceTimeSize 声音的长度
     * @return OssEntity
     **/
    OssEntity uploadMiniFile(MultipartFile multipartFile, String userUuid, FlyweightRes res, int voiceTimeSize) throws Exception;


    /**
     * description: 0-6位取文件名
     * @author wxl
     * date 2022/7/22 22:50
     * @param oleName  oleName
     * @return String
     **/
    String rename(String oleName);




}

