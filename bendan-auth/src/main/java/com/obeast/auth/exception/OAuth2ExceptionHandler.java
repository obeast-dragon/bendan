package com.obeast.auth.exception;

import com.obeast.common.base.CommonResult;
import com.obeast.common.base.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author wxl
 * Date 2022/9/21 15:41
 * @version 1.0
 * Description: OAuth2异常处理
 */
@RestControllerAdvice
public class OAuth2ExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @ExceptionHandler(OAuth2AuthenticationException.class)
    public CommonResult<?> oauth2AuthenticationExceptionHandler(OAuth2AuthenticationException e) {
        log.error("|--Bendan身份验证异常捕获--| ", e);
        return CommonResult.error(ResultCode.FAILURE);
    }


    @ExceptionHandler(OAuth2AuthorizationException.class)
    public CommonResult<?> oauth2AuthorizationExceptionHandler(OAuth2AuthorizationException e) {
        log.error("|--Bendan授权异常捕获--| ", e);
        return CommonResult.error(ResultCode.FAILURE);
    }

}