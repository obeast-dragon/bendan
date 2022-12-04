package com.obeast.security.business.service.impl;

import com.obeast.entity.BendanSysUser;
import com.obeast.security.business.service.BendanSysUserService;
import com.obeast.vo.UserInfo;
import com.obeast.core.constant.UserLoginConstant;
import com.obeast.security.business.domain.BendanSecurityUser;
import com.obeast.security.business.service.BendanUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import javax.security.auth.login.LoginException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class BendanUserDetailsServiceImpl implements BendanUserDetailsService {

    private final BendanSysUserService bendanSysUserService;

    /**
     * 二级缓存
     * */
//    private final CacheManager cacheManager;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Cache cache = cacheManager.getCache(UserLoginConstant.USERINFO);
//        if (cache != null && cache.get(username) != null) {
//            return (BendanSecurityUser) cache.get(username).get();
//        }
        UserDetails userDetails = getUserDetails(username);
//        if (cache != null){
//            cache.put(username, userDetails);
//        }
        return userDetails;
    }

    private UserDetails getUserDetails(String username) throws LoginException {
        UserInfo userInfo = Optional.ofNullable(bendanSysUserService.findUserInfo(username)).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        // TODO: 2022/11/30 资源控制
        BendanSysUser sysUser = userInfo.getBendanSysUser();
        String[] roles = userInfo.getBendanSysRoles().stream().map(item -> UserLoginConstant.ROLE + item.getName()).toArray(String[]::new);
        Set<GrantedAuthority> authorities = new HashSet<>(AuthorityUtils.createAuthorityList(roles));
        return new BendanSecurityUser(
                sysUser.getId(),
                sysUser.getUsername(),
                sysUser.getPassword(),
                sysUser.getEmail(),
                true,
                true,
                true,
                sysUser.getLockStatus().equals(UserLoginConstant.NORMAL_STATUS),
                authorities
        );
    }
}
