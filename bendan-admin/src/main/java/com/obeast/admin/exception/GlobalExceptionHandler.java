package com.obeast.admin.exception;

import com.obeast.core.base.CommonResult;
import com.obeast.core.constant.WebResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author wxl
 * Date 2022/9/21 15:41
 * @version 1.0
 * Description: 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @ExceptionHandler(Exception.class)
    public CommonResult<?> exceptionHandler(Exception e) throws Exception {
        log.error("全局异常捕获---------------->", e);
        return CommonResult.error(WebResultEnum.FAILURE);
    }

    @ExceptionHandler(value = {NullPointerException.class, NumberFormatException.class})
    public Object nullPointerException(Exception e) {
        log.error("程序中出现空引用和空参数，请检查！ ------------>", e);
        return CommonResult.error(WebResultEnum.PARAM_MISS);
    }

}