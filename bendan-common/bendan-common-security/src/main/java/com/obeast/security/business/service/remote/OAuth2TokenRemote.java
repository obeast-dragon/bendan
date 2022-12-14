package com.obeast.security.business.service.remote;

import com.obeast.core.base.CommonResult;
import com.obeast.core.config.fegin.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;


/**
 * @author wxl
 * Date 2022/10/20 11:19
 * @version 1.0
 * Description: bendan-auth调用
 */
@FeignClient(name = "bendan-auth", configuration = FeignConfig.class )
public interface OAuth2TokenRemote {

    @PostMapping(value = "/oauth2/token", consumes =  {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    CommonResult<?> getAccessToken(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorization, OAuth2TokenParams oAuth2TokenParams);




}
