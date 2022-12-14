package com.obeast.business.vo;

import lombok.Data;

/**
 * @author wxl
 * Date 2022/10/28 15:25
 * @version 1.0
 * Description: OAuth2PasswordVo
 */
@Data
public class OAuth2PasswordVo {

    String username;

    String password;

    String grant_type;

    String scope;
}
