package com.obeast.business.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.obeast.core.validation.group.AddGroup;
import com.obeast.core.validation.group.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * @author obeast-dragon
 * Date 2022-11-30 10:42:42
 * @version 1.0
 * Description: 角色表
 */
@Data
@TableName("bendan_sys_role")
public class SysRoleEntity implements Serializable {


	@Serial
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;


	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	@NotNull(groups = UpdateGroup.class)
	private Long id;

	/**
	 * 角色名字
	 */
	@NotNull(groups = {AddGroup.class, UpdateGroup.class})
	private String name;

	/**
	 * 角色码
	 */
	private String code;

	/**
	 * 角色描述
	 */
	private String roleDescribe;

	/**
	 * 逻辑删除标记(0--正常 1--删除)
	 */
	@Range(min = 0,  max = 1, groups = {AddGroup.class, UpdateGroup.class})
	private Integer del;

	/**
	 * 创建人
	 */
	@NotNull(groups = {AddGroup.class})
	private Long createId;

	/**
	 * 修改人
	 */
	@NotNull(groups = {UpdateGroup.class})
	private Long updateId;

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


}
