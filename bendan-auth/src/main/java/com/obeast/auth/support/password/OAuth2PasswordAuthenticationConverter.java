package com.obeast.auth.support.password;

import com.obeast.auth.support.base.OAuth2BaseAuthenticationConverter;
import com.obeast.auth.utils.OAuth2Utils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;

/**
 * @author wxl
 * Date 2022/12/6 10:47
 * @version 1.0
 * Description: 获取参数生成 OAuth2PasswordCredentialsAuthenticationToken
 */
public class OAuth2PasswordAuthenticationConverter extends OAuth2BaseAuthenticationConverter<OAuth2PasswordAuthenticationToken> {

    @Override
    public boolean support(String grantType) {
        return AuthorizationGrantType.PASSWORD.getValue().equals(grantType);
    }

    @Override
    public void checkRequestParams(MultiValueMap<String, String> parameters) {
        // username (REQUIRED)
        String username = parameters.getFirst(OAuth2ParameterNames.USERNAME);
        if (!StringUtils.hasText(username) || parameters.get(OAuth2ParameterNames.USERNAME).size() != 1) {
            OAuth2Utils.throwNullError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.USERNAME,
                    OAuth2Utils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        // password (REQUIRED)
        String password = parameters.getFirst(OAuth2ParameterNames.PASSWORD);
        if (!StringUtils.hasText(password) || parameters.get(OAuth2ParameterNames.PASSWORD).size() != 1) {
            OAuth2Utils.throwNullError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.PASSWORD,
                    OAuth2Utils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }
    }

    @Override
    public OAuth2PasswordAuthenticationToken buildConvertToken(Authentication clientPrincipal, Set<String> requestedScopes, Map<String, Object> additionalParameters) {
        return new OAuth2PasswordAuthenticationToken(AuthorizationGrantType.PASSWORD, clientPrincipal,
                requestedScopes, additionalParameters);
    }
}
