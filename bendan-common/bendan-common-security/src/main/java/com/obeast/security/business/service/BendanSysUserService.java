package com.obeast.security.business.service;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.obeast.core.base.CommonResult;
import com.obeast.core.domain.PageObjects;
import com.obeast.business.entity.SysUserEntity;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author wxl
 * Date 2022/11/30 9:20
 * @version 1.0
 * Description: 针对表【bendan_sys_user】的数据库操作Service
 */
public interface BendanSysUserService extends IService<SysUserEntity> {


    /**
     * Description: 登录
     *
     * @param username username
     * @param password password
     * @param request request
     * @return com.obeast.core.base.CommonResult<?>
     * @author wxl
     * Date: 2022/11/30 9:56
     */
    CommonResult<?> login(String username, String password, HttpServletRequest request) throws LoginException;

    /**
     * Description: 注册
     * @author wxl
     * Date: 2022/12/1 18:53
     * @param sysUserEntity  bendanSysUser
     */
    void register(SysUserEntity sysUserEntity) throws LoginException;

//    ----------------------default------------------------------------------

    /**
     * Description: 查询用户详情
     *
     * @author wxl
     * Date: 2022/11/21 15:37
     * @param username username
     * @return UserInfo
     */
    UserInfo findUserInfo(String username) throws LoginException;

    /**
     * Description: 分页查询
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @param params 分页参数
     * @return PageObjects<UserInfoEntity>
     */
    PageObjects<SysUserEntity> queryPage(JSONObject params);



    /**
     * Description: 查询所有
     * @author wxl
     * Date: 2022/11/30 10:26
     * @return java.util.List<com.obeast.admin.business.entity.UserInfoEntity>
     */
    List<SysUserEntity> queryAll();


    /**
     * Description: 根据Id查询
     * @author wxl
     * Date: 2022/11/30 10:27
     * @param userId   userId
     * @return com.obeast.admin.business.entity.BendanSysUser
     */
    SysUserEntity queryById(Long userId);


    /**
     * Description: 查询用户 by username
     * @author wxl
     * Date: 2022/11/30 15:06
     * @param username  username
     * @return com.obeast.business.entity.BendanSysUser
     */
    SysUserEntity findByUsername(String username);


}
