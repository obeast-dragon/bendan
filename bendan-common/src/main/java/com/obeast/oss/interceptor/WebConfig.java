package com.obeast.oss.interceptor;


//继承：WebMvcConfigurationSupport 或者接口：WebMvcConfigurer 均可
//配置登录拦截内容
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/admin/**","/","/archives/**")
//                .excludePathPatterns("/admin")
//                .excludePathPatterns("/admin/login")
//                .excludePathPatterns("/admin/login/input");
////        WebMvcConfigurer.super.addInterceptors(registry);
//
//    }
//
//
//
//}