package com.obeast.common.stt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author wxl
 * Date 2023/1/4 9:56
 * @version 1.0
 * Description: 百度配置
 */
@Data
@RefreshScope
@ConfigurationProperties("third-party.baidu")
public class ThirdPartyBaiduSttProperties {


    /**
     * appId
     * */
    private String appId;

    /**
     * 客户端Id
     * */
    private String apiKey;

    /**
     * 客户端 密钥
     * */
    private String secretKey;

}
