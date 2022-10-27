package com.obeast.auth.support.password;


import com.obeast.auth.support.base.OAuth2BaseAuthenticationProvider;
import com.obeast.auth.support.base.OAuth2BaseAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.StringUtils;


import java.util.Map;


/**
 * @author wxl
 * Date 2022/10/24 13:52
 * @version 1.0
 * Description: 密码模式的Provider
 */
public class OAuth2PasswordAuthenticationProvider extends
        OAuth2BaseAuthenticationProvider<OAuth2BaseAuthenticationToken> {

    private static final Logger log = LoggerFactory.getLogger(OAuth2PasswordAuthenticationProvider.class);

    public OAuth2PasswordAuthenticationProvider(
            AuthenticationManager authenticationManager,
            OAuth2AuthorizationService authorizationService,
            OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator
    ) {
        super(authenticationManager, authorizationService, tokenGenerator);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        boolean supports = OAuth2PasswordAuthenticationToken.class.isAssignableFrom(authentication);
        log.debug("supports authentication=" + authentication + " returning " + supports);
        return supports;

    }

    @Override
    public UsernamePasswordAuthenticationToken buildCustomizeToken(Map<String, Object> reqParams) {
        String username = (String) reqParams.get(OAuth2ParameterNames.USERNAME);
        String password = (String) reqParams.get(OAuth2ParameterNames.PASSWORD);
        if (!StringUtils.hasText(password) || !StringUtils.hasText(username)) {
            log.error("密码或者用户名不能为空");
            throw new BadCredentialsException("username or password are must be not null");
        }
        return new UsernamePasswordAuthenticationToken(username, password);
    }

    @Override
    public void supportClient(RegisteredClient registeredClient) {
        assert registeredClient != null;
        if (!registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.PASSWORD)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }
    }
}
