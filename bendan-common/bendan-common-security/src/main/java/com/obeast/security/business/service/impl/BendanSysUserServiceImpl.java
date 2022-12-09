package com.obeast.security.business.service.impl;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.core.base.CommonResult;
import com.obeast.core.constant.OauthScopeConstant;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.utils.PageQueryUtils;
import com.obeast.business.entity.BendanSysMenu;
import com.obeast.business.entity.BendanSysRole;
import com.obeast.business.entity.BendanSysUser;
import com.obeast.security.business.dao.BendanSysRoleDao;
import com.obeast.security.business.dao.BendanSysUserDao;
import com.obeast.security.business.service.BendanSysMenuService;
import com.obeast.security.business.service.BendanSysUserService;
import com.obeast.security.business.service.remote.OAuth2TokenRemote;
import com.obeast.business.vo.OAuth2TokenParams;
import com.obeast.business.vo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;


/**
 * @author wxl
 * Date 2022/11/30 9:21
 * @version 1.0
 * Description: 针对表【bendan_sys_user】的数据库操作Service实现
 */
@Service
@RequiredArgsConstructor
public class BendanSysUserServiceImpl extends ServiceImpl<BendanSysUserDao, BendanSysUser>
        implements BendanSysUserService {

    private final BendanSysRoleDao bendanSysRoleDao;

    private final OAuth2TokenRemote OAuth2TokenRemote;

    private final BendanSysMenuService bendanSysMenuService;

    private static final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();



    @Override
    public CommonResult<?> login(String username, String password, HttpServletRequest request) throws LoginException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null){
            throw new LoginException("Authentication header is null, please check your http headers");
        }
        OAuth2TokenParams oAuth2Params = new OAuth2TokenParams();
        oAuth2Params.setGrant_type(AuthorizationGrantType.PASSWORD.getValue());
        oAuth2Params.setUsername(username);
        oAuth2Params.setPassword(password);
        oAuth2Params.setScope(OauthScopeConstant.ALL);
        return OAuth2TokenRemote.getAccessToken(header, oAuth2Params);
    }

    @Override
    public void register(BendanSysUser bendanSysUser) throws LoginException {
        String username = bendanSysUser.getUsername();
        if (username != null) {
            if (this.findByUsername(username) != null){
                throw new LoginException("用户已经存在");
            }
            String password = bendanSysUser.getPassword();
            String encodePassword = encoder.encode(password);
            bendanSysUser.setPassword(encodePassword);
            this.save(bendanSysUser);
        }
    }

    @Override
    public PageObjects<BendanSysUser> queryPage(JSONObject params) {
        String key = params.getStr("orderField");
        QueryWrapper<BendanSysUser> queryWrapper = getWrapper();
        IPage<BendanSysUser> page = this.page(
                new PageQueryUtils<BendanSysUser>().getPage(params, key, false),
                queryWrapper
        );
        return new PageQueryUtils<>().getPageObjects(page, BendanSysUser.class);
    }



    @Override
    public List<BendanSysUser> queryAll() {
        return this.list();
    }

    @Override
    public BendanSysUser queryById(Long userId) {
        if (userId != null) {
            return this.getById(userId);
        }
        return null;
    }


    @Override
    public UserInfo findUserInfo(String username) throws LoginException {
        UserInfo userInfo = new UserInfo();
        BendanSysUser bendanSysUser = this.findByUsername(username);
        if (bendanSysUser != null) {
            List<BendanSysRole> bendanSysRoles = bendanSysRoleDao.listRolesByUserId(bendanSysUser.getId());
            if (bendanSysRoles.isEmpty()){
                throw new LoginException("当前用户没有设置角色");
            }
            List<Long> roleIds = bendanSysRoles.stream().map(BendanSysRole::getId).toList();
            Set<BendanSysMenu> menus =  bendanSysMenuService.getMenusByRoleIds(roleIds);
            userInfo
                    .setBendanSysUser(bendanSysUser)
                    .setBendanSysMenus(menus)
                    .setBendanSysRoles(bendanSysRoles)
                    .setRoleIds(roleIds);
            return userInfo;
        }
        return null;
    }

    @Override
    public BendanSysUser findByUsername(String username) {
        if (username != null) {
            LambdaQueryWrapper<BendanSysUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(BendanSysUser::getUsername, username);
            return this.getOne(wrapper);
        }
        return null;
    }


    /**
     * Description: 自定义QueryWrapper
     *
     * @return QueryWrapper<BendanSysUser>
     * @author obeast-dragon
     * Date 2022-10-11 21:02:40
     */
    private QueryWrapper<BendanSysUser> getWrapper() {
        return new QueryWrapper<>();
    }

}




