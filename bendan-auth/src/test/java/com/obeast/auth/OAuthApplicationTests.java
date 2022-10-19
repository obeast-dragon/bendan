package com.obeast.auth;

import com.obeast.auth.business.entity.UserInfoEntity;
import com.obeast.auth.business.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.util.UUID;

@SpringBootTest
@Slf4j
class OAuthApplicationTests {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Disabled
    void userSave() {
        String password = "admin";
        UserInfoEntity info = new UserInfoEntity();
        info.setPassword(passwordEncoder.encode(password));
        info.setUsername("test4");
        info.setNickName("hanbao");
        userInfoService.save(info);

    }

    @Autowired
    RegisteredClientRepository registeredClientRepository;

    @Test
    @Disabled
    void registerClient(){
        String id = UUID.randomUUID().toString().replaceAll("-", "");

        TokenSettings tokenSettings = TokenSettings.builder()
                .reuseRefreshTokens(true)
                .refreshTokenTimeToLive(Duration.ofDays(7))
                .accessTokenTimeToLive(Duration.ofHours(8))
                .idTokenSignatureAlgorithm(SignatureAlgorithm.RS256)
                .reuseRefreshTokens(false)
                .build();

        RegisteredClient client = RegisteredClient.withId(id)
                .clientId("web-browser")
                .clientIdIssuedAt(Instant.now())
                .clientSecret("{noop}secret")
                .clientSecretExpiresAt(Instant.now().plus(Period.ofDays(20)))
                .clientName("bendan有限公司")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scope("server")
                .tokenSettings(tokenSettings)
                .build();
        registeredClientRepository.save(client);

    }


    @Autowired
    KeyPair keyPair;

    @Test
    @Disabled
    void GetKey() {
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        System.out.println(publicKey);
        System.out.println(privateKey);
    }
}
