package com.obeast.auth.support.handler.result.failure;

import cn.hutool.core.util.StrUtil;
import com.obeast.core.base.CommonResult;
import com.obeast.core.constant.WebResultEnum;
import lombok.SneakyThrows;
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


    @SneakyThrows
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        log.info("认证异常: {}", exception.getLocalizedMessage());
        OAuth2Error error = ((OAuth2AuthenticationException) exception).getError();
//        log.info("认证异常: {}", error);
        System.out.println(error.getDescription());
        System.out.println(error.getErrorCode());
        System.out.println(error.getUri());
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        httpResponse.setStatusCode(HttpStatus.OK);

        CommonResult<OAuth2Error> res = CommonResult.error();
        res.setMsg(handlerResMsg(error, exception));
        res.setSuccess(Boolean.FALSE);
        res.setCode(WebResultEnum.PARAM_VALID_ERROR.getCode());
        accessTokenHttpResponseConverter.write(res, null, httpResponse);
    }


    /**
     * Description: 处理异常信息
     * @author wxl
     * Date: 2022/12/21 11:42
     * @param error  error message
     * @param exception exception
     * @return java.lang.String
     */
    private String handlerResMsg(OAuth2Error error, AuthenticationException exception) {
        String errorMsg = exception.getLocalizedMessage() == null ? error.getDescription() : exception.getLocalizedMessage();
        if (StrUtil.isEmpty(errorMsg)) {
            errorMsg = error.getErrorCode();
        }
        return errorMsg;
    }
}
