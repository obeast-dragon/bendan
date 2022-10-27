package com.obeast.auth.support.sms;

import com.obeast.auth.support.base.OAuth2BaseAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.util.Map;

/**
 * @author wxl
 * Date 2022/10/26 17:26
 * @version 1.0
 * Description: 短信模式provider
 */
public class OAuth2SmsAuthenticationProvider extends OAuth2BaseAuthenticationProvider<OAuth2SmsAuthenticationToken> {

    public OAuth2SmsAuthenticationProvider(AuthenticationManager authenticationManager, OAuth2AuthorizationService authorizationService, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        super(authenticationManager, authorizationService, tokenGenerator);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }

    @Override
    public UsernamePasswordAuthenticationToken buildCustomizeToken(Map<String, Object> reqParams) {
        return null;
    }

    @Override
    public void supportClient(RegisteredClient registeredClient) {

    }
}
