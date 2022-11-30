package com.obeast.admin.business.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.obeast.business.entity.BendanSysRole;
import com.obeast.core.domain.PageObjects;

import java.util.List;


/**
 * @author obeast-dragon
 * Date 2022-11-30 10:42:42
 * @version 1.0
 * Description: 角色表
 */
public interface BendanSysRoleService extends IService<BendanSysRole> {


    /**
     * Description: 分页查询
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param params 分页参数
     * @return PageObjects<SysRoleEntity>
     */
    PageObjects<BendanSysRole> queryPage(JSONObject params);


    /**
     * Description: 根据Id查询
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param id id  of entity
     * @return SysRoleEntity
     */
     BendanSysRole queryById(Long id);



    /**
     * Description: 查询所有
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @return List<SysRoleEntity>
     */
    List<BendanSysRole> queryAll();


    /**
     * Description: 根据条件查询
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @return List<SysRoleEntity>
     */
    List<BendanSysRole> queryByConditions();



    /**
     * Description: 新增
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param bendanSysRole entity object
     * @return boolean
     */
    boolean add(BendanSysRole bendanSysRole);


    /**
     * Description: 批量新增
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param data entity objects
     * @return boolean
     */
    boolean addList(List<BendanSysRole> data);


    /**
     * Description: 更新
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param bendanSysRole entity object
     * @return boolean
     */
    boolean replace(BendanSysRole bendanSysRole);


    /**
     * Description: 批量更新
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param data entity objects
     * @return boolean
     */
    boolean replaceList(List<BendanSysRole> data);


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

