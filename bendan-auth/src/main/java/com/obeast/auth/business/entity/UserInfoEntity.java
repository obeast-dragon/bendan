package com.obeast.auth.business.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;



/**
 * @author obeast-dragon
 * Date 2022-10-11 21:02:40
 * @version 1.0
 * Description: 
 */
@Data
@TableName("bendan_user_info")
public class UserInfoEntity implements Serializable {

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
	 * 性别   -1 未知 0 女性  1 男性 
	 */
	private Integer gender;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 创建人ID
	 */
	private Long createId;

	/**
	 * 修改时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

	/**
	 * 修改人ID
	 */
	private Long updateId;

	/**
	 * 删除状态（1-正常，0-删除）
	 */
	private Integer delEnabled;


	/**
	 * 锁定状态（3-正常，2-锁定）
	 */
	private Integer useStatus;

	/**
	 * 密文
	 */
	private String secret;

	/**
	 *使用状态（5-正常，4-禁用）
	 */
	private Integer userAble;

	/**
	 *过期状态（7-正常，6-过期）
	 */
	private Integer userExpired;

}
