package com.obeast.admin.business.controller;

import com.obeast.admin.business.service.remote.MailRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxl
 * Date 2022/11/3 14:33
 * @version 1.0
 * Description:
 */
@RestController
@RequestMapping("/admin")
public class TestController {

    @Autowired
    MailRemote  mailRemote;

    @GetMapping("/test")
    public String t () {
        return mailRemote.test();
    }

}
