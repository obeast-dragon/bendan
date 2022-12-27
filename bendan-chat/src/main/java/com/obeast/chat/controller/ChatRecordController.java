package com.obeast.chat.controller;

import com.obeast.chat.entity.ChatRecordEntity;
import com.obeast.chat.service.ChatRecordService;
import com.obeast.core.base.CommonResult;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.domain.PageParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * @author wxl
 * Date 2022/12/27 12:37
 * @version 1.0
 * Description:
 */
@RestController
@RequestMapping("/chatRecord")
@RequiredArgsConstructor
public class ChatRecordController {

    private final ChatRecordService chatRecordService;

    /**
     * 查询聊天记录
     */
    @GetMapping("/listRecord")
    public CommonResult<PageObjects<ChatRecordEntity>> page(PageParams pageParams, String uuid ) {
        PageObjects<ChatRecordEntity> page = chatRecordService.queryPage(pageParams, uuid);
        return CommonResult.success(page, "page");
    }

}
