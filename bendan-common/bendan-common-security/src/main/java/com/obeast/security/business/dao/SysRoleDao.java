package com.obeast.security.business.dao;


import com.obeast.core.base.BaseDao;
import com.obeast.business.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
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
public interface SysRoleDao extends BaseDao<SysRoleEntity> {
    /**
     * Description: 查询角色s by userId
     * @author wxl
     * Date: 2022/12/11 14:48
     * @param userId  userId
     * @return java.util.List<com.worldintek.business.entity.SysRole>
     */
    List<SysRoleEntity> listRolesByUserId(@Param("userId") Long userId);

    /**
     * Description: 添加角色用户关系
     * @author wxl
     * Date: 2022/12/11 14:48
     * @param userId   userId
     * @param roleId    roleId
     * @return java.lang.Boolean
     */
    @Insert("insert into bendan_sys_user_role (user_id, role_id) values(#{userId}, #{roleId})")
    Boolean addRelation(@Param("userId") Long userId, @Param("roleId") Long roleId);


    /**
     * Description: 删除关系 by userId
     * @author wxl
     * Date: 2022/12/11 14:48
     * @param userId    userId
     * @return java.lang.Boolean
     */
    @Delete("delete from bendan_sys_user_role as ur where ur.user_id = #{userId}")
    Boolean delRelsByUserId(@Param("userId") Long userId);
}
