package com.obeast.auth.business.dao;

import com.obeast.auth.business.entity.UserInfoEntity;
import com.obeast.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author obeast-dragon
 * Date 2022-10-11 21:02:40
 * @version 1.0
 * Description: 
 */
@Mapper
public interface UserInfoDao extends BaseDao<UserInfoEntity> {

}
