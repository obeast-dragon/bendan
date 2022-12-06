package com.obeast.auth.support.password;

import com.obeast.auth.support.base.OAuth2BaseAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Map;
import java.util.Set;

/**
 * @author wxl
 * Date 2022/12/6 10:47
 * @version 1.0
 * Description: password token
 */
public class OAuth2PasswordAuthenticationToken extends OAuth2BaseAuthenticationToken {
    public OAuth2PasswordAuthenticationToken(AuthorizationGrantType authorizationGrantType,
                                             Authentication clientPrincipal,
                                             Set<String> scopes,
                                             Map<String, Object> additionalParameters) {
        super(authorizationGrantType, clientPrincipal, scopes, additionalParameters);
    }
}
