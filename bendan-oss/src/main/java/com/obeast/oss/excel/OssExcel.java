package com.obeast.oss.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;



/**
 * @author obeast-dragon
 * Date 2022-09-21 15:19:09
 * @version 1.0
 * Description: 
 */
@Data
public class OssExcel {

    /**
     * 主键ID
     */
    @ExcelIgnore
    private Long id;

    /**
     * md5文件名
     */
    @ExcelProperty(value = "md5文件名")
    private String md5FileName;

    /**
     * 文件的路径
     */
    @ExcelProperty(value = "文件的路径")
    private String url;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private Date createTime;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private Date updateTime;

}
