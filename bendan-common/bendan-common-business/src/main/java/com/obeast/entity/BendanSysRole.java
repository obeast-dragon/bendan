package com.obeast.entity;

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
 * Description: 角色表
 */
@Data
@TableName("bendan_sys_role")
public class BendanSysRole implements Serializable {

	@Serial
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;


	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 角色名字
	 */
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
	private Integer del;

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
