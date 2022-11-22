package com.obeast.auth.controller;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxl
 * Date 2022/11/21 15:13
 * @version 1.0
 * Description:
 */
@RestController
@RequestMapping("/t1")
public class TestController {

    @RequestMapping("/test")
    public void test  () {
        throw new OAuth2AuthenticationException("6666");
    }
}
