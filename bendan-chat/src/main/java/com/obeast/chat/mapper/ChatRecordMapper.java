package com.obeast.chat.mapper;

import com.obeast.chat.entity.ChatRecordEntity;
import com.obeast.core.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wxl
 * Date 2022/12/27 12:37
 * @version 1.0
 * Description: 聊天记录模拟表
 */
@Mapper
public interface ChatRecordMapper extends BaseDao<ChatRecordEntity> {


}
