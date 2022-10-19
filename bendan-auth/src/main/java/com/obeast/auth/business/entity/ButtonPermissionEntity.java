package com.obeast.auth.business.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;



/**
 * @author obeast-dragon
 * Date 2022-10-12 20:10:00
 * @version 1.0
 * Description: 按钮权限表
 */
@Data
@TableName("bendan_button_permission")
public class ButtonPermissionEntity implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;


	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 
	 */
	private Integer isAdd;

	/**
	 * 
	 */
	private Integer isEdit;

	/**
	 * 
	 */
	private Integer isDel;

}
