package com.obeast.chat.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wxl
 * Date 2022/12/27 14:17
 * @version 1.0
 * Description: 聊天记录模拟表
 */
@TableName(value ="bendan_chat_record")
@Data
public class ChatRecordEntity implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @JsonIgnore
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */

    private Long fromId;

    /**
     * 专家id

     */
    private Long toId;

    /**
     * 发送内容
     */
    private String sendContent;

    /**
     * 发送类型【0文本，1图片，2语言，3视频】
     */
    private Integer sendType;

    /**
     * 发送时长
     */
    private Long sendTimeLength;

    /**
     * 发送时间
     */
    private Date sendTime;


}
