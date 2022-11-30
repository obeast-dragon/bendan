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
    private Long userId;

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
    private String avatarUrl;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 删除状态（1-正常，0-删除）
     */
    private Integer delEnabled;

    /**
     * 密文
     */
    private String secret;

    /**
     * 锁定状态（3-正常，2-锁定）
     */
    private Integer useStatus;

    /**
     * 使用状态（5-正常，4-禁用）
     */
    private Integer userAble;

    /**
     * 过期状态（7-正常，6-过期）
     */
    private Integer userExpired;


    /**
     * 性别   -1 未知 0 女性  1 男性
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
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;


}