package com.obeast.auth.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;


/**
 * @author wxl
 * Date 2022/10/27 15:49
 * @version 1.0
 * Description: OAuth2持久化配置
 */
@Configuration
public class OAuth2AuthorizationPersistenceConfig {


//    /**
//     * Description: token 持久化存储
//     * @author wxl
//     * Date: 2022/10/31 13:51
//     * @param jdbcTemplate jdbcTemplate
//     * @param registeredClientRepository registeredClientRepository
//     * @return org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
//     */
//    @Bean
//    public OAuth2AuthorizationService oAuth2AuthorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository){
//        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
//
//    }



    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }
}
