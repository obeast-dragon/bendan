package com.obeast.common.stt.template;

import com.baidu.aip.speech.AipSpeech;
import com.obeast.common.stt.constant.BaiduSttConstant;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


/**
 * @author wxl
 * Date 2023/1/4 17:35
 * @version 1.0
 * Description: baidu stt 模板类
 */
@RequiredArgsConstructor
public class BaiduSttTemplate {

    private final AipSpeech aipSpeech;


    public JSONArray asr(InputStream is, BaiduSttConstant.FileAsrSuffix fileAsrSuffix) throws IOException {
        JSONObject asrRes = aipSpeech.asr(is.readAllBytes(), fileAsrSuffix.getName(), 16000, null);
        return asrRes.getJSONArray("result");
    }

}
