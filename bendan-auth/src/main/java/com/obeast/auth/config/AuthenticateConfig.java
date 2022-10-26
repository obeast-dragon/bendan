//package com.obeast.auth.config;
//
//import com.nimbusds.jose.jwk.JWKSet;
//import com.nimbusds.jose.jwk.RSAKey;
//import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
//import com.nimbusds.jose.jwk.source.JWKSource;
//import com.nimbusds.jose.proc.SecurityContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//
//import javax.sql.DataSource;
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.util.UUID;
//
///**
// * @author wxl
// * Date 2022/10/24 10:50
// * @version 1.0
// * Description:
// */
//@EnableWebSecurity
//@Configuration
//public class AuthenticateConfig {
//
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public RegisteredClientRepository registeredClientRepository() {
//
//        return new JdbcRegisteredClientRepository(jdbcTemplate);
//    }
//
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new JdbcUserDetailsManager(dataSource);
//    }
//
//    //    /**
////     * 配置用户信息，或者配置用户数据来源，主要用于用户的检索。
////     * @return
////     */
////    @Bean
////    public UserDetailsService userDetailsService() {
////        UserDetails userDetails = User.withDefaultPasswordEncoder()
////                .username("user")
////                .password("password")
////                .roles("USER")
////                .build();
////
////        return new InMemoryUserDetailsManager(userDetails);
////    }
////
////    /**
////     * oauth2 用于第三方认证，RegisteredClientRepository 主要用于管理第三方（每个第三方就是一个客户端）
////     * @return
////     */
////    @Bean
////    public RegisteredClientRepository registeredClientRepository() {
////        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
////                .clientId("messaging-client")
////                .clientSecret("{noop}secret")
////                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
////                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
////                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
////                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
////                .redirectUri("http://127.0.0.1:18812/authorized")
////                .scope(OidcScopes.OPENID)
////                .scope("message.read")
////                .scope("message.write")
////                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
////                .build();
////
////        return new InMemoryRegisteredClientRepository(registeredClient);
////    }
//
//    /**
//     * 用于给access_token签名使用。
//     * @return
//     */
//    @Bean
//    public JWKSource<SecurityContext> jwkSource() {
//        KeyPair keyPair = generateRsaKey();
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//        RSAKey rsaKey = new RSAKey.Builder(publicKey)
//                .privateKey(privateKey)
//                .keyID(UUID.randomUUID().toString())
//                .build();
//        JWKSet jwkSet = new JWKSet(rsaKey);
//        return new ImmutableJWKSet<>(jwkSet);
//    }
//
//    /**
//     * 生成秘钥对，为jwkSource提供服务。
//     * @return
//     */
//    private static KeyPair generateRsaKey() {
//        KeyPair keyPair;
//        try {
//            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//            keyPairGenerator.initialize(2048);
//            keyPair = keyPairGenerator.generateKeyPair();
//        } catch (Exception ex) {
//            throw new IllegalStateException(ex);
//        }
//        return keyPair;
//    }
//
////    /**
////     * 配置Authorization Server实例
////     * @return
////     */
////    @Bean
////    public ProviderSettings providerSettings() {
////        return ProviderSettings.builder().build();
////    }
//}
