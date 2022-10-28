package com.obeast.admin.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.obeast.admin.business.entity.UserInfoEntity;
import com.obeast.common.base.CommonResult;
import com.obeast.common.domain.PageObjects;
import com.obeast.common.to.UserInfoDto;

import java.util.Map;
import java.util.List;


/**
 * @author obeast-dragon
 * Date 2022-10-11 21:02:40
 * @version 1.0
 * Description: 
 */
public interface UserInfoService extends IService<UserInfoEntity> {


    /**
     * 注册功能
     */
//     register();

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 调用认证中心返回结果
     */
    CommonResult<?> login(String username, String password);



    UserInfoDto loadUserByUsername(String username);


    /**
     * Description: 分页查询
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @param params 分页参数
     * @return PageObjects<UserInfoEntity>
     */
    PageObjects<UserInfoEntity> queryPage(Map<String, Object> params);


    /**
     * Description: 根据Id查询
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @param userId userId  of entity
     * @return UserInfoEntity
     */
     UserInfoEntity queryById(Long userId);



    /**
     * Description: 查询所有
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @return List<UserInfoEntity>
     */
    List<UserInfoEntity> queryAll();


    /**
     * Description: 根据条件查询
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @return List<UserInfoEntity>
     */
    List<UserInfoEntity> queryByConditions();




    /**
     * Description: 新增
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @param userInfoEntity entity object
     * @return boolean
     */
    boolean add(UserInfoEntity userInfoEntity);


    /**
     * Description: 批量新增
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @param data entity objects
     * @return boolean
     */
    boolean addList(List<UserInfoEntity> data);


    /**
     * Description: 更新
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @param userInfoEntity entity object
     * @return boolean
     */
    boolean replace(UserInfoEntity userInfoEntity);


    /**
     * Description: 批量更新
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @param data entity objects
     * @return boolean
     */
    boolean replaceList(List<UserInfoEntity> data);


    /**
     * Description: 根据Id删除
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @param userId userId of entity
     * @return boolean
     */
    boolean deleteById(Long userId);


    /**
     * Description: 根据Id批量删除
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @param ids list of ids
     * @return boolean
     */
    boolean deleteByIds(List<Long> ids);


}

