package com.obeast.business.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wxl
 * Date 2022/12/30 21:21
 * @version 1.0
 * Description: 好友 关系vo
 */
@TableName(value ="friend_rel")
@Data
public class FriendRelsVo implements Serializable {

    /**
     * userAIds
     * */
    @TableField("user_a")
    private Long userA;

    /**
     *userBIds
     * */
    @TableField("user_b")
    private Long userB;
}
