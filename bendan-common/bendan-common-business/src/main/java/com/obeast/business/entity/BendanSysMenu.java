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
 * @author obeast-dragon
 * Date 2022-11-30 10:42:42
 * @version 1.0
 * Description: 菜单(权限)表
 */
@Data
@TableName("bendan_sys_menu")
public class BendanSysMenu implements Serializable {
	@Serial
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
	private String name;

	/**
	 * 是否可跳转，0 不可跳转  1 可跳转
	 */
	private Integer type;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 路径
	 */
	private String path;

	/**
	 * 排序;数字越越靠后
	 */
	private Integer sort;


	/**
	 * 逻辑删除标记(0--正常 1--删除)
	 */
	private Integer status;

	/**
	 * 创建人
	 */
	private String createBy;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改人
	 */
	private String updateBy;

	/**
	 * 更新时间
	 */
	private Date updateTime;

}
