package com.obeast.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.obeast.admin.entity.AdminUserEntity;
import com.obeast.admin.excel.AdminUserExcel;
import com.obeast.oss.domain.PageObjects;

import java.util.Map;
import java.util.List;


/**
 * @author obeast-dragon
 * Date 2022-10-04 00:20:25
 * @version 1.0
 * Description: 管理系统用户
 */
public interface AdminUserService extends IService<AdminUserEntity> {


    /**
     * Description: 分页查询
     * @author obeast-dragon
     * Date: 2022-10-04 00:20:25
     * @param params 分页参数
     * @return PageObjects<AdminUserEntity>
     */
    PageObjects<AdminUserEntity> queryPage(Map<String, Object> params);


    /**
     * Description: 根据Id查询
     * @author obeast-dragon
     * Date: 2022-10-04 00:20:25
     * @param id id  of entity
     * @return AdminUserEntity
     */
     AdminUserEntity queryById(Long id);



    /**
     * Description: 查询所有
     * @author obeast-dragon
     * Date: 2022-10-04 00:20:25
     * @return List<AdminUserEntity>
     */
    List<AdminUserEntity> queryAll();


    /**
     * Description: 根据条件查询
     * @author obeast-dragon
     * Date: 2022-10-04 00:20:25
     * @return List<AdminUserEntity>
     */
    List<AdminUserEntity> queryByConditions();


    /**
     * Description: 根据条件查询Excel
     * @author obeast-dragon
     * Date: 2022-10-04 00:20:25
     * @return List<AdminUserExcel>
     */
    List<AdminUserExcel> queryExcelByConditions();

    /**
     * Description: 新增
     * @author obeast-dragon
     * Date: 2022-10-04 00:20:25
     * @param adminUserEntity entity object
     * @return boolean
     */
    boolean add(AdminUserEntity adminUserEntity);


    /**
     * Description: 批量新增
     * @author obeast-dragon
     * Date: 2022-10-04 00:20:25
     * @param data entity objects
     * @return boolean
     */
    boolean addList(List<AdminUserEntity> data);


    /**
     * Description: 更新
     * @author obeast-dragon
     * Date: 2022-10-04 00:20:25
     * @param adminUserEntity entity object
     * @return boolean
     */
    boolean replace(AdminUserEntity adminUserEntity);


    /**
     * Description: 批量更新
     * @author obeast-dragon
     * Date: 2022-10-04 00:20:25
     * @param data entity objects
     * @return boolean
     */
    boolean replaceList(List<AdminUserEntity> data);


    /**
     * Description: 根据Id删除
     * @author obeast-dragon
     * Date: 2022-10-04 00:20:25
     * @param id id of entity
     * @return boolean
     */
    boolean deleteById(Long id);


    /**
     * Description: 根据Id批量删除
     * @author obeast-dragon
     * Date: 2022-10-04 00:20:25
     * @param ids list of ids
     * @return boolean
     */
    boolean deleteByIds(List<Long> ids);

}

