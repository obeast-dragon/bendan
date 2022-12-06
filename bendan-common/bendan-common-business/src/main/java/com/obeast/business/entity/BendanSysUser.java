package com.obeast.business.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * @author wxl
 * Date 2022/11/30 9:20
 * @version 1.0
 * Description: bendan_sys_user
 */
@TableName(value ="bendan_sys_user")
@Data
public class BendanSysUser implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;


    /**
     * 密文
     */
    private String secret;

    /**
     * 锁定状态（0-正常，1-锁定）
     */
    private Integer lockStatus;


    /**
     * 性别   (-1 未知 0 女性  1 男性)
     */
    private Integer gender;

    /**
     * 创建人ID
     */
    private Long createId;

    /**
     * 修改人ID
     */
    private Long updateId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;



}