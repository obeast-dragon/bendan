package com.obeast.auth.business;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxl
 * Date 2022/10/27 9:39
 * @version 1.0
 * Description:
 */
@RestController("/hello")
public class HelloController {

    @GetMapping("/t1")
    public String hello() {
        System.out.println("HelloController");
        return "HelloController";
    }
}
