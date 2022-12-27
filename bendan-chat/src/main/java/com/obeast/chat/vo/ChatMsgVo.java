package com.obeast.chat.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wxl
 * Date 2022/12/27 14:16
 * @version 1.0
 * Description: 封装的前端数据 + 时间
 */
@Data
public class ChatMsgVo implements Serializable {
    /**
     * fromId 来自谁的ID
     * */
    private String fromUuid;


    /**
     * toId 去向谁的的ID
     * */
    private String toUuid;

    /**
     * content 内容
     * */
    private String content;

    /**
     * 消息类型
     * */
    private Integer sendType;

    /**
     * 发送时间
     */
    private Date sendTime;
}
