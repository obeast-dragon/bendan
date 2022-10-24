package com.obeast.admin.business.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

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
@TableName("bendan_authorization_consent")
public class AuthorizationConsentEntity implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;


	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private String registeredClientId;

	/**
	 * 
	 */
	private String principalName;

	/**
	 * 
	 */
	private String authorities;

}
