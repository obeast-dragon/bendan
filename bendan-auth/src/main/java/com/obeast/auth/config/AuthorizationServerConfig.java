package com.obeast.auth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.obeast.auth.business.domain.AuthUser;
import com.obeast.auth.support.password.another.OAuth2PasswordCredentialsAuthenticationConverter;
import com.obeast.auth.support.password.another.OAuth2PasswordCredentialsAuthenticationProvider;
import com.obeast.auth.utils.Jwks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.web.authentication.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Joe Grandja
 * @since 0.0.1
 */
@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {

    private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";

    @Autowired
    JdbcTemplate jdbcTemplate;
    

    @Bean
    public RegisteredClientRepository registeredClientRepository() {

        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }


    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(
            HttpSecurity http,
            OAuth2PasswordCredentialsAuthenticationProvider oAuth2PasswordCredentialsAuthenticationProvider,
            DaoAuthenticationProvider daoAuthenticationProvider) throws Exception {
        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer<>();
        http.apply(authorizationServerConfigurer.tokenEndpoint((tokenEndpoint) ->
                tokenEndpoint.accessTokenRequestConverter(createCustomizeConverter()))
//                todo 成功处理器 失败处理器
        );

//        authorizationServerConfigurer.authorizationEndpoint(authorizationEndpoint ->
//                authorizationEndpoint.consentPage(CUSTOM_CONSENT_PAGE_URI));
        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();
        http
                .requestMatcher(endpointsMatcher)
                .authorizeRequests(authorizeRequests -> {
                    //设置不需要权限拦截的url
                    List<String> ignores = List.of(
                           //swagger 相关
                            "/v3/api-docs",
                            "/swagger-ui/**",
                            "/swagger-resources/**",
                            //登录、图形验证码，站点图标
                            "/login*",
                            "/favicon.ico",
                            "/common/**",
                            "/login_sms"
                    );

                    authorizeRequests.antMatchers(ignores.toArray(new String[0])).permitAll();

                    authorizeRequests.anyRequest().authenticated();
                })
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .apply(authorizationServerConfigurer);



        SecurityFilterChain securityFilterChain = http.formLogin(Customizer.withDefaults()).build();

        http.authenticationProvider(oAuth2PasswordCredentialsAuthenticationProvider);
        http.authenticationProvider(daoAuthenticationProvider);
        return securityFilterChain;
    }



    /**
     * Description:
     * @author wxl
     * Date: 2022/10/26 9:42
     * @return org.springframework.security.web.authentication.AuthenticationConverter
     */
    private AuthenticationConverter createCustomizeConverter(){
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
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * password认证模式
     * @return
     */
    @Bean
    public OAuth2PasswordCredentialsAuthenticationProvider oAuth2PasswordCredentialsAuthenticationProvider
    (PasswordEncoder passwordEncoder,
     UserDetailsService userDetailsService,
     HttpSecurity httpSecurity){
        OAuth2AuthorizationService authorizationService = httpSecurity.getSharedObject(OAuth2AuthorizationService.class);
        OAuth2PasswordCredentialsAuthenticationProvider provider =
                new OAuth2PasswordCredentialsAuthenticationProvider(
                        OAuth2ConfigurerUtils.getTokenGenerator(httpSecurity),authorizationService);
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
            PasswordEncoder passwordEncoder, UserDetailsService userDetailsService){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }


    /**
     * This is a bit a hack, but as we do not know how we integrate the HealthPlattform this is a very easy way to solve the Problem for the moment.
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return context -> {
            Authentication principal = context.getPrincipal();
            if (Objects.equals(context.getTokenType(), OAuth2TokenType.ACCESS_TOKEN) && principal instanceof UsernamePasswordAuthenticationToken) {
                Optional.ofNullable(principal.getPrincipal()).ifPresent(p ->{
                    if(p instanceof AuthUser){
                        AuthUser user = (AuthUser)p;
                        context.getClaims()
                                .claim("user_id", String.valueOf(user.getUserId()));
                    }
                });
            }
        };
    }


}