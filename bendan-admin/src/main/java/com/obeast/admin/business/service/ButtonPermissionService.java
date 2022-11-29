package com.obeast.admin.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.obeast.admin.business.entity.ButtonPermissionEntity;
import com.obeast.core.domain.PageObjects;

import java.util.Map;
import java.util.List;


/**
 * @author obeast-dragon
 * Date 2022-10-12 20:10:00
 * @version 1.0
 * Description: 按钮权限表
 */
public interface ButtonPermissionService extends IService<ButtonPermissionEntity> {


    /**
     * Description: 分页查询
     * @author obeast-dragon
     * Date: 2022-10-12 20:10:00
     * @param params 分页参数
     * @return PageObjects<ButtonPermissionEntity>
     */
    PageObjects<ButtonPermissionEntity> queryPage(Map<String, Object> params);


    /**
     * Description: 根据Id查询
     * @author obeast-dragon
     * Date: 2022-10-12 20:10:00
     * @param id id  of entity
     * @return ButtonPermissionEntity
     */
     ButtonPermissionEntity queryById(Long id);



    /**
     * Description: 查询所有
     * @author obeast-dragon
     * Date: 2022-10-12 20:10:00
     * @return List<ButtonPermissionEntity>
     */
    List<ButtonPermissionEntity> queryAll();


    /**
     * Description: 根据条件查询
     * @author obeast-dragon
     * Date: 2022-10-12 20:10:00
     * @return List<ButtonPermissionEntity>
     */
    List<ButtonPermissionEntity> queryByConditions();




    /**
     * Description: 新增
     * @author obeast-dragon
     * Date: 2022-10-12 20:10:00
     * @param buttonPermissionEntity entity object
     * @return boolean
     */
    boolean add(ButtonPermissionEntity buttonPermissionEntity);


    /**
     * Description: 批量新增
     * @author obeast-dragon
     * Date: 2022-10-12 20:10:00
     * @param data entity objects
     * @return boolean
     */
    boolean addList(List<ButtonPermissionEntity> data);


    /**
     * Description: 更新
     * @author obeast-dragon
     * Date: 2022-10-12 20:10:00
     * @param buttonPermissionEntity entity object
     * @return boolean
     */
    boolean replace(ButtonPermissionEntity buttonPermissionEntity);


    /**
     * Description: 批量更新
     * @author obeast-dragon
     * Date: 2022-10-12 20:10:00
     * @param data entity objects
     * @return boolean
     */
    boolean replaceList(List<ButtonPermissionEntity> data);


    /**
     * Description: 根据Id删除
     * @author obeast-dragon
     * Date: 2022-10-12 20:10:00
     * @param id id of entity
     * @return boolean
     */
    boolean deleteById(Long id);


    /**
     * Description: 根据Id批量删除
     * @author obeast-dragon
     * Date: 2022-10-12 20:10:00
     * @param ids list of ids
     * @return boolean
     */
    boolean deleteByIds(List<Long> ids);

}

