package com.obeast.security.business.dao;


import com.obeast.core.base.BaseDao;
import com.obeast.business.entity.BendanSysMenu;
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
public interface BendanSysMenuDao extends BaseDao<BendanSysMenu> {
    Set<BendanSysMenu> listMenusByRoleId(@Param("roleId") Long roleId);
}
