package com.obeast.blog.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.obeast.blog.entity.UserInfoEntity;
import com.obeast.blog.excel.UserInfoExcel;
import com.obeast.oss.domain.PageObjects;

import java.util.Map;
import java.util.List;


/**
 * @author obeast-dragon
 * Date 2022-09-21 12:18:12
 * @version 1.0
 * Description: 
 */
public interface UserInfoService extends IService<UserInfoEntity> {


    /**
     * Description: 分页查询
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @param params 分页参数
     * @return PageObjects<UserInfoEntity>
     */
    PageObjects<UserInfoEntity> queryPage(Map<String, Object> params);


    /**
     * Description: 根据Id查询
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @param id id  of entity
     * @return UserInfoEntity
     */
     UserInfoEntity queryById(Long id);



    /**
     * Description: 查询所有
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @return List<UserInfoEntity>
     */
    List<UserInfoEntity> queryAll();


    /**
     * Description: 根据条件查询
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @return List<UserInfoEntity>
     */
    List<UserInfoEntity> queryByConditions();


    /**
     * Description: 根据条件查询Excel
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @return List<UserInfoExcel>
     */
    List<UserInfoExcel> queryExcelByConditions();

    /**
     * Description: 新增
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @param userInfoEntity entity object
     * @return boolean
     */
    boolean add(UserInfoEntity userInfoEntity);


    /**
     * Description: 批量新增
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @param data entity objects
     * @return boolean
     */
    boolean addList(List<UserInfoEntity> data);


    /**
     * Description: 更新
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @param userInfoEntity entity object
     * @return boolean
     */
    boolean replace(UserInfoEntity userInfoEntity);


    /**
     * Description: 批量更新
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @param data entity objects
     * @return boolean
     */
    boolean replaceList(List<UserInfoEntity> data);


    /**
     * Description: 根据Id删除
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @param id id of entity
     * @return boolean
     */
    boolean deleteById(Long id);


    /**
     * Description: 根据Id批量删除
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @param ids list of ids
     * @return boolean
     */
    boolean deleteByIds(List<Long> ids);

}

