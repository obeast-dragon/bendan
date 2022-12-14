package com.obeast.auth.support.handler.result.success;

import com.obeast.core.base.CommonResult;
import com.obeast.core.constant.SysConstant;
import com.obeast.security.business.domain.OAuth2TokenRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author wxl
 * Date 2022/10/31 9:32
 * @version 1.0
 * Description: 自定义Authentication成功处理器
 */
@Slf4j
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    private final HttpMessageConverter<Object> accessTokenHttpResponseConverter = new MappingJackson2HttpMessageConverter();


    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        OAuth2AccessTokenAuthenticationToken accessTokenAuthentication =
                (OAuth2AccessTokenAuthenticationToken) authentication;
        handlerTokenResponse(request, response, accessTokenAuthentication);
    }

    /**
     * Description: 处理token
     * @author wxl
     * Date: 2022/12/13 22:44
     * @param request  request
     * @param response  response
     * @param authentication  authentication
     */
    private void handlerTokenResponse(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException {

        OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;

        OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
        OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
        Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();

        OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
                .tokenType(accessToken.getTokenType()).scopes(accessToken.getScopes());
        long betweenAccessToken = 0, betweenRefreshToken = 0;
        if (refreshToken != null) {
            builder.refreshToken(refreshToken.getTokenValue());
            if (refreshToken.getIssuedAt() != null && refreshToken.getExpiresAt() != null) {
                betweenRefreshToken = SysConstant.TOKEN_UNIT.between(refreshToken.getIssuedAt(), refreshToken.getExpiresAt());
                builder.expiresIn(SysConstant.TOKEN_UNIT.between(refreshToken.getIssuedAt(), refreshToken.getExpiresAt()));
            }
        }
        if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
            betweenAccessToken = SysConstant.TOKEN_UNIT.between(accessToken.getIssuedAt(), accessToken.getExpiresAt());
            builder.expiresIn(SysConstant.TOKEN_UNIT.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
        }

        if (!CollectionUtils.isEmpty(additionalParameters)) {
            builder.additionalParameters(additionalParameters);
        }
        OAuth2AccessTokenResponse accessTokenResponse = builder.build();

        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        OAuth2TokenRes oAuth2TokenRes = new OAuth2TokenRes();
        OAuth2TokenRes res =
                oAuth2TokenRes.setOauth2AccessTokenResponse(accessTokenResponse)
                        .setRefreshExpiresIn(betweenRefreshToken)
                        .setAccessTokenExpiresIn(betweenAccessToken);
        // 无状态 注意删除 context 上下文的信息
        SecurityContextHolder.clearContext();
        this.accessTokenHttpResponseConverter.write(CommonResult.success(res), null, httpResponse);
    }
}
