package com.obeast.common.stt.config;

import com.obeast.common.stt.properties.ThirdPartyBaiduSttProperties;
import com.obeast.common.stt.template.BaiduSttTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;


/**
 * @author wxl
 * Date 2023/1/4 17:33
 * @version 1.0
 * Description: baidu Stt 自动配置
 */
public class BaiduSttAutoConfig {

    /**
     * Description: baidu stt 配置
     *
     * @return com.obeast.common.stt.properties.ThirdPartyBaiduProperties
     * @author wxl
     * Date: 2023/1/4 14:56
     */
    @Bean
    @Primary
    public ThirdPartyBaiduSttProperties thirdPartyBaiduProperties() {
        return new ThirdPartyBaiduSttProperties();
    }

    /**
     * Description: 创建 百度stt 客户端
     * @author wxl
     * Date: 2023/1/5 9:28
     * @param baiduAtProperties baiduAtProperties
     * @return com.baidu.aip.speech.AipSpeech
     */
    @Bean
    public AtsSpeech aipSpeech(ThirdPartyBaiduSttProperties baiduAtProperties) {
        return new AtsSpeech(baiduAtProperties.getAppId(), baiduAtProperties.getApiKey(), baiduAtProperties.getSecretKey());
    }

    /**
     * Description: 百度 stt 方法
     * @author wxl
     * Date: 2023/1/5 9:35
     * @param atsSpeech atsSpeech
     * @return com.obeast.common.stt.template.BaiduSttTemplate
     */
    @Bean
    public BaiduSttTemplate  BaiduSttTemplate(AtsSpeech atsSpeech) {
        return new BaiduSttTemplate(atsSpeech);
    }

}
