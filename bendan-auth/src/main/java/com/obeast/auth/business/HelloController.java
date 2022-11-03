package com.obeast.auth.business;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author wxl
 * Date 2022/10/27 9:39
 * @version 1.0
 * Description:
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/t1")
    public String hello() {
        System.out.println("HelloController");
        return "HelloController";
    }

    @GetMapping("/t2")
    public String test() {
        return "token 成功";
    }
}
