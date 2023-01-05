package com.obeast.admin.business.service;

import com.obeast.common.oss.template.TencentOssTemplate;
import com.obeast.common.stt.constant.BaiduSttConstant;
import com.obeast.common.stt.template.BaiduSttTemplate;
import com.obeast.core.exception.BendanException;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @author wxl
 * Date 2023/1/5 9:54
 * @version 1.0
 * Description: Baidu Stt 服务
 */
@Service
@RequiredArgsConstructor
public class BaiduSttService {

    private final BaiduSttTemplate baiduSttTemplate;

    private final TencentOssTemplate tencentOssTemplate;

    public JSONArray stt (String url) {
        try {
            InputStream is = tencentOssTemplate.downloadByUrl(url);
            return baiduSttTemplate.asr(is, BaiduSttConstant.FileAsrSuffix.PCM);
        }catch (Exception e) {
            throw new BendanException(e.getMessage());
        }
    }
}
