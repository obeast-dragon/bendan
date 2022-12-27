package com.obeast.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.chat.entity.ChatRecordEntity;
import com.obeast.chat.mapper.ChatRecordMapper;
import com.obeast.chat.service.ChatRecordService;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.domain.PageParams;
import com.obeast.core.utils.PageQueryUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


/**
 * @author wxl
 * Date 2022/12/27 13:19
 * @version 1.0
 * Description: 聊天记录模拟表
 */
@Service
public class ChatRecordServiceImpl extends ServiceImpl<ChatRecordMapper, ChatRecordEntity> implements ChatRecordService {

    @Override
    public PageObjects<ChatRecordEntity> queryPage(PageParams pageParams, String uuid) {
        Assert.isNull(uuid, "uuid cannot be null");
        QueryWrapper<ChatRecordEntity> queryWrapper = new QueryWrapper<ChatRecordEntity>()
                .eq("from_uuid", uuid)
                .or()
                .eq("to_uuid", uuid);
        IPage<ChatRecordEntity> page = this.baseMapper.selectPage(
                new PageQueryUtils<ChatRecordEntity>().getPage(pageParams),
                queryWrapper
        );
        return new PageQueryUtils<>().getPageObjects(page, ChatRecordEntity.class);
    }
}
