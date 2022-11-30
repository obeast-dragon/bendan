package com.obeast.admin.business.service.impl;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.admin.business.dao.BendanSysMenuDao;
import com.obeast.admin.business.dao.BendanSysRoleDao;
import com.obeast.admin.business.dao.BendanSysUserDao;
import com.obeast.admin.business.service.BendanSysMenuService;
import com.obeast.business.entity.BendanSysMenu;
import com.obeast.business.entity.BendanSysRole;
import com.obeast.business.entity.BendanSysUser;
import com.obeast.admin.business.service.BendanSysUserService;
import com.obeast.admin.business.service.remote.OAuth2TokenRemote;
import com.obeast.business.vo.OAuth2TokenParams;
import com.obeast.business.vo.UserInfo;
import com.obeast.core.base.CommonResult;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.utils.PageQueryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
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
public class BendanSysUserServiceImpl extends ServiceImpl<BendanSysUserDao, BendanSysUser> implements BendanSysUserService {


    private final BendanSysRoleDao bendanSysRoleDao;

    private final BendanSysMenuDao bendanSysMenuDao;

    private final OAuth2TokenRemote OAuth2TokenRemote;

    private final BendanSysMenuService bendanSysMenuService;

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
    public CommonResult<?> login(String username, String password) {
        OAuth2TokenParams oAuth2Params = new OAuth2TokenParams();
        oAuth2Params.setClient_id("messaging-client");
        oAuth2Params.setClient_secret("secret");
        oAuth2Params.setGrant_type("password");
        oAuth2Params.setUsername(username);
        oAuth2Params.setPassword(password);
        return OAuth2TokenRemote.getAccessToken(oAuth2Params);
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
            List<BendanSysRole> bendanSysRoles = bendanSysRoleDao.listRolesByUserId(bendanSysUser.getUserId());
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




