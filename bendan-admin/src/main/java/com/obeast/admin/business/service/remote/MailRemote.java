package com.obeast.admin.business.service.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author wxl
 * Date 2022/11/3 14:32
 * @version 1.0
 * Description:
 */
@FeignClient(value = "bendan-mail")
public interface MailRemote {

    @GetMapping("/t/h")
    String test ();
}
