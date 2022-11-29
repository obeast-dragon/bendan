package com.obeast.admin.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.obeast.admin.business.entity.SysMenuEntity;
import com.obeast.core.domain.PageObjects;

import java.util.Map;
import java.util.List;


/**
 * @adminor obeast-dragon
 * Date 2022-10-12 20:10:00
 * @version 1.0
 * Description: 菜单表
 */
public interface SysMenuService extends IService<SysMenuEntity> {


    /**
     * Description: 分页查询
     * @adminor obeast-dragon
     * Date: 2022-10-12 20:10:00
     * @param params 分页参数
     * @return PageObjects<SysMenuEntity>
     */
    PageObjects<SysMenuEntity> queryPage(Map<String, Object> params);


    /**
     * Description: 根据Id查询
     * @adminor obeast-dragon
     * Date: 2022-10-12 20:10:00
     * @param id id  of entity
     * @return SysMenuEntity
     */
     SysMenuEntity queryById(Long id);



    /**
     * Description: 查询所有
     * @adminor obeast-dragon
     * Date: 2022-10-12 20:10:00
     * @return List<SysMenuEntity>
     */
    List<SysMenuEntity> queryAllTree();


    /**
     * Description: 根据条件查询
     * @adminor obeast-dragon
     * Date: 2022-10-12 20:10:00
     * @return List<SysMenuEntity>
     */
    List<SysMenuEntity> queryByConditions();


    /**
     * Description: 新增
     * @adminor obeast-dragon
     * Date: 2022-10-12 20:10:00
     * @param sysMenuEntity entity object
     * @return boolean
     */
    boolean add(SysMenuEntity sysMenuEntity);


    /**
     * Description: 批量新增
     * @adminor obeast-dragon
     * Date: 2022-10-12 20:10:00
     * @param data entity objects
     * @return boolean
     */
    boolean addList(List<SysMenuEntity> data);


    /**
     * Description: 更新
     * @adminor obeast-dragon
     * Date: 2022-10-12 20:10:00
     * @param sysMenuEntity entity object
     * @return boolean
     */
    boolean replace(SysMenuEntity sysMenuEntity);


    /**
     * Description: 批量更新
     * @adminor obeast-dragon
     * Date: 2022-10-12 20:10:00
     * @param data entity objects
     * @return boolean
     */
    boolean replaceList(List<SysMenuEntity> data);


    /**
     * Description: 根据Id删除
     * @adminor obeast-dragon
     * Date: 2022-10-12 20:10:00
     * @param id id of entity
     * @return boolean
     */
    boolean deleteById(Long id);


    /**
     * Description: 根据Id批量删除
     * @adminor obeast-dragon
     * Date: 2022-10-12 20:10:00
     * @param ids list of ids
     * @return boolean
     */
    boolean deleteByIds(List<Long> ids);

}

