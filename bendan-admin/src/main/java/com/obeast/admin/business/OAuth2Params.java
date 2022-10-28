package com.obeast.admin.business;

import lombok.Data;

/**
 * @author wxl
 * Date 2022/10/28 15:25
 * @version 1.0
 * Description:
 */
@Data
public class OAuth2Params {

    String username;
    String password;
    String grant_type;
    String client_id;
    String client_secret;
}
