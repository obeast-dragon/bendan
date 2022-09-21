package com.obeast.blog.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;



/**
 * @author obeast-dragon
 * Date 2022-09-21 12:18:12
 * @version 1.0
 * Description: 
 */
@Data
public class BlogExcel {

    /**
     * id
     */
    @ExcelIgnore
    private Long id;

    /**
     * 分类ID
     */
    @ExcelProperty(value = "分类ID")
    private Long typeId;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 标题
     */
    @ExcelProperty(value = "标题")
    private String title;

    /**
     * 访问次数
     */
    @ExcelProperty(value = "访问次数")
    private Integer views;

    /**
     * 博客内容
     */
    @ExcelProperty(value = "博客内容")
    private String content;

    /**
     * 博客描述
     */
    @ExcelProperty(value = "博客描述")
    private String description;

    /**
     * 首图编码
     */
    @ExcelProperty(value = "首图编码")
    private String firstPicture;

    /**
     * 标记
     */
    @ExcelProperty(value = "标记")
    private String flag;

    /**
     * 是否保存状态[0:关闭,1:开启]
     */
    @ExcelProperty(value = "是否保存状态[0:关闭,1:开启]")
    private Boolean isPublished;

    /**
     * 是否推荐[0:关闭,1:开启]
     */
    @ExcelProperty(value = "是否推荐[0:关闭,1:开启]")
    private Boolean recommend;

    /**
     * 转载声明是否开启[0:关闭,1:开启]
     */
    @ExcelProperty(value = "转载声明是否开启[0:关闭,1:开启]")
    private Boolean shareStatement;

    /**
     * 赞赏是否开启[0:关闭,1:开启]
     */
    @ExcelProperty(value = "赞赏是否开启[0:关闭,1:开启]")
    private Boolean isAppreciation;

    /**
     * 评论是否开启[0:关闭,1:开启]
     */
    @ExcelProperty(value = "评论是否开启[0:关闭,1:开启]")
    private Boolean isCommentable;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

}
