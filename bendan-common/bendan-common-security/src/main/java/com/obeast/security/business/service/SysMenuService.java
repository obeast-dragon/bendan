package com.obeast.security.business.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.obeast.business.dto.SysMenuDTO;
import com.obeast.core.domain.PageObjects;
import com.obeast.business.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;


/**
 * @author obeast-dragon
 * Date 2022-11-30 10:42:42
 * @version 1.0
 * Description: 菜单(权限)表
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    /**
     * Description: 新增关系
     * @author wxl
     * Date: 2022/12/12 9:43
     * @param roleId    roleId
     * @param menuId    menuId
     * @return java.lang.Boolean
     */
    Boolean addRoleMenuRel(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    /**
     * Description: 新增关系s
     * @author wxl
     * Date: 2022/12/12 9:43
     * @param roleId    roleId
     * @param menuIds    menuIds
     * @return java.lang.Boolean
     */
    Boolean addRoleMenuRels(List<Long> menuIds, Long roleId);

    /**
     * Description: 删除关系
     * @author wxl
     * Date: 2022/12/12 9:43
     * @param roleId    roleId
     * @return java.lang.Boolean
     */
    Boolean delRelsByRoleId(@Param("roleId") Long roleId);

    /**
     * Description: 清除菜单缓存
     * @author wxl
     * Date: 2022/12/9 13:39
     */
    void clearMenuCache();

    /**
     * Description: 分页查询
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param params 分页参数
     * @return PageObjects<SysMenuEntity>
     */
    PageObjects<SysMenuEntity> queryPage(JSONObject params);


    /**
     * Description: 根据Id查询
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param id id  of entity
     * @return SysMenuEntityEntity
     */
    SysMenuEntity queryById(Long id);



    /**
     * Description: 查询所有
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @return List<SysMenuEntityEntity>
     */
    List<SysMenuEntity> queryAll();


    /**
     * Description: 根据条件查询
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @return List<SysMenuEntityEntity>
     */
    List<SysMenuEntity> queryByConditions();



    /**
     * Description: 新增
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param sysMenus SysMenuEntitys
     * @return boolean
     */
    Boolean addMenus(List<SysMenuEntity> sysMenus);


    /**
     * Description: 更新
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param sysMenuDTO entity object
     * @return boolean
     */
    Boolean replace(SysMenuDTO sysMenuDTO);


    /**
     * Description: 批量更新
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param data entity objects
     * @return boolean
     */
    Boolean replaceList(List<SysMenuEntity> data);


    /**
     * Description: 根据Id删除
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param id id of entity
     * @return boolean
     */
    Boolean deleteById(Long id);


    /**
     * Description: 根据角色Ids获取 权限集合
     * @author wxl
     * Date: 2022/11/30 15:27
     * @param roleIds roleIds
     * @return java.util.Set<com.worldintek.business.entity.BendanSysMenu>
     */
    Set<SysMenuEntity> getMenusByRoleIds(List<Long> roleIds);

    /**
     * Description: 根据角色Ids获取 权限集合
     * @author wxl
     * Date: 2022/12/9 13:36
     * @param roleId  roleId
     * @return java.util.Set<com.worldintek.business.entity.SysMenu>
     */
    Set<SysMenuEntity> getMenusByRoleId(Long roleId);

    /**
     * Description: 根据title获取id
     * @author wxl
     * Date: 2022/12/5 10:38
     * @param title title
     * @return java.lang.Long
     */
    Long getIdByTitle(String title) throws NullPointerException;

    /**
     * Description: 根据id判断菜单是否存在
     * @author wxl
     * Date: 2022/12/12 10:34
     * @param menuId  menuId
     * @return boolean
     */
    Boolean isMenuExistById(Long menuId);

    /**
     * Description: 根据角色id查询菜单树
     *
     * @param roleIds roleIds
     * @param isLazy    isLazy
     * @return com.worldintek.business.entity.SysMenu
     * @author wxl
     * Date: 2022/12/14 10:06
     */
    List<SysMenuEntity> treeByRoleId(List<Long> roleIds, Boolean isLazy);
}

