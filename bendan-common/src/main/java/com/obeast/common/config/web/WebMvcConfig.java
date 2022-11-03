package com.obeast.common.config.web;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wxl
 * Date 2022/11/2 10:45
 * @version 1.0
 * Description:绑定拦截器
 */
@AutoConfiguration
@ConditionalOnMissingClass("com.obeast.gateway.filter.BendanRequestGlobalFilter")
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor requestHeaderContextInterceptor() {
        return new RequestHeaderContextInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestHeaderContextInterceptor()).addPathPatterns("/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
