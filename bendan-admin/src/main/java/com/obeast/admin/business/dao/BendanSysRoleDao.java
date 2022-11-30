package com.obeast.admin.business.dao;



import com.obeast.business.entity.BendanSysRole;
import com.obeast.core.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author obeast-dragon
 * Date 2022-11-30 10:42:42
 * @version 1.0
 * Description: 角色表
 */
@Mapper
public interface BendanSysRoleDao extends BaseDao<BendanSysRole> {

    List<BendanSysRole> listRolesByUserId(@Param("userId") Long userId);
}
