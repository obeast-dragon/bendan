package com.obeast.oss.controller;


import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import com.obeast.oss.base.R;
import com.obeast.oss.domain.ResponseEntry;
import com.obeast.oss.entity.OssEntity;
import com.obeast.oss.enumration.ShardFileStatusCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.obeast.oss.service.OssService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * @author obeast-dragon
 * Date 2022-09-21 15:19:09
 * @version 1.0
 * Description: 
 */
@Slf4j
@CrossOrigin // 允许跨域
@RestController
@RequestMapping("/oss")
@Tag(name = "Oss接口")
public class OssController {

    @Autowired
    private OssService ossService;

    @Autowired
    private ResponseEntry res;

    /**
     * @param multipartFile file
     * @param
     * @description: 分片上传
     * @author wxl
     * @date 2022/7/21 9:57
     **/
    @SneakyThrows
    @PostMapping(value = "/shardFile")
    public R<OssEntity> uploadFile(@RequestParam("multipartFile") MultipartFile multipartFile, HttpServletRequest request) {

        try {
            String userUuid = request.getParameter("userUuid");
            int voiceTimeSize = Integer.parseInt(request.getParameter("voiceTimeSize"));
            if (multipartFile != null && userUuid != null) {
                InputStream inputStream = multipartFile.getInputStream();
                String filenameExtension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename() != null ? multipartFile.getOriginalFilename() : multipartFile.getName());
                String fileName = ossService.rename(multipartFile.getOriginalFilename()) + "." + filenameExtension;
                multipartFile = new MockMultipartFile(fileName, inputStream);
//            String fileName = multipartFile.getOriginalFilename() != null ? multipartFile.getOriginalFilename() : multipartFile.getName();
                Map<String, Object> map = res.computeIfAbsent(userUuid, k -> new HashMap<>());
                map.put("stopStatus", true);
                map.put("fileName", fileName);
                map.put("fileSize", multipartFile.getSize());

                OssEntity ossEntity;
                log.info("文件大小为{}", multipartFile.getSize());
                if (multipartFile.getSize() < 5242880L) {
                    ossEntity = ossService.upload(multipartFile, userUuid, res, voiceTimeSize);
                } else {
                    ossEntity = ossService.uploadShard(multipartFile, res, filenameExtension, userUuid);

                }
                return R.success(ossEntity, ShardFileStatusCode.ALL_CHUNK_SUCCESS.getMessage());
            } else {
                return R.error(ShardFileStatusCode.FILE_IS_NULL.getMessage() + "或者" + ShardFileStatusCode.UUID_IS_NULL);
            }
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }


    /**
     * @param flag file stop status
     * @description: setStopStatus
     * @author wxl
     * @date 2022/7/21 9:58
     **/
    @GetMapping(value = "/stopStatus")
    public R setStopStatus(@RequestHeader String Authorization, @RequestParam("flag") Integer flag, @RequestParam("userUuid") String userUuid) {
        /*
          1 -> true 上传
          0 -> false 暂停
          */
        boolean stopStatus = flag.equals(1);
        if (res.get(userUuid) != null) {
            res.get(userUuid).put("stopStatus", stopStatus);
            if (!stopStatus) {
                log.info("{}>>>>>>>>>>>>>>>上传暂停", stopStatus);
            } else {
                log.info("{}>>>>>>>>>>>>>>>上传开始", stopStatus);
            }
            return R.success(stopStatus);
        }
        return R.error("请先上传");

    }


    /**
     * @description: 获取实现上传进度
     * @author wxl
     * @date 2022/7/21 10:24
     **/
    @GetMapping(value = "/percent")
    public R<JSONObject> getUploadPercent(@RequestParam("userUuid") String userUuid) {

        if (res.get(userUuid) != null) {
            String uploadPercent = res.get(userUuid).get("uploadPercent") == null ? "0" : String.valueOf(res.get(userUuid).get("uploadPercent"));
            String uploadSize = res.get(userUuid).get("uploadSize") == null ? "0" : String.valueOf(res.get(userUuid).get("uploadSize"));
            String fileSize = res.get(userUuid).get("fileSize") == null ? "0" : String.valueOf(res.get(userUuid).get("fileSize"));
            JSONObject data = new JSONObject();
            try {
                data.append("uploadPercent", uploadPercent);
                data.append("uploadSize", uploadSize);
                data.append("fileSize", fileSize);
                data.append("fileName", res.get(userUuid).get("fileName"));
            } catch (JSONException e) {
                log.error("上传进度查询失败{}", e.getMessage());
            }
            return R.success(data, "获取进度百分比成功");
        }
        return R.error("请先上传");

    }


    /**
     * @return R json
     * @description: 重置上传进度
     * @author wxl
     * @date 2022/7/21 10:24
     **/
    @GetMapping("/resetPercent")
    @ResponseBody
    public R resetPercent(@RequestParam String userUuid) {
        if (res.get(userUuid) != null){
            res.get(userUuid).put("uploadPercent", 0);
            return R.success();
        }
        return R.error("请先上传");
    }





}
