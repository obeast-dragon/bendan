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
public class UserExcel {

    /**
     * id
     */
    @ExcelIgnore
    private Long id;

    /**
     * 账号
     */
    @ExcelProperty(value = "账号")
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
    private String nickname;

    /**
     * 角色
     */
    @ExcelProperty(value = "角色")
    private Integer type;

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

}
