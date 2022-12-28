package com.obeast.security.business.dao;


import com.obeast.core.base.BaseDao;
import com.obeast.business.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wxl
 * Date 2022/11/30 9:20
 * @version 1.0
 * Description: 针对表【bendan_sys_user】的数据库操作Mapper
 */
@Mapper
public interface SysUserDao extends BaseDao<SysUserEntity> {

    @Select("select distinct COALESCE(userB_id, userA_id) as `friends` from friend_rel where userA_id = #{userId} or userB_id = #{userId}")
    List<Long> getFriends(@Param("userId") Long userId);
}




