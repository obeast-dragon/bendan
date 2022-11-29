package com.obeast.auth.business.service.impl;

import com.obeast.auth.business.service.remote.BendanAdminUserInfoService;
import com.obeast.common.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Component
public class BendanUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BendanAdminUserInfoService bendanAdminUserInfoService;

    @Autowired
    private HttpServletRequest request;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String clientId = request.getParameter("client_id");
        //todo 判断客户端ID
        UserInfoDto userInfoDto = bendanAdminUserInfoService.loadUserByUsername(username);
        if (userInfoDto == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        userInfoDto.setClientId(clientId);

        return buildUser(userInfoDto);

    }

    private UserDetails buildUser(UserInfoDto userInfoDto) {
        //todo 权限
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("USER"));
        return User.builder()
                .username(userInfoDto.getUsername())
                .password(userInfoDto.getPassword())
                .authorities(authorities)
                .build();
    }
}
