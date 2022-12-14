package com.obeast.security.business.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.obeast.core.domain.PageObjects;
import com.obeast.business.entity.SysRoleEntity;

import java.util.List;


/**
 * @author obeast-dragon
 * Date 2022-11-30 10:42:42
 * @version 1.0
 * Description: 角色表
 */
public interface BendanSysRoleService extends IService<SysRoleEntity> {


    /**
     * Description: 分页查询
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param params 分页参数
     * @return PageObjects<SysRoleEntity>
     */
    PageObjects<SysRoleEntity> queryPage(JSONObject params);


    /**
     * Description: 根据Id查询
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param id id  of entity
     * @return SysRoleEntity
     */
     SysRoleEntity queryById(Long id);



    /**
     * Description: 查询所有
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @return List<SysRoleEntity>
     */
    List<SysRoleEntity> queryAll();


    /**
     * Description: 根据条件查询
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @return List<SysRoleEntity>
     */
    List<SysRoleEntity> queryByConditions();



    /**
     * Description: 新增
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param sysRoleEntity entity object
     * @return boolean
     */
    boolean add(SysRoleEntity sysRoleEntity);


    /**
     * Description: 批量新增
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param data entity objects
     * @return boolean
     */
    boolean addList(List<SysRoleEntity> data);


    /**
     * Description: 更新
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param sysRoleEntity entity object
     * @return boolean
     */
    boolean replace(SysRoleEntity sysRoleEntity);


    /**
     * Description: 批量更新
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param data entity objects
     * @return boolean
     */
    boolean replaceList(List<SysRoleEntity> data);


    /**
     * Description: 根据Id删除
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param id id of entity
     * @return boolean
     */
    boolean deleteById(Long id);


    /**
     * Description: 根据Id批量删除
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param ids list of ids
     * @return boolean
     */
    boolean deleteByIds(List<Long> ids);

}

