package com.obeast.auth.support.handler.result.failure;

import com.obeast.core.base.CommonResult;
import com.obeast.core.constant.WebResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wxl
 * Date 2022/10/31 9:32
 * @version 1.0
 * Description: 自定义Authentication失败处理器
 */
@Slf4j
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final HttpMessageConverter<Object> accessTokenHttpResponseConverter = new MappingJackson2HttpMessageConverter();

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        log.error("custom authentication failure: ", exception);
        OAuth2Error error = ((OAuth2AuthenticationException) exception).getError();
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        httpResponse.setStatusCode(HttpStatus.OK);

        CommonResult<OAuth2Error> res = CommonResult.error(WebResultEnum.PARAM_VALID_ERROR);
        res.setData(error);
        accessTokenHttpResponseConverter.write(res, null, httpResponse);
    }
}
