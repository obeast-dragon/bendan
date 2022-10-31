package com.obeast.admin.business.service.remote;

import com.obeast.admin.business.OAuth2Params;
import com.obeast.common.base.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * @author wxl
 * Date 2022/10/20 11:19
 * @version 1.0
 * Description: bendan-auth调用
 */
@FeignClient("bendan-auth")
public interface OAuth2Remote {

    @PostMapping(value = "/oauth2/token", consumes =  {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    CommonResult<?> getAccessToken(OAuth2Params oAuth2Params);


}
