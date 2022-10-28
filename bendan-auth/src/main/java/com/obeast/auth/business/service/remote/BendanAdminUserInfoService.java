package com.obeast.auth.business.service.remote;

import com.obeast.common.to.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wxl
 * Date 2022/10/27 14:32
 * @version 1.0
 * Description:
 */
@FeignClient("bendan-admin")
public interface BendanAdminUserInfoService {

    @GetMapping("/userinfo/loadByUsername")
    UserInfoDto loadUserByUsername(@RequestParam("username") String username);
}
