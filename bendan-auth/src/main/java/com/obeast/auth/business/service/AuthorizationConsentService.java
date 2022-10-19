package com.obeast.auth.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.obeast.auth.business.entity.AuthorizationConsentEntity;
import com.obeast.common.domain.PageObjects;

import java.util.Map;
import java.util.List;


/**
 * @author obeast-dragon
 * Date 2022-10-11 21:02:40
 * @version 1.0
 * Description: 
 */
public interface AuthorizationConsentService extends IService<AuthorizationConsentEntity> {


    /**
     * Description: 分页查询
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @param params 分页参数
     * @return PageObjects<AuthorizationConsentEntity>
     */
    PageObjects<AuthorizationConsentEntity> queryPage(Map<String, Object> params);


    /**
     * Description: 根据Id查询
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @param registeredClientId registeredClientId  of entity
     * @return AuthorizationConsentEntity
     */
     AuthorizationConsentEntity queryById(String registeredClientId);



    /**
     * Description: 查询所有
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @return List<AuthorizationConsentEntity>
     */
    List<AuthorizationConsentEntity> queryAll();


    /**
     * Description: 根据条件查询
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @return List<AuthorizationConsentEntity>
     */
    List<AuthorizationConsentEntity> queryByConditions();




    /**
     * Description: 新增
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @param authorizationConsentEntity entity object
     * @return boolean
     */
    boolean add(AuthorizationConsentEntity authorizationConsentEntity);


    /**
     * Description: 批量新增
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @param data entity objects
     * @return boolean
     */
    boolean addList(List<AuthorizationConsentEntity> data);


    /**
     * Description: 更新
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @param authorizationConsentEntity entity object
     * @return boolean
     */
    boolean replace(AuthorizationConsentEntity authorizationConsentEntity);


    /**
     * Description: 批量更新
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @param data entity objects
     * @return boolean
     */
    boolean replaceList(List<AuthorizationConsentEntity> data);


    /**
     * Description: 根据Id删除
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @param registeredClientId registeredClientId of entity
     * @return boolean
     */
    boolean deleteById(String registeredClientId);


    /**
     * Description: 根据Id批量删除
     * @author obeast-dragon
     * Date: 2022-10-11 21:02:40
     * @param ids list of ids
     * @return boolean
     */
    boolean deleteByIds(List<String> ids);

}

