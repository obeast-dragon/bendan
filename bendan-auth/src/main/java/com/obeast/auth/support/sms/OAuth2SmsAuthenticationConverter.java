package com.obeast.auth.support.sms;

import com.obeast.auth.support.base.OAuth2BaseAuthenticationConverter;
import com.obeast.auth.support.base.OAuth2BaseAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * @author wxl
 * Date 2022/10/26 17:25
 * @version 1.0
 * Description: 短信模式converter
 */
public class OAuth2SmsAuthenticationConverter
        extends OAuth2BaseAuthenticationConverter<OAuth2SmsAuthenticationToken> {
    @Override
    public boolean support(String grantType) {
        return false;
    }

    @Override
    public void checkRequestParams(MultiValueMap<String, String> parameters) {

    }

    @Override
    public OAuth2SmsAuthenticationToken buildConvertToken(Authentication clientPrincipal, Set<String> requestedScopes, Map<String, Object> additionalParameters) {
        return null;
    }
}
