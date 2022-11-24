package com.obeast.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obeast.common.config.GatewayConfigProperties;
import com.obeast.gateway.filter.BendanRequestGlobalFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author wxl
 * Date 2022/11/2 15:29
 * @version 1.0
 * Description: 网关配置
 */
@Configuration
@EnableConfigurationProperties(GatewayConfigProperties.class)
public class GatewayConfig {

    @Bean
    public BendanRequestGlobalFilter bendanRequestGlobalFilter () {
        return new BendanRequestGlobalFilter();
    }


}
