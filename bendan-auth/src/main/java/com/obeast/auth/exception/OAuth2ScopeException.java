package com.obeast.auth.exception;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * @author wxl
 * Date 2022/10/24 15:49
 * @version 1.0
 * Description: Scope异常处理
 */
public class OAuth2ScopeException extends OAuth2AuthenticationException {

    /**
     * Constructs a <code>ScopeException</code> with the specified message.
     * @param msg the detail message.
     */
    public OAuth2ScopeException(String msg) {
        super(new OAuth2Error(msg), msg);
    }

    /**
     * Constructs a {@code ScopeException} with the specified message and root cause.
     * @param msg the detail message.
     * @param cause root cause
     */
    public OAuth2ScopeException(String msg, Throwable cause) {
        super(new OAuth2Error(msg), cause);
    }

}
