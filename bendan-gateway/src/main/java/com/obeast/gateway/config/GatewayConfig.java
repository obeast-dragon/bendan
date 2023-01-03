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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;


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
     *
     * @param objectMapper objectMapper
     * @return org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
     * @author wxl
     * Date: 2022/12/9 9:55
     */
    @Primary
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ObjectMapper objectMapper) {
        return new GatewayGlobalExceptionHandler(objectMapper);
    }


    /**
     * Description: 处理token
     *
     * @param configProperties configProperties
     * @return com.obeast.gateway.filter.BendanRequestGlobalFilter
     * @author wxl
     * Date: 2022/12/9 23:37
     */
    @Bean
    public BendanRequestGlobalFilter bendanRequestGlobalFilter(GatewayConfigProperties configProperties) {
        return new BendanRequestGlobalFilter(configProperties);
    }


    /**
     * Description: 跨域配置
     *
     * @return CorsFilter
     * @author wxl
     * Date: 2022/11/25 9:56
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 配置跨域
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许哪个请求头
        corsConfiguration.addAllowedHeader("*");
        // 允许哪个方法进行跨域
        corsConfiguration.addAllowedMethod("*");
        // 允许哪个请求来源进行跨域
        // corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedOriginPattern("*");
        // 是否允许携带cookie进行跨域
        corsConfiguration.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(source);
    }

}
