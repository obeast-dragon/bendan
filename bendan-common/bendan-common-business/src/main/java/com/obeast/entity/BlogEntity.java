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
@TableName("bendan_blog")
public class BlogEntity implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;


	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 分类ID
	 */
	private Long typeId;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 访问次数
	 */
	private Integer views;

	/**
	 * 博客内容
	 */
	private String content;

	/**
	 * 博客描述
	 */
	private String description;

	/**
	 * 首图编码
	 */
	private String firstPicture;

	/**
	 * 标记
	 */
	private String flag;

	/**
	 * 是否保存状态[0:关闭,1:开启]
	 */
	private Boolean isPublished;

	/**
	 * 是否推荐[0:关闭,1:开启]
	 */
	private Boolean recommend;

	/**
	 * 转载声明是否开启[0:关闭,1:开启]
	 */
	private Boolean shareStatement;

	/**
	 * 赞赏是否开启[0:关闭,1:开启]
	 */
	private Boolean isAppreciation;

	/**
	 * 评论是否开启[0:关闭,1:开启]
	 */
	private Boolean isCommentable;
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
