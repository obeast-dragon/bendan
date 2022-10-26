package com.obeast.auth.business.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("oauth2_authorization")
public class BendanOAuth2Authorization implements Serializable {
    @TableId
    private String id;
    private String registeredClientId;
    private String principalName;
    private String authorizationGrantType;
    private String attributes;
    private String state;
    private String authorizationCodeValue;
    private LocalDateTime authorizationCodeIssuedAt;
    private LocalDateTime authorizationCodeExpiresAt;
    private String authorizationCodeMetadata;
    private String accessTokenValue;
    private LocalDateTime accessTokenIssuedAt;
    private LocalDateTime accessTokenExpiresAt;
    private String accessTokenMetadata;
    private String accessTokenType;
    private String accessTokenScopes;
    private String oidcIdTokenValue;
    private LocalDateTime oidcIdTokenIssuedAt;
    private LocalDateTime oidcIdTokenExpiresAt;
    private String oidcIdTokenMetadata;
    private String refreshTokenValue;
    private LocalDateTime refreshTokenIssuedAt;
    private LocalDateTime refreshTokenExpiresAt;
    private String refreshTokenMetadata;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
