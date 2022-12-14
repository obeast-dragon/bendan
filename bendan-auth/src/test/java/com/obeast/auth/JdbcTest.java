package com.obeast.auth;


import cn.hutool.json.JSON;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import java.time.Duration;
import java.util.UUID;

/**
 * @author wxl
 * Date 2022/10/20 14:40
 * @version 1.0
 * Description:
 */
@SpringBootTest
public class JdbcTest {
    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    //
    @Autowired
    private RegisteredClientRepository registeredClientRepository;


    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
     CacheManager cacheManager;

    @Test
    void test(){
        Cache cache = cacheManager.getCache(UserLoginConstant.USERINFO);
        JSON json = (JSON) cache.get("admin").get();
        System.out.println(json);


    }


    /**
     * 创建clientId信息
     */
    @Test
    @Disabled
    void testSaveClient() {
        String bendan = passwordEncoder.encode("bendan");
        RegisteredClient cLIENT_SECRET_BASIC = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("web")
                .clientSecret(bendan)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:18812/authorized")
                .scope(OidcScopes.OPENID)
                .scope(OauthScopeConstant.READ)
                .scope(OauthScopeConstant.WRITE)
                .scope(OauthScopeConstant.ALL)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofHours(12))
                        .refreshTokenTimeToLive(Duration.ofDays(3))
                        .build())
                .build();
        RegisteredClient cLIENT_SECRET_POST = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("messaging-client")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:18812/authorized")
                .scope(OidcScopes.OPENID)
                .scope("message.read")
                .scope("message.write")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

//        注意client_id的变化
        //请求头
//        http://127.0.0.1:18812/oauth2/authorize?response_type=code&client_id=messaging-client&scope=message.read&redirect_uri=http://127.0.0.1:18812/authorized
//        registeredClientRepository.save(cLIENT_SECRET_BASIC);

//         请求体
//        http://127.0.0.1:18812/oauth2/authorize?response_type=code&client_id=messaging-client2&scope=message.read&redirect_uri=http://127.0.0.1:18812/authorized
//        registeredClientRepository.save(cLIENT_SECRET_POST);


        registeredClientRepository.save(cLIENT_SECRET_BASIC);

    }

}