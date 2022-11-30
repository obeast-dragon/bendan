package com.obeast.security.business.service.remote;

import com.obeast.business.entity.BendanSysUser;
import com.obeast.business.vo.UserInfo;
import com.obeast.core.base.CommonResult;
import com.obeast.core.config.fegin.FeignConfig;
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
public interface BendanSysUserRemote {

    @GetMapping("/sysUser/getUserinfo")
    CommonResult<UserInfo> getUserinfo(@RequestParam("username") String username);
}
