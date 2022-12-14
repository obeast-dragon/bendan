package com.obeast.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obeast.security.resource.BendanUserDetailsService;
import com.obeast.security.resource.BendanBearerTokenExtractor;
import com.obeast.security.resource.BendanOpaqueTokenIntrospector;
import com.obeast.security.exceptition.ResourceAuthException;
import com.obeast.security.resource.PurviewService;
import com.obeast.security.resource.ResourcesProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

/**
 * @author wxl
 * Date 2022/12/4 0:06
 * @version 1.0
 * Description: 资源服务器自动配置
 */
@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(ResourcesProperties.class)
public class ResourceAutoConfig {

    private final OAuth2AuthorizationService authorizationService;

    private final BendanUserDetailsService userDetailsService;

    private final ObjectMapper objectMapper;


    /**
     * Description: 鉴权bean
     * @author wxl
     * Date: 2022/12/7 21:04
     * @return com.obeast.security.resource.PurviewService
     */
    @Bean("pvs")
    public PurviewService purviewService  () {
        return new PurviewService();
    }

    /**
     * Description: token解析
     * @author wxl
     * Date: 2022/12/7 21:04
     * @param urlProperties   urlProperties
     * @return com.obeast.security.resource.BendanBearerTokenExtractor
     */
    @Bean
    public BendanBearerTokenExtractor bendanBearerTokenExtractor  (ResourcesProperties urlProperties) {
        return new BendanBearerTokenExtractor(urlProperties);
    }


    /**
     * Description: bendanOpaqueTokenIntrospector
     * @author wxl
     * Date: 2022/12/4 12:09
     * @return com.obeast.security.resource.BendanOpaqueTokenIntrospector
     */
    @Bean
    public OpaqueTokenIntrospector bendanOpaqueTokenIntrospector (
    ){
        return new BendanOpaqueTokenIntrospector(authorizationService, userDetailsService);
    }


    @Bean
    public ResourceAuthException resourceAuthExceptionEntryPoint  () {
        return new ResourceAuthException(objectMapper);
    }
}
