package com.obeast.admin.dao;

import com.obeast.oss.base.BaseDao;
import com.obeast.admin.entity.AdminUserEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author obeast-dragon
 * Date 2022-10-04 00:20:25
 * @version 1.0
 * Description: 管理系统用户
 */
@Mapper
public interface AdminUserDao extends BaseDao<AdminUserEntity> {

}
