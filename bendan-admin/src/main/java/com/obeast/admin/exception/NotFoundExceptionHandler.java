package com.obeast.admin.exception;

import com.obeast.core.base.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author wxl
 * Date 2022/12/14 16:34
 * @version 1.0
 * Description: 找不到路径的异常处理
 */
@RestControllerAdvice
@Slf4j
public class NotFoundExceptionHandler extends ResponseEntityExceptionHandler {

    public NotFoundExceptionHandler() {
        super();
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage(),ex);
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
        }
        return new ResponseEntity<>(CommonResult.error(status.value(), ex.getMessage()), headers, status);
    }


 }
