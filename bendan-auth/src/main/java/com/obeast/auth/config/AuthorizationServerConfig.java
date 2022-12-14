package com.obeast.auth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.obeast.auth.support.handler.result.failure.CustomizeAuthenticationFailureHandler;
import com.obeast.auth.support.handler.result.success.CustomizeAuthenticationSuccessHandler;

import com.obeast.auth.support.password.OAuth2PasswordAuthenticationConverter;
import com.obeast.auth.support.password.OAuth2PasswordAuthenticationProvider;
import com.obeast.auth.utils.Jwks;
import com.obeast.auth.utils.OAuth2GeneratorUtils;
import com.obeast.security.resource.BendanUserDetailsService;
import lombok.RequiredArgsConstructor;
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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

/**
 * @author Joe Grandja
 * @since 0.0.1
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class AuthorizationServerConfig {

    private final OAuth2AuthorizationService authorizationService;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(
            HttpSecurity http,
            OAuth2PasswordAuthenticationProvider oAuth2PasswordCredentialsAuthenticationProvider,
            DaoAuthenticationProvider daoAuthenticationProvider
    ) throws Exception {
        /*配置自定义密码 token 、 res*/
        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer<>();
        http.apply(authorizationServerConfigurer.tokenEndpoint(tokenEndpoint -> {
            tokenEndpoint.accessTokenRequestConverter(createCustomizeConverter());
            tokenEndpoint.accessTokenResponseHandler(customizeAuthenticationSuccessHandler());
            tokenEndpoint.errorResponseHandler(customizeAuthenticationFailureHandler());
        }));
        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        http
                .requestMatcher(endpointsMatcher)
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .apply(authorizationServerConfigurer);

        SecurityFilterChain securityFilterChain = http.formLogin(Customizer.withDefaults()).build();
        http.authenticationProvider(oAuth2PasswordCredentialsAuthenticationProvider);
        http.authenticationProvider(daoAuthenticationProvider);
        return securityFilterChain;
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
                new OAuth2PasswordAuthenticationConverter(),
                new OAuth2AuthorizationCodeAuthenticationConverter(),
                new OAuth2AuthorizationCodeRequestAuthenticationConverter())
        );

    }

    /**
     * Description: 密码模式
     * @author wxl
     * Date: 2022/11/22 16:47
     * @param passwordEncoder passwordEncoder
     * @param userDetailsService  userDetailsService
     * @param httpSecurity http security
     * @return com.obeast.auth.support.password.test.OAuth2PasswordCredentialsAuthenticationProvider
     */
    @Bean
    public OAuth2PasswordAuthenticationProvider oAuth2PasswordCredentialsAuthenticationProvider
    (PasswordEncoder passwordEncoder,
     BendanUserDetailsService userDetailsService,
     HttpSecurity httpSecurity) {
        OAuth2PasswordAuthenticationProvider provider =
                new OAuth2PasswordAuthenticationProvider(
                        authorizationService,
                        OAuth2GeneratorUtils.getTokenGenerator(httpSecurity),
                        userDetailsService,
                        passwordEncoder
                );
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