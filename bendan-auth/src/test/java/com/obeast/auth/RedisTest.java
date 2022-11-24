package com.obeast.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Timer;
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

    private static final String AUTHORIZATION = "token";

    @Test
    void get() {
        OAuth2Authorization authorizationServiceById = authorizationService.findById("946da889-0b30-49ff-8468-f90c6b44c885");
        System.out.println(authorizationServiceById.getAccessToken().getToken().getTokenValue());
        System.out.println();
//        redisTemplate.opsForValue().set("token", authorizationServiceById);
//        OAuth2Authorization token = (OAuth2Authorization) redisTemplate.opsForValue().get("token");
//        System.out.println("redis--------------------------------" + token.getAccessToken().getToken().getTokenValue());

    }

    @Test
    void testClient() {
        AuthorizationGrantType password = AuthorizationGrantType.PASSWORD;
        redisTemplate.opsForValue().set("password", password);
    }

    @Test
    void getCount(){
        String token =
                "eyJraWQiOiI1MTBmMTFiNy0xZGJhLTRjNTUtOWFkNi02ZmQ3MzEyNDczYzgiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXVkIjoibWVzc2FnaW5nLWNsaWVudCIsIm5iZiI6MTY2OTE4MTk1OSwic2NvcGUiOlsib3BlbmlkIiwibWVzc2FnZS5yZWFkIiwibWVzc2FnZS53cml0ZSJdLCJpc3MiOiJodHRwOi8vYXV0aC1zZXJ2ZXI6OTAwMCIsImV4cCI6MTY2OTE4MjI1OSwiaWF0IjoxNjY5MTgxOTU5fQ.PnzOKUjGHsU0Brag7_uM8jrRo2qQxMRGv-S_n3ziz1tbl6csj0T5sjWo8ZbwjmrhtTN2OKfezrlDZQ2xV4j6nhG9Mwxip7I0FYx6Sr9lEEQ8GXK5a6BX8Mvv05Pg5brSh6evqZNX1SLm3qDpbFXxmvi3Xd63uSf6WaevxOO-hz0CNP8379GGwmlsVSsjKPx47YwsBBr7XQbDfW5wWDNoApgnKpEh1NlnXqprRe_g3F1JvEk94CFd8UJTDcajLnbTE744IyRCmKFV4cSWCzaz_oT-ina8K8rujgXFBcrAL3aAR5_XmfpKZjCh7WrCAFMxW_inUUKRcLe9yeibjwioqA"
                ;
        OAuth2Authorization serviceByToken = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        System.out.println(serviceByToken);

    }


    private String createRedisKey(String type, String value) {
        return String.format("%s::%s::%s", AUTHORIZATION, type, value);
    }

}
