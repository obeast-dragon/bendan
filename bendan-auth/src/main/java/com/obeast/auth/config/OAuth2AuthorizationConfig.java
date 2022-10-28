package com.obeast.auth.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;


/**
 * @author wxl
 * Date 2022/10/27 15:49
 * @version 1.0
 * Description:
 */
@Configuration
public class OAuth2AuthorizationConfig {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RegisteredClientRepository registeredClientRepository;

    @Bean
    public OAuth2AuthorizationService getOAuth2AuthorizationService(){
        JdbcOAuth2AuthorizationService jdbcOAuth2AuthorizationService = new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
        return jdbcOAuth2AuthorizationService;

    }
}
