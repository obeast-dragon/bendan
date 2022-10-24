package com.obeast.auth;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.UUID;

/**
 * @author wxl
 * Date 2022/10/20 14:40
 * @version 1.0
 * Description:
 */
@SpringBootTest
public class JdbcTest {


    /**
     * 初始化客户端信息
     */
    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private RegisteredClientRepository registeredClientRepository;


    @Test
    @Disabled
    void testOAuth2AuthorizationService() {
    }

    /**
     * 创建clientId信息
     */
    @Test
    @Disabled
    void testSaveClient() {

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
        RegisteredClient cLIENT_SECRET_POST = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("messaging-client2")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
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
        registeredClientRepository.save(cLIENT_SECRET_BASIC);

//         请求体
//        http://127.0.0.1:18812/oauth2/authorize?response_type=code&client_id=messaging-client2&scope=message.read&redirect_uri=http://127.0.0.1:18812/authorized
        registeredClientRepository.save(cLIENT_SECRET_POST);

    }

    /**
     * 创建用户信息
     */
    @Test
    void testSaveUser() {
        UserDetails userDetails = User.builder().passwordEncoder(s -> "{bcrypt}" + new BCryptPasswordEncoder().encode(s))
                .username("user")
                .password("password")
                .roles("ADMIN")
                .build();
        userDetailsManager.createUser(userDetails);
    }
}
