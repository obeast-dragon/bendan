package com.obeast.security.business.domain;


import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

import java.io.Serializable;


/**
 * @author wxl
 * Date 2022/10/31 10:55
 * @version 1.0
 * Description: 自定义AccessTokenResponse返回结果
 */
@Data
@Accessors(chain = true)
public class OAuth2TokenRes implements Serializable {

    private OAuth2AccessTokenResponse oauth2AccessTokenResponse;

    private long accessTokenExpiresIn;

    private long refreshExpiresIn;


}