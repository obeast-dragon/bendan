package com.obeast.admin.business.controller;

import com.obeast.admin.business.service.BaiduSttService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    private final BaiduSttService baiduSttService;

    @Operation(summary = "翻译 url 音频文件")
    @GetMapping("/asr")
    public JSONArray asr (String url) {
        return baiduSttService.stt(url);
    }
}
