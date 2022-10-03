package com.obeast.admin.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;



/**
 * @author obeast-dragon
 * Date 2022-10-04 00:20:25
 * @version 1.0
 * Description: 管理系统用户
 */
@Data
public class AdminUserExcel {

    /**
     * 用户id
     */
    @ExcelIgnore
    private Long id;

    /**
     * 用户名
     */
    @ExcelProperty(value = "用户名")
    private String username;

    /**
     * 密码
     */
    @ExcelProperty(value = "密码")
    private String password;

    /**
     * 头像
     */
    @ExcelProperty(value = "头像")
    private String avatar;

    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱")
    private String email;

    /**
     * 昵称
     */
    @ExcelProperty(value = "昵称")
    private String nickName;

    /**
     * 备注信息
     */
    @ExcelProperty(value = "备注信息")
    private String note;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 最后登录时间
     */
    @ExcelProperty(value = "最后登录时间")
    private Date loginLastTime;

    /**
     * 帐号启用状态：0->禁用；1->启用
     */
    @ExcelProperty(value = "帐号启用状态：0->禁用；1->启用")
    private Integer disableStatus;

}
