package com.obeast.auth.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@JsonIgnoreType
public class AuthUser extends User {
    @Getter
    @Setter
    private Long userId;
    public AuthUser(String username,
                    String password,
                    Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
