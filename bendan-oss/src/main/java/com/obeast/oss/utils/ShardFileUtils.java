package com.obeast.oss.utils;

import com.worldintek.fms.domain.MockMultipartFile;
import com.worldintek.fms.enumration.ShardFileStatusCode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxl
 * @version 1.0
 * @description: 文件分片工具类
 * @date 2022/7/6 18:16
 */
@Slf4j
public class ShardFileUtils {

    private ShardFileUtils() {

    }


    /**
     * 大文件分片成 List<InputStream>
     *
     * @param file      文件路径；
     * @param splitSize = 5 * 1024 * 1024;//单片文件大小,5M
     * @return List<MultipartFile> 分片集合
     */
    @SneakyThrows
    public static List<InputStream> splitFileInputStreams(File file, long splitSize) {
        if (splitSize < (5 * 1024 * 1024)) {
            throw new Exception(ShardFileStatusCode.SHARD_MUST_MORE_THAN_5M.getMessage());
        }
        List<InputStream> inputStreams = new ArrayList<>();
        InputStream bis = null;//输入流用于读取文件数据
        OutputStream bos = null;//输出流用于输出分片文件至磁盘
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            long writeByte = 0;//已读取的字节数
            int len = 0;
            byte[] bt = new byte[5 * 1024 * 1024];
            while (-1 != (len = bis.read(bt))) {
                if (writeByte % splitSize == 0) {
                    bos = new ByteArrayOutputStream();
                }
                writeByte += len;
                bos.write(bt, 0, len);
                if (writeByte % splitSize == 0) {
                    InputStream inputStream = IOConvertUtils.oConvertI(bos);
                    inputStreams.add(inputStream);
                }
            }
            InputStream inputStream = IOConvertUtils.oConvertI(bos);
            inputStreams.add(inputStream);
            log.info("{} 文件分片成功！开始准备分片上传", file.getName());
        } catch (Exception e) {
            log.error("文件分片失败！原因：{}", e.getMessage());
            e.printStackTrace();
        }
        return inputStreams;
    }


    /**
     * Multipart文件分片成List<InputStream>
     *
     * @param multipartFile      MultipartFile
     * @param splitSize = 5 * 1024 * 1024;//单片文件大小,5M
     * @return List<MultipartFile> 分片集合
     */
    @SneakyThrows
    public static List<InputStream> splitMultipartFileInputStreams(MultipartFile multipartFile, long splitSize) {
        if (splitSize < (5 * 1024 * 1024)) {
            throw new Exception(ShardFileStatusCode.SHARD_MUST_MORE_THAN_5M.getMessage());
        }
        String filename = multipartFile.getOriginalFilename();
        List<InputStream> inputStreams = new ArrayList<>();
        InputStream bis = null;//输入流用于读取文件数据
        OutputStream bos = null;//输出流用于输出分片文件至磁盘
        try {
            bis = new BufferedInputStream(multipartFile.getInputStream());
            long writeByte = 0;//已读取的字节数
            int len = 0;
            byte[] bt = new byte[5 * 1024 * 1024];
            while (-1 != (len = bis.read(bt))) {
                if (writeByte % splitSize == 0) {
                    bos = new ByteArrayOutputStream();
                }
                writeByte += len;
                bos.write(bt, 0, len);
                if (writeByte % splitSize == 0) {
                    InputStream inputStream = IOConvertUtils.oConvertI(bos);
                    inputStreams.add(inputStream);
                }
            }
            InputStream inputStream = IOConvertUtils.oConvertI(bos);
            inputStreams.add(inputStream);
            log.info("{} 文件分片成功！", filename);
        } catch (Exception e) {
            log.error("文件分片失败！原因：{}", e.getMessage());
            e.printStackTrace();
        }
        return inputStreams;
    }

    /**
     * 大文件分片成List<MultipartFile>
     * @param file      文件路径
     * @param splitSize = 5 * 1024 * 1024;//单片文件大小,5M
     * @return List<MultipartFile>
     */
    public static List<MultipartFile> splitFileMultipartFiles(File file, long splitSize) {
        List<MultipartFile> files = new ArrayList<>();
        InputStream bis = null;//输入流用于读取文件数据
        OutputStream bos = null;//输出流用于输出分片文件至磁盘
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            long writeByte = 0;//已读取的字节数
            int len = 0;
            byte[] bt = new byte[5 * 1024 * 1024];
            while (-1 != (len = bis.read(bt))) {
                if (writeByte % splitSize == 0) {
                    bos = new ByteArrayOutputStream();
                }
                writeByte += len;
                bos.write(bt, 0, len);
                if (writeByte % splitSize == 0) {
                    InputStream inputStream = IOConvertUtils.oConvertI(bos);
                    MultipartFile multipartFile = new MockMultipartFile(String.valueOf((writeByte / splitSize)), inputStream);
                    files.add(multipartFile);
                }
            }
            InputStream inputStream = IOConvertUtils.oConvertI(bos);
            MultipartFile multipartFile = new MockMultipartFile(String.valueOf((writeByte / splitSize)), inputStream);
            files.add(multipartFile);
            System.out.println("文件分片成功！");
        } catch (Exception e) {
            System.out.println("文件分片失败！");
            e.printStackTrace();
        }
        return files;
    }

}
