package com.obeast.business.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.obeast.core.validation.group.AddGroup;
import com.obeast.core.validation.group.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
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
public class SysMenuEntity implements Serializable {

	@Serial
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@NotNull(groups = {UpdateGroup.class})
	@Schema(description = "菜单id")
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 父Id 0
	 */
	@Schema(description = "父Id")
	private Long parentId;

	/**
	 * 层级
	 * */
	@Schema(description = "层级")
	private Integer level;


	/**
	 * 权限标识名
	 */
	@Schema(description = "权限标识名")
	private String purviewName;

	/**
	 * 菜单名
	 */
	@NotNull(groups = {AddGroup.class, UpdateGroup.class})
	@Schema(description = "菜单名")
	private String name;

	/**
	 * 图标
	 */
	@Schema(description = "图标")
	private String icon;

	/**
	 * 路径
	 */
	@Schema(description = "路径")
	private String path;

	/**
	 * 排序;数字越越靠后
	 */
	@Schema(description = "排序;数字越越靠后")
	private Integer sort;


	/**
	 * 逻辑删除标记(0--正常 1--删除)
	 */
	@Range(min = 0, max = 1)
	@Schema(description = "逻辑删除标记")
	private Integer status;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	@NotNull(groups = {AddGroup.class})
	private String createId;


	/**
	 * 修改人
	 */
	@NotNull(groups = {UpdateGroup.class})
	@Schema(description = "修改人")
	private String updateId;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间", hidden = true)
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间", hidden = true)
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

	/**
	 * 子菜单
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@TableField(exist = false)
	private List<SysMenuEntity> children;

}
