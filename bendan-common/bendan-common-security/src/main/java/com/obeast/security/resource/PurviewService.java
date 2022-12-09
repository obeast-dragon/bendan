package com.obeast.security.resource;

import cn.hutool.core.util.ArrayUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * @author wxl
 * Date 2022/12/7 16:52
 * @version 1.0
 * Description: 接口鉴权
 */
public class PurviewService {

    /**
     * 判断接口是否有任意xxx，xxx权限
     * @param permissions 权限
     * @return {boolean}
     */
    public boolean hasPermission(String... permissions) {
        if (ArrayUtil.isEmpty(permissions)) {
            return false;
        }
//        获取bean Authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().map(GrantedAuthority::getAuthority).filter(StringUtils::hasText)
                .anyMatch(x -> PatternMatchUtils.simpleMatch(permissions, x));
    }
}
