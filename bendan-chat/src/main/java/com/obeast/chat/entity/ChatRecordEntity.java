package com.obeast.chat.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wxl
 * Date 2022/12/27 14:17
 * @version 1.0
 * Description: 聊天记录模拟表
 */
@TableName(value ="plant_chat_record")
@Data
public class ChatRecordEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */

    private String fromUuid;

    /**
     * 专家id

     */
    private String toUuid;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 发送类型【0文本，1图片，2语言，3视频】
     */
    private Integer sendType;

    /**
     * 发送时间
     */
    private Date sendTime;


}
