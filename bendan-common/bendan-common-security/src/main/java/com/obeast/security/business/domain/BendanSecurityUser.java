package com.obeast.security.business.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wxl
 * Date 2022/11/29 17:16
 * @version 1.0
 * Description: User扩展
 */
public class BendanSecurityUser extends User implements OAuth2AuthenticatedPrincipal, Serializable {

    @Serial
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 用户id
     * */
    @JsonSerialize(using = ToStringSerializer.class)
    private final Long id;

    /**
     * 邮箱
     * */
    @JsonSerialize(using = ToStringSerializer.class)
    private final String email;

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public BendanSecurityUser(Long id, String username, String password, String email, boolean enabled,
                              boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
                              Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.email = email;
    }


    /**
     * Get the OAuth 2.0 token attributes
     *
     * @return the OAuth 2.0 token attributes
     */
    @Override
    public Map<String, Object> getAttributes() {
        return new HashMap<>();
    }

    @Override
    public String getName() {
        return this.getUsername();
    }
}
