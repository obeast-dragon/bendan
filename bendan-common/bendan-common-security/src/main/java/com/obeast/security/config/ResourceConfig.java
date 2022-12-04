package com.obeast.security.config;

import cn.hutool.core.util.ArrayUtil;
import com.obeast.security.resource.BendanBearerTokenExtractor;
import com.obeast.security.exceptition.ResourceAuthException;
import com.obeast.security.resource.ResourcesProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author wxl
 * Date 2022/12/4 0:07
 * @version 1.0
 * Description: 资源服务器Bean配置
 */
@Slf4j
@EnableWebSecurity
@RequiredArgsConstructor
public class ResourceConfig {

    protected final ResourceAuthException resourceAuthException;

    private final ResourcesProperties resourcesProperties;

    private final BendanBearerTokenExtractor bendanBearerTokenExtractor;

    private final OpaqueTokenIntrospector customOpaqueTokenIntrospector;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers(ArrayUtil.toArray(resourcesProperties.getUrls(), String.class)).permitAll().anyRequest()
                        .authenticated())
                .oauth2ResourceServer(
                        oauth2 -> oauth2.opaqueToken(token -> token.introspector(customOpaqueTokenIntrospector))
                                .authenticationEntryPoint(resourceAuthException)
                                .bearerTokenResolver(bendanBearerTokenExtractor))
                .headers().frameOptions().disable().and().csrf().disable();

        return http.build();
    }
}
