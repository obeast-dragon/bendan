package com.obeast.oss.exception;

import com.obeast.oss.base.CommonResult;
import com.obeast.oss.base.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



/**
 * @author wxl
 * Date 2022/9/21 15:41
 * @version 1.0
 * Description: 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @ExceptionHandler(Exception.class)
    public CommonResult<?> exceptionHandler(Exception e) throws Exception {
        log.error("全局异常捕获---------------->", e);
        return CommonResult.error(ResultCode.FAILED);
    }

    @ExceptionHandler(value = {NullPointerException.class, NumberFormatException.class})
    public Object nullPointerException(Exception e) {
        log.error("程序中出现空引用和空参数，请检查！ ------------>", e);
        return CommonResult.error(ResultCode.ARGS_IS_NULL);
    }
}