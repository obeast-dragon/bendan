package com.obeast.bendan.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;



/**
 * @author obeast-dragon
 * Date 2022-09-21 10:03:18
 * @version 1.0
 * Description: 
 */
@Data
public class CommentExcel {

    /**
     * id
     */
    @ExcelIgnore
    private Long id;

    /**
     * 博客id
     */
    @ExcelProperty(value = "博客id")
    private Long blogId;

    /**
     * 评论父id
     */
    @ExcelProperty(value = "评论父id")
    private Long parentCommentId;

    /**
     * 头像
     */
    @ExcelProperty(value = "头像")
    private String avatar;

    /**
     * 评论内容
     */
    @ExcelProperty(value = "评论内容")
    private String content;

    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱")
    private String email;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private String name;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

}
