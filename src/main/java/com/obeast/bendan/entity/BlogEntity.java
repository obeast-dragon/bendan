package com.obeast.bendan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;



/**
 * @author obeast-dragon
 * Date 2022-09-21 10:03:18
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
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 创建时间
	 */
	private Date createTime;

}
