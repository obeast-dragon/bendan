package com.obeast.security.business.service.impl;

import com.obeast.business.entity.BendanSysUser;
import com.obeast.core.base.CommonResult;
import com.obeast.security.business.service.BendanUserDetailsService;
import com.obeast.security.business.service.remote.BendanSysUserRemote;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
@RequiredArgsConstructor
public class BendanUserDetailsServiceImpl implements BendanUserDetailsService {


    private final BendanSysUserRemote bendanSysUserRemote;

    private final HttpServletRequest request;

    /**
     * 二级缓存
     * */
    private final CacheManager cacheManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CommonResult<BendanSysUser> result = bendanSysUserRemote.getUserinfo(username);

        return null;
    }
}
