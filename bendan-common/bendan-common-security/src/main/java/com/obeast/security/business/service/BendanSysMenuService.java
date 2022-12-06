package com.obeast.security.business.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.obeast.core.domain.PageObjects;
import com.obeast.entity.BendanSysMenu;

import java.util.List;
import java.util.Set;


/**
 * @author obeast-dragon
 * Date 2022-11-30 10:42:42
 * @version 1.0
 * Description: 菜单(权限)表
 */
public interface BendanSysMenuService extends IService<BendanSysMenu> {


    /**
     * Description: 分页查询
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param params 分页参数
     * @return PageObjects<SysMenuEntity>
     */
    PageObjects<BendanSysMenu> queryPage(JSONObject params);


    /**
     * Description: 根据Id查询
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param id id  of entity
     * @return SysMenuEntity
     */
     BendanSysMenu queryById(Long id);



    /**
     * Description: 查询所有
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @return List<SysMenuEntity>
     */
    List<BendanSysMenu> queryAll();


    /**
     * Description: 根据条件查询
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @return List<SysMenuEntity>
     */
    List<BendanSysMenu> queryByConditions();



    /**
     * Description: 新增
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param bendanSysMenu entity object
     * @return boolean
     */
    boolean add(BendanSysMenu bendanSysMenu);


    /**
     * Description: 批量新增
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param data entity objects
     * @return boolean
     */
    boolean addList(List<BendanSysMenu> data);


    /**
     * Description: 更新
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param bendanSysMenu entity object
     * @return boolean
     */
    boolean replace(BendanSysMenu bendanSysMenu);


    /**
     * Description: 批量更新
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param data entity objects
     * @return boolean
     */
    boolean replaceList(List<BendanSysMenu> data);


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

    /**
     * Description: 根据角色Id获取 权限集合
     * @author wxl
     * Date: 2022/11/30 15:27
     * @param roleIds roleIds
     * @return java.util.Set<com.obeast.business.entity.BendanSysMenu>
     */
    Set<BendanSysMenu> getMenusByRoleIds(List<Long> roleIds);

    /**
     * Description: 根据title获取id
     * @author wxl
     * Date: 2022/12/5 10:38
     * @param title title
     * @return java.lang.Long
     */
    Long getIdByTitle(String title) throws NullPointerException;
}

