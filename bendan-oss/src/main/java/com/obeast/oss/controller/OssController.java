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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletInputStream;
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
@RequestMapping("/file")
@Tag(name = "Oss接口")
@RequiredArgsConstructor
public class OssController {

    private final OssService ossService;

    private final FlyweightRes res;


    /**
     * Description: 文件上传
     *
     * @param file file
     * @param userId  userId
     * @return com.obeast.oss.base.R<com.obeast.business.entity.OssEntity>
     * @author wxl
     * Date: 2022/9/21 16:04
     */
    @SneakyThrows
    @PostMapping(value = "/uploadFile")
    public CommonResult<OssEntity> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        return ossService.uploadFile(file, userId);

    }


    /**
     * Description: file stop status
     *
     * @param flag          the flag
     * @param userId        userId
     * @return com.obeast.core.base.CommonResult<?>
     * @author wxl
     * Date: 2022/12/26 21:05
     */
    @GetMapping(value = "/stopStatus")
    public CommonResult<?> setStopStatus( @RequestParam("flag") Integer flag, @RequestParam("userId") Long userId) {
        /*
          1 -> true 上传
          0 -> false 暂停
          */
        boolean stopStatus = flag.equals(1);
        if (res.get(userId) != null) {
            res.get(userId).put("stopStatus", stopStatus);
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
     *
     * @param userId userId
     * @return com.obeast.core.base.CommonResult<cn.hutool.json.JSONObject>
     * @author wxl
     * Date: 2022/12/14 17:53
     */
    @GetMapping(value = "/percent")
    public CommonResult<JSONObject> getUploadPercent(@RequestParam("userId") Long userId) {

        if (res.get(userId) != null) {
            String uploadPercent = res.get(userId).get("uploadPercent") == null ? "0" : String.valueOf(res.get(userId).get("uploadPercent"));
            String uploadSize = res.get(userId).get("uploadSize") == null ? "0" : String.valueOf(res.get(userId).get("uploadSize"));
            String fileSize = res.get(userId).get("fileSize") == null ? "0" : String.valueOf(res.get(userId).get("fileSize"));
            JSONObject data = new JSONObject();
            try {
                data.append("uploadPercent", uploadPercent);
                data.append("uploadSize", uploadSize);
                data.append("fileSize", fileSize);
                data.append("fileName", res.get(userId).get("fileName"));
            } catch (JSONException e) {
                log.error("上传进度查询失败{}", e.getMessage());
            }
            return CommonResult.success(data, "获取进度百分比成功");
        }
        return CommonResult.error("请先上传");

    }


    /**
     * Description: 重置上传进度
     *
     * @param userId userId
     * @return com.obeast.core.base.CommonResult
     * @author wxl
     * Date: 2022/12/31 13:19
     */
    @GetMapping("/resetPercent")
    @ResponseBody
    public CommonResult<?> resetPercent(@RequestParam("userId") Long userId) {
        if (res.get(userId) != null) {
            res.get(userId).put("uploadPercent", 0);
            return CommonResult.success();
        }
        return CommonResult.error("请先上传");
    }


}
