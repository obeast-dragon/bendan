package com.obeast.security.business.service.remote;

import com.obeast.business.vo.OAuth2PasswordVo;
import com.obeast.business.vo.OAuth2RefreshVo;
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
public interface OAuth2TokenEndpoint {

    /**
     * Description: 密码模式
     * @author wxl
     * Date: 2022/12/12 11:27
     * @param authorization    authorization
     * @param oAuth2PasswordVo  oAuth2TokenParams
     * @return com.worldintek.core.base.CommonResult<?>
     */
    @PostMapping(value = "/oauth2/token", consumes =  {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    CommonResult<?> authPassword(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorization, OAuth2PasswordVo oAuth2PasswordVo);



    /**
     * Description: 密码模式
     * @author wxl
     * Date: 2022/12/12 11:27
     * @param authorization    authorization
     * @param oAuth2RefreshVo  oAuth2RefreshVo
     * @return com.worldintek.core.base.CommonResult<?>
     */
    @PostMapping(value = "/oauth2/token", consumes =  {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    CommonResult<?> authRefresh(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorization, OAuth2RefreshVo oAuth2RefreshVo);


}
