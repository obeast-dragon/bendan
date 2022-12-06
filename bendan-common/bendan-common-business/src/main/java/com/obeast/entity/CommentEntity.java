package com.obeast.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;



/**
 * @author obeast-dragon
 * Date 2022-09-21 12:18:12
 * @version 1.0
 * Description: 
 */
@Data
@TableName("bendan_comment")
public class CommentEntity implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;


	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 博客id
	 */
	private Long blogId;

	/**
	 * 评论父id
	 */
	private Long parentCommentId;

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 评论内容
	 */
	private String content;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 
	 */
	private String name;

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
