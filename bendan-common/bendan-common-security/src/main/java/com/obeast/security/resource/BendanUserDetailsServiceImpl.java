package com.obeast.security.resource;

import com.obeast.business.entity.SysUserEntity;
import com.obeast.security.business.service.BendanSysUserService;
import com.obeast.security.business.domain.BendanSecurityUser;
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
        SysUserEntity sysUserEntity = userInfo.getSysUser();
        String[] roles = userInfo.getSysRoles().stream().map(item -> UserLoginConstant.ROLE + item.getName()).toArray(String[]::new);
        Set<GrantedAuthority> authorities = new HashSet<>(AuthorityUtils.createAuthorityList(roles));
        return new BendanSecurityUser(
                sysUserEntity.getId(),
                sysUserEntity.getUsername(),
                sysUserEntity.getPassword(),
                sysUserEntity.getEmail(),
                true,
                true,
                true,
                sysUserEntity.getStatus().equals(UserLoginConstant.NORMAL_STATUS),
                authorities
        );
    }
}
