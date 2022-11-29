package com.obeast.core.dto;

import lombok.Data;

import java.util.List;

/**
 * @author wxl
 * Date 2022/10/27 14:39
 * @version 1.0
 * Description: UserInfoDto
 */
@Data
public class UserInfoDto {
    private Long id;
    private String username;
    private String password;
    private Integer status;
    private String clientId;
    private List<String> roles;

}
