package com.obeast.auth.support.password.test;

import com.obeast.auth.support.base.OAuth2BaseAuthenticationConverter;
import com.obeast.auth.utils.OAuth2Utils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 *
 * password converter
 * @author 01266953
 * 获取参数生成 OAuth2PasswordCredentialsAuthenticationToken
 */
public class OAuth2PasswordAuthenticationConverter
        extends OAuth2BaseAuthenticationConverter<OAuth2PasswordAuthenticationToken> {
    private static final String ACCESS_TOKEN_REQUEST_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    @Override
    public boolean support(String grantType) {
        return AuthorizationGrantType.PASSWORD.getValue().equals(grantType);
    }

    @Override
    public void checkRequestParams(MultiValueMap<String, String> parameters) {
        //获取用户名密码
        String username = parameters.getFirst(OAuth2ParameterNames.USERNAME);
        if(!StringUtils.hasText(username)){
            OAuth2Utils.throwError(
                    OAuth2ErrorCodes.INVALID_REQUEST,
                    OAuth2ParameterNames.USERNAME,
                    ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        String password = parameters.getFirst(OAuth2ParameterNames.PASSWORD);
        if(!StringUtils.hasText(password)){
            OAuth2Utils.throwError(
                    OAuth2ErrorCodes.INVALID_REQUEST,
                    OAuth2ParameterNames.PASSWORD,
                    ACCESS_TOKEN_REQUEST_ERROR_URI);
        }
    }

    @Override
    public OAuth2PasswordAuthenticationToken buildConvertToken(
            Authentication clientPrincipal,
            Set<String> requestedScopes,
            Map<String, Object> additionalParameters) {


        return null;
    }


}
