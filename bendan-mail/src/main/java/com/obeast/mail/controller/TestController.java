package com.obeast.mail.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxl
 * Date 2022/11/3 14:28
 * @version 1.0
 * Description:
 */
@RestController
@RequestMapping("t")
public class TestController {

    @GetMapping("/h")
    public String test () {
        return "token token token token token token token token token";
    }

}
