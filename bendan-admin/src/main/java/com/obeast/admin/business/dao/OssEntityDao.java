package com.obeast.admin.business.dao;

import com.obeast.business.entity.OssEntity;
import com.obeast.core.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Future;


/**
 * @author obeast-dragon
 * Date 2022-09-21 15:19:09
 * @version 1.0
 * Description:  OssEntityDao
 */
@Mapper
public interface OssEntityDao extends BaseDao<OssEntity> {

    @Async
    default void insertAsync(String uploadUrl, Long userId, String filename) {
        OssEntity ossEntity = new OssEntity();
        ossEntity.setUserId(userId);
        ossEntity.setFileName(filename);
        ossEntity.setUrl(uploadUrl);
        this.insert(ossEntity);
//        return new AsyncResult<>(ossEntity);
    }

}
