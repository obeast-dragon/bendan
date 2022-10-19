package com.obeast.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author wxl
 * Date 2022/9/21 15:37
 * @version 1.0
 * Description: 自定义异常
 */
@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class BendanException extends RuntimeException {


    public BendanException(String message) {
        super(message);
    }

    public BendanException(String message, Throwable cause) {
        super(message, cause);
    }


}
