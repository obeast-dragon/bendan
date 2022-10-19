package com.obeast.auth.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author wxl
 * Date 2022/10/19 11:32
 * @version 1.0
 * Description: 安全配置
 */
@Slf4j
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        //配置oauth2
        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer<>();

        //配置http
        http
                .authorizeRequests(
                        request -> {
                            try {
                                request
                                        .antMatchers("/ras/jwtkey")
                                        .permitAll()
                                        .and()
                                        .authorizeRequests()
                                        .anyRequest()
                                        .authenticated()
                                ;
                            } catch (Exception e) {
                                log.error("===>Default security filter fail: ", e);
                                throw new RuntimeException(e);
                            }
                        }
                )
                .formLogin(withDefaults())
//                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .apply(authorizationServerConfigurer)
        ;
        return null;
    }
}
