package com.obeast.auth.business.service.remote;

import com.obeast.common.config.fegin.FeignConfig;
import com.obeast.common.dto.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wxl
 * Date 2022/10/27 14:32
 * @version 1.0
 * Description:
 */
@FeignClient(value = "bendan-admin", configuration = FeignConfig.class)
public interface BendanAdminUserInfoService {

    @GetMapping("/userinfo/loadByUsername")
    UserInfoDto loadUserByUsername(@RequestParam("username") String username);
}
