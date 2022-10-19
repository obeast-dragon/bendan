package com.obeast.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxl
 * Date 2022/10/14 9:31
 * @version 1.0
 * Description:
 */
@RestController
@RequestMapping("/gateway")
public class TestController {


    @GetMapping("/test")
    public String getName(){
        return "TestController";
    }

}
