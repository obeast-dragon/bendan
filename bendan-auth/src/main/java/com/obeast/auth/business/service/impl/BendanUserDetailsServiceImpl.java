package com.obeast.auth.business.service.impl;

import com.obeast.auth.business.service.remote.BendanAdminUserInfoService;
import com.obeast.core.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BendanUserDetailsServiceImpl implements UserDetailsService {

    private final BendanAdminUserInfoService bendanAdminUserInfoService;

    private final HttpServletRequest request;

    /**
     * 二级缓存
     * */
    private final CacheManager cacheManager;


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
