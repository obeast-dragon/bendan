package com.obeast.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.obeast.gateway.filter.BendanRequestGlobalFilter;
import com.obeast.gateway.handler.GatewayGlobalExceptionHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;


/**
 * @author wxl
 * Date 2022/11/2 15:29
 * @version 1.0
 * Description: 网关配置
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(GatewayConfigProperties.class)
public class GatewayConfig {


    /**
     * Description: 自定义异常处理
     * @author wxl
     * Date: 2022/12/9 9:55
     * @param objectMapper objectMapper
     * @return org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
     */
    @Primary
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ObjectMapper objectMapper) {
        return new GatewayGlobalExceptionHandler(objectMapper);
    }


    /**
     * Description: 处理token
     * @author wxl
     * Date: 2022/12/9 23:37
     * @param configProperties  configProperties
     * @return com.obeast.gateway.filter.BendanRequestGlobalFilter
     */
    @Bean
    public BendanRequestGlobalFilter bendanRequestGlobalFilter (GatewayConfigProperties configProperties) {
        return new BendanRequestGlobalFilter(configProperties);
    }



//    /**
//     * Description: 跨域配置
//     * @author wxl
//     * Date: 2022/11/25 9:56
//     * @return CorsFilter
//     */
//    @Bean
//    public CorsFilter corsFilter(){
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**",corsConfiguration);
//        return new CorsFilter(source);
//    }

}
