package com.obeast.gateway.config;

import com.obeast.gateway.filter.BendanRequestGlobalFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;


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
    public BendanRequestGlobalFilter bendanRequestGlobalFilter (GatewayConfigProperties configProperties, RedisTemplate<String, Object> redisTemplate) {
        return new BendanRequestGlobalFilter(configProperties,redisTemplate);
    }

//
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
