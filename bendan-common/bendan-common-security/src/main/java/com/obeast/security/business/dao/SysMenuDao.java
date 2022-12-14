package com.obeast.security.business.dao;


import com.obeast.core.base.BaseDao;
import com.obeast.business.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;


/**
 * @author obeast-dragon
 * Date 2022-11-30 10:42:42
 * @version 1.0
 * Description: 菜单(权限)表
 */
@Mapper
public interface SysMenuDao extends BaseDao<SysMenuEntity> {
    /**
     * Description: 根据roleId查询权限集合
     * @author wxl
     * Date: 2022/12/12 9:36
     * @param roleId    roleId
     * @return java.util.Set<com.worldintek.business.entity.SysMenu>
     */
    Set<SysMenuEntity> listMenusByRoleId(@Param("roleId") Long roleId);

    /**
     * Description: 新增关系
     * @author wxl
     * Date: 2022/12/12 9:43
     * @param roleId    roleId
     * @param menuId    menuId
     * @return java.lang.Boolean
     */
    @Insert("insert into bendan_sys_role_menu (role_id, menu_id) values (#{roleId}, #{menuId})")
    Boolean addRoleMenuRel(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    /**
     * Description: 删除关系
     * @author wxl
     * Date: 2022/12/12 9:43
     * @param roleId    roleId
     * @return java.lang.Boolean
     */
    @Delete("delete from bendan_sys_role_menu where role_id  = #{roleId};")
    Boolean delRelsByRoleId(@Param("roleId") Long roleId);

}
