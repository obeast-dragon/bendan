package com.obeast.admin.business.controller;

import com.obeast.common.stt.constant.BaiduSttConstant;
import com.obeast.common.stt.template.BaiduSttTemplate;
import com.obeast.core.base.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxl
 * Date 2023/1/3 11:40
 * @version 1.0
 * Description: stt
 */
@RestController
@Tag(name = "stt 接口")
@RequestMapping("/stt")
@RequiredArgsConstructor
public class SttController {

    private final BaiduSttTemplate baiduSttTemplate;

    @Operation(summary = "翻译任务")
    @GetMapping("/atsTask")
    public CommonResult<JSONObject> atsTask(@RequestParam("url") String url) {
        JSONArray jsonArray = baiduSttTemplate.atsTaskResult(url);
        System.err.println(jsonArray);
        return null;
    }



}
