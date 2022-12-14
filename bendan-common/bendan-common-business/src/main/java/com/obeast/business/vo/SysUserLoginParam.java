package com.obeast.business.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author wxl
 * Date 2022/10/20 11:11
 * @version 1.0
 * Description: 登录参数
 */
@Data
public class SysUserLoginParam {

    @Schema(description = "用户名")
    String username;

    @Schema(description = "密码")
    String password;
}
