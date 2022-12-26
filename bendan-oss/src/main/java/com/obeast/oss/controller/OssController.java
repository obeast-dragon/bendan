package com.obeast.oss.controller;


import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import com.obeast.business.entity.OssEntity;
import com.obeast.core.base.CommonResult;
import com.obeast.oss.domain.FlyweightRes;
import com.obeast.oss.enumration.ShardFileStatusCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.obeast.oss.service.OssService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
@RequiredArgsConstructor
public class OssController {

    private final OssService ossService;

    private final FlyweightRes res;

    /**
     * Description: 分片上传
     * @author wxl
     * Date: 2022/9/21 16:04
     * @param multipartFile files
     * @param request request
     * @return com.obeast.oss.base.R<com.obeast.business.entity.OssEntity>
     */
    @SneakyThrows
    @PostMapping(value = "/shardFile")
    public CommonResult<OssEntity> uploadFile(@RequestParam("multipartFile") MultipartFile multipartFile, HttpServletRequest request) {

        try {
            String userUuid = request.getParameter("userUuid");
            if (multipartFile != null && userUuid != null) {
                String fileName = multipartFile.getOriginalFilename() != null
                        ? multipartFile.getOriginalFilename()
                        : multipartFile.getName();
                String filenameExtension = StringUtils.getFilenameExtension(fileName);

                Map<String, Object> map = res.computeIfAbsent(userUuid, k -> new HashMap<>());
                map.put("stopStatus", true);
                map.put("fileName", fileName);
                map.put("fileSize", multipartFile.getSize());

                OssEntity ossEntity;
                log.info("文件大小为{}", multipartFile.getSize());
                if (multipartFile.getSize() < 5242880L) {
                    ossEntity = ossService.uploadMiniFile(multipartFile, userUuid, res, 0);
                } else {
                    ossEntity = ossService.uploadShard(multipartFile, res, filenameExtension, userUuid);

                }
                return CommonResult.success(ossEntity, ShardFileStatusCode.ALL_CHUNK_SUCCESS.getMessage());
            } else {
                return CommonResult.error(ShardFileStatusCode.FILE_IS_NULL.getMessage() + "或者" + ShardFileStatusCode.UUID_IS_NULL);
            }
        } catch (Exception e) {
            return CommonResult.error(e.getMessage());
        }
    }



    /**
     * Description: file stop status
     * @author wxl
     * Date: 2022/12/26 21:05
     * @param Authorization     the authorization
     * @param flag  the flag
     * @param userUuid      the user
     * @return com.obeast.core.base.CommonResult<?>
     */
    @GetMapping(value = "/stopStatus")
    public CommonResult<?> setStopStatus(@RequestHeader String Authorization, @RequestParam("flag") Integer flag, @RequestParam("userUuid") String userUuid) {
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
            return CommonResult.success(stopStatus);
        }
        return CommonResult.error("请先上传");

    }


    /**
     * Description: 获取实现上传进度
     * @author wxl
     * Date: 2022/12/14 17:53
     * @param userUuid       UUID of the user
     * @return com.obeast.core.base.CommonResult<cn.hutool.json.JSONObject>
     */
    @GetMapping(value = "/percent")
    public CommonResult<JSONObject> getUploadPercent(@RequestParam("userUuid") String userUuid) {

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
            return CommonResult.success(data, "获取进度百分比成功");
        }
        return CommonResult.error("请先上传");

    }


    /**
     * @return R json
     * @description: 重置上传进度
     * @author wxl
     * @date 2022/7/21 10:24
     **/
    @GetMapping("/resetPercent")
    @ResponseBody
    public CommonResult resetPercent(@RequestParam String userUuid) {
        if (res.get(userUuid) != null){
            res.get(userUuid).put("uploadPercent", 0);
            return CommonResult.success();
        }
        return CommonResult.error("请先上传");
    }





}
