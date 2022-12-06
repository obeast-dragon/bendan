package com.obeast.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


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
	 * 层级
	 * */
	private Integer level;

	/**
	 * 孩子们 （空间换时间）
	 * */
	@TableField(value = "menu_children", typeHandler = FastjsonTypeHandler.class)
	private List<BendanSysMenu> children;

	/**
	 * 菜单名
	 */
	private String name;

	/**
	 * 是否可跳转，(null 不可跳转  !null 可跳转)
	 */
	private String url;

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
	private String createId;
	/**
	 * 修改人
	 */
	private String updateId;

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
