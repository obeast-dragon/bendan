package com.obeast.auth.support.password;

import com.obeast.auth.support.base.OAuth2BaseAuthenticationToken;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wxl
 * Date 2022/10/24 13:53
 * @version 1.0
 * Description: 密码模式的Token
 */
public class OAuth2PasswordAuthenticationToken extends OAuth2BaseAuthenticationToken {

    public OAuth2PasswordAuthenticationToken(
            AuthorizationGrantType authorizationGrantType,
            Authentication clientPrincipal,
            @Nullable Set<String> scopes,
            @Nullable Map<String, Object> additionalParameters
    ) {
        super(authorizationGrantType, clientPrincipal, scopes, additionalParameters);
    }
}
