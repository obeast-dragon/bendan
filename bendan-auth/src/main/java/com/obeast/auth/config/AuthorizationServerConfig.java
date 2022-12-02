package com.obeast.auth.config;

import cn.hutool.core.util.ArrayUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import com.obeast.auth.support.handler.result.failure.CustomizeAuthenticationFailureHandler;
import com.obeast.auth.support.handler.result.success.CustomizeAuthenticationSuccessHandler;
import com.obeast.auth.support.password.OAuth2PasswordCredentialsAuthenticationConverter;
import com.obeast.auth.support.password.OAuth2PasswordCredentialsAuthenticationProvider;
import com.obeast.auth.support.resource.BendanBearerTokenExtractor;
import com.obeast.auth.support.resource.BendanOpaqueTokenIntrospector;
import com.obeast.auth.exception.ResourceAuthExceptionEntryPoint;
import com.obeast.auth.support.resource.ResourcesProperties;
import com.obeast.auth.utils.Jwks;
import com.obeast.auth.utils.OAuth2GeneratorUtils;
import com.obeast.security.business.service.BendanUserDetailsService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.web.authentication.*;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;

import java.util.List;

/**
 * @author Joe Grandja
 * @since 0.0.1
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ResourcesProperties.class)
public class AuthorizationServerConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(
            HttpSecurity http,
            OAuth2PasswordCredentialsAuthenticationProvider oAuth2PasswordCredentialsAuthenticationProvider,
            DaoAuthenticationProvider daoAuthenticationProvider,
            BendanBearerTokenExtractor bendanBearerTokenExtractor,
            ResourcesProperties propertiesUrls,
            ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint,
            OpaqueTokenIntrospector opaqueTokenIntrospector
    ) throws Exception {
        /*配置自定义密码 token 、 res*/
        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer<>();
        http.apply(authorizationServerConfigurer.tokenEndpoint(tokenEndpoint -> {
            tokenEndpoint.accessTokenRequestConverter(createCustomizeConverter());
            tokenEndpoint.accessTokenResponseHandler(customizeAuthenticationSuccessHandler());
            tokenEndpoint.errorResponseHandler(customizeAuthenticationFailureHandler());
        }));

        /*配置登录处理*/
        http
            .authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers(ArrayUtil.toArray(propertiesUrls.getUrls(), String.class)).permitAll().anyRequest()
                        .authenticated())
                .oauth2ResourceServer(
                        oauth2 -> oauth2
                                .opaqueToken(token -> token.introspector(opaqueTokenIntrospector))
                                .authenticationEntryPoint(resourceAuthExceptionEntryPoint)
                                .bearerTokenResolver(bendanBearerTokenExtractor))
                .headers().frameOptions().disable().and()
                .csrf()
                .disable()
                .apply(authorizationServerConfigurer);

        SecurityFilterChain securityFilterChain = http.formLogin(Customizer.withDefaults()).build();
        http.authenticationProvider(oAuth2PasswordCredentialsAuthenticationProvider);
        http.authenticationProvider(daoAuthenticationProvider);
        return securityFilterChain;
    }


    /**
     * 请求令牌的抽取逻辑
     * @param urlProperties 对外暴露的接口列表
     * @return BearerTokenExtractor
     */
    @Bean
    public BendanBearerTokenExtractor bendanBearerTokenExtractor(ResourcesProperties urlProperties) {
        return new BendanBearerTokenExtractor(urlProperties);
    }


    /**
     * 资源服务器异常处理
     * @param objectMapper jackson 输出对象
     * @return ResourceAuthExceptionEntryPoint
     */
    @Bean
    public ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint(ObjectMapper objectMapper) {
        return new ResourceAuthExceptionEntryPoint(objectMapper);
    }

    /**
     * 资源服务器toke内省处理器
     * @param authorizationService token 存储实现
     * @return TokenIntrospector
     */
    @Bean
    public OpaqueTokenIntrospector opaqueTokenIntrospector(OAuth2AuthorizationService authorizationService, BendanUserDetailsService userDetailsService) {
        return new BendanOpaqueTokenIntrospector(authorizationService, userDetailsService);
    }

    /**
     * Description: 创建自定义Converter
     *
     * @return org.springframework.security.web.authentication.AuthenticationConverter
     * @author wxl
     * Date: 2022/10/26 9:42
     */
    private AuthenticationConverter createCustomizeConverter() {
        return new DelegatingAuthenticationConverter(List.of(
                new OAuth2RefreshTokenAuthenticationConverter(),
                new OAuth2ClientCredentialsAuthenticationConverter(),
                new OAuth2PasswordCredentialsAuthenticationConverter(),
                new OAuth2AuthorizationCodeAuthenticationConverter(),
                new OAuth2AuthorizationCodeRequestAuthenticationConverter())
        );

    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = Jwks.generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder().issuer("http://auth-server:9000").build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * Description: 密码模式
     * @author wxl
     * Date: 2022/11/22 16:47
     * @param passwordEncoder passwordEncoder
     * @param userDetailsService  userDetailsService
     * @param httpSecurity http security
     * @return com.obeast.auth.support.password.OAuth2PasswordCredentialsAuthenticationProvider
     */
    @Bean
    public OAuth2PasswordCredentialsAuthenticationProvider oAuth2PasswordCredentialsAuthenticationProvider
    (PasswordEncoder passwordEncoder,
     BendanUserDetailsService userDetailsService,
     HttpSecurity httpSecurity) {
        OAuth2AuthorizationService authorizationService = httpSecurity.getSharedObject(OAuth2AuthorizationService.class);
        OAuth2PasswordCredentialsAuthenticationProvider provider =
                new OAuth2PasswordCredentialsAuthenticationProvider(
                        OAuth2GeneratorUtils.getTokenGenerator(httpSecurity), authorizationService);
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
            PasswordEncoder passwordEncoder, BendanUserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    /**
     * Description: 创建自定义CustomizeAuthenticationSuccessHandler
     * @author wxl
     * Date: 2022/10/31 9:42
     * @return com.obeast.auth.support.handler.result.success.CustomizeAuthenticationSuccessHandler
     */
    private CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler(){
        return new CustomizeAuthenticationSuccessHandler();
    }


    /**
     * Description: 创建自定义CustomizeAuthenticationSuccessHandler
     * @author wxl
     * Date: 2022/10/31 9:42
     * @return com.obeast.auth.support.handler.result.success.CustomizeAuthenticationFailureHandler
     */
    private CustomizeAuthenticationFailureHandler customizeAuthenticationFailureHandler(){
        return new CustomizeAuthenticationFailureHandler();
    }


}