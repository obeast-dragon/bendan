package com.obeast.auth.business;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public void hello(@RequestParam Map<String, String> maps) {
        System.out.println(maps);
    }
}
