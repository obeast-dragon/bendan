package com.obeast.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wxl
 * Date 2022/12/28 21:30
 * @version 1.0
 * Description:  聊天记录
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
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 自己id
     */

    private Long fromUuid;

    /**
     * 用户id

     */
    private Long toUuid;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 发送类型【0文本，1图片，2语言，3视频】
     */
    private Integer sendType;

    /**
     * 发送时长
     */
    private Date sendTimeLength;

    /**
     * 发送时间
     */
    private Date sendTime;

}
