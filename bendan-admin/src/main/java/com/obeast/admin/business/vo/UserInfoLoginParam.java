package com.obeast.admin.business.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author wxl
 * Date 2022/10/20 11:11
 * @version 1.0
 * Description: 登录参数
 */
@Data
public class UserInfoLoginParam {

    //todo check
    @Schema(name = "用户名")
    String username;

    @Schema(name = "密码")
    String password;

}
