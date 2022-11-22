package com.obeast.gateway.config;

import com.obeast.gateway.filter.BendanRequestGlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wxl
 * Date 2022/11/2 15:29
 * @version 1.0
 * Description: 网关配置
 */
@Configuration
public class GatewayConfig {


    @Bean
    public BendanRequestGlobalFilter bendanRequestGlobalFilter () {
        return new BendanRequestGlobalFilter();
    }


//    @Bean
//    public ValidateGatewayFilter validateGatewayFilter () {
//        return new ValidateGatewayFilter();
//    }
}
