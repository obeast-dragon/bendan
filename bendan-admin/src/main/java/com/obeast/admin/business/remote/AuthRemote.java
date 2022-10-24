package com.obeast.admin.business.remote;

import com.obeast.common.base.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author wxl
 * Date 2022/10/20 11:19
 * @version 1.0
 * Description: bendan-auth调用
 */
@FeignClient("bendan-auth")
public interface AuthRemote {

    @PostMapping("/oauth2/token")
    CommonResult<?> getAccessToken(@RequestParam Map<String, String> params);

}
