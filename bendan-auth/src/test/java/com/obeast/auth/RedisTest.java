package com.obeast.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;

import java.util.UUID;

/**
 * @author wxl
 * Date 2022/10/31 14:13
 * @version 1.0
 * Description:
 */
@SpringBootTest
public class RedisTest {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    OAuth2AuthorizationService authorizationService;


    @Test
    void get(){
        OAuth2Authorization authorizationServiceById = authorizationService.findById("946da889-0b30-49ff-8468-f90c6b44c885");
        System.out.println(authorizationServiceById.getAccessToken().getToken().getTokenValue());
        System.out.println();
        redisTemplate.opsForValue().set("token", authorizationServiceById);
        OAuth2Authorization token = (OAuth2Authorization) redisTemplate.opsForValue().get("token");
        System.out.println("redis--------------------------------" + token.getAccessToken().getToken().getTokenValue());

    }

    @Test
    void testClient() {
        RegisteredClient cLIENT_SECRET_BASIC = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("messaging-client")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:18812/authorized")
                .scope(OidcScopes.OPENID)
                .scope("message.read")
                .scope("message.write")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        redisTemplate.opsForValue().set("cLIENT_SECRET_BASIC", cLIENT_SECRET_BASIC);
    }
}
