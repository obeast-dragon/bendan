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
public class TagsExcel {

    /**
     * id
     */
    @ExcelIgnore
    private Long id;

    /**
     * 标签名称
     */
    @ExcelProperty(value = "标签名称")
    private String name;

}
