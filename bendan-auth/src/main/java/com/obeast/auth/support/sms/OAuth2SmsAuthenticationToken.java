package com.obeast.auth.support.sms;

import com.obeast.auth.support.base.OAuth2BaseAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Map;
import java.util.Set;

/**
 * @author wxl
 * Date 2022/10/26 17:27
 * @version 1.0
 * Description: 短信模式tokengenerator
 */
public class OAuth2SmsAuthenticationToken extends OAuth2BaseAuthenticationToken {

    public OAuth2SmsAuthenticationToken(AuthorizationGrantType authorizationGrantType,
                                        Authentication clientPrincipal,
                                        Set<String> scopes, Map<String, Object> additionalParameters) {
        super(authorizationGrantType, clientPrincipal, scopes, additionalParameters);
    }
}
