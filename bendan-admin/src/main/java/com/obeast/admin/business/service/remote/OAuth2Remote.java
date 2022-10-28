package com.obeast.admin.business.service.remote;

import com.obeast.admin.business.OAuth2Params;
import com.obeast.common.base.CommonResult;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.Map;

/**
 * @author wxl
 * Date 2022/10/20 11:19
 * @version 1.0
 * Description: bendan-auth调用
 */
@FeignClient("bendan-auth")
public interface OAuth2Remote {

    @PostMapping(value = "/oauth2/token", consumes =  {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    Object getAccessToken(OAuth2Params oAuth2Params);


}
