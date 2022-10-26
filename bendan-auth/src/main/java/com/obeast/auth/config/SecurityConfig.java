//package com.obeast.auth.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//
//
//    /**
//     * 这是个Spring security 的过滤器链，默认会配置
//     * <p>
//     * OAuth2 Authorization endpoint
//     * <p>
//     * OAuth2 Token endpoint
//     * <p>
//     * OAuth2 Token Introspection endpoint
//     * <p>
//     * OAuth2 Token Revocation endpoint
//     * <p>
//     * OAuth2 Authorization Server Metadata endpoint
//     * <p>
//     * JWK Set endpoint
//     * <p>
//     * OpenID Connect 1.0 Provider Configuration endpoint
//     * <p>
//     * OpenID Connect 1.0 UserInfo endpoint
//     * 这些协议端点，只有配置了他才能够访问的到接口地址（类似mvc的controller）。
//     *
//     * @param http
//     * @return
//     * @throws Exception
//     */
//    @Bean
//    @Order(1)
//    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
//            throws Exception {
//        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
//        http
//                // Redirect to the login page when not authenticated from the
//                // authorization endpoint
//                .exceptionHandling((exceptions) -> exceptions
//                        .authenticationEntryPoint(
//                                new LoginUrlAuthenticationEntryPoint("/login"))
//                );
//
//        return http.build();
//    }
//
//    /**
//     * 这个也是个Spring Security的过滤器链，用于Spring Security的身份认证。
//     * @param http
//     * @return
//     * @throws Exception
//     */
//    @Bean
//    @Order(2)
//    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
//            throws Exception {
//        http
//                .authorizeHttpRequests((authorize) -> authorize
//                        .anyRequest().authenticated()
//                )
//                // Form login handles the redirect to the login page from the
//                // authorization server filter chain
//                .formLogin(Customizer.withDefaults());
//
//        return http.build();
//    }
//
//
//
//
//}