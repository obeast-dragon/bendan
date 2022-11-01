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
        redisTemplate.opsForValue().set("token", authorizationServiceById);
        OAuth2Authorization token = (OAuth2Authorization) redisTemplate.opsForValue().get("token");
        System.out.println("redis--------------------------------" + token.getAccessToken().getToken().getTokenValue());

    }

    @Test
    void testClient() {
        AuthorizationGrantType password = AuthorizationGrantType.PASSWORD;
        redisTemplate.opsForValue().set("password", password);
    }

    @Test
    void getCount(){
        String token =
                "eyJraWQiOiJlMDk1YjFlNi03NmQ0LTRkZjctYTBlMC03MzViZTQ1NTExMTciLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXVkIjoibWVzc2FnaW5nLWNsaWVudCIsIm5iZiI6MTY2NzI3MjkyMiwic2NvcGUiOlsib3BlbmlkIiwibWVzc2FnZS5yZWFkIiwibWVzc2FnZS53cml0ZSJdLCJpc3MiOiJodHRwOi8vYXV0aC1zZXJ2ZXI6OTAwMCIsImV4cCI6MTY2NzI3MzIyMiwiaWF0IjoxNjY3MjcyOTIyfQ.aRbEtVAv9R7iQcTJ1uPDPRKSiVoiHAuJAHRD8jfoZ3vVLCJso8Xdqqz7N4S0dQ6PeE6RHYuHjoMCPFS0ZJUJfunHnm4zpAKPJhzB75i13lcNOKSWf0MXL2-1HMriubp_lkNLdrWl_OPdV-ShM8D2a_mvrXSPA7zUVY-C859xgRb_A0bsWCmvY7sM28xS0R6qSjMeFpfOnYeVucJ-5ltP8gRKXn8CtI9VsNrj4d0xm-U46x4NBXjFteCsPc5JJ_F35oGuYUnGwLdxeixRa6nxcoeSbXVfsvc8kZDHi32HAju4OweRFfrR4k2w9OTOjx6593ReJQjmpoBdX0dQTGrHCg"
                ;
        OAuth2Authorization serviceByToken = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
//        System.out.println(serviceByToken.getAccessToken().getToken().getTokenValue());
//        authorizationService.remove(serviceByToken);
    }

}
