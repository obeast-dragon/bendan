package com.obeast.admin.business.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;



/**
 * @author obeast-dragon
 * Date 2022-10-12 20:10:00
 * @version 1.0
 * Description: 菜单表
 */
@Data
@TableName("bendan_sys_menu")
public class SysMenuEntity implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 父ID
	 */
	private Long parentId;

	/**
	 * 菜单名
	 */
	private String title;

	/**
	 * 是否可跳转，0 不可跳转  1 可跳转
	 */
	private Integer type;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 跳转链接
	 */
	private String isLink;

	/**
	 * 排序
	 * */
	private Integer sort;

	/**
	 * 路径
	 */
	private String path;

	@TableField(exist = false)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<SysMenuEntity> children;

}
