package com.obeast.blog.controller;

import com.obeast.blog.excel.BlogExcel;
import com.obeast.core.base.CommonResult;
import com.obeast.core.constant.PageConstant;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.utils.EasyExcelUtils;
import com.obeast.core.validation.group.AddGroup;
import com.obeast.core.validation.group.DefaultGroup;
import com.obeast.core.validation.group.DeleteGroup;
import com.obeast.core.validation.group.UpdateGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.obeast.blog.entity.BlogEntity;

import com.obeast.blog.service.BlogService;

import javax.servlet.http.HttpServletResponse;


/**
 * @author obeast-dragon
 * Date 2022-09-21 12:18:12
 * @version 1.0
 * Description: 
 */
@RestController
@RequestMapping("/blog")
@Tag(name = "Blog接口")
public class BlogController {

    @Autowired
    private BlogService blogService;

    /**
     * 分页列表
     */
    @GetMapping("/listPage")
    @Operation(summary = "分页查询")
    @Parameters({
            @Parameter(name = PageConstant.CUR, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, schema = @Schema(implementation = Integer.class)),
            @Parameter(name = PageConstant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY, required = true, schema = @Schema(implementation = Integer.class)),
            @Parameter(name = PageConstant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, schema = @Schema(implementation = String.class)),
            @Parameter(name = PageConstant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, required = true, schema = @Schema(implementation = String.class))
    })
    public CommonResult<PageObjects<BlogEntity>> list(@RequestParam Map<String, Object> params) {
        if (params.size() == 0) {
            return CommonResult.error("params is null");
        }
        PageObjects<BlogEntity> page = blogService.queryPage(params);
        return CommonResult.success(page, "page");
    }


    /**
     * 查询所有
     * */
    @GetMapping("/listAll")
    @Operation(summary = "查询所有")
    public CommonResult<List<BlogEntity>> listAll() {
        List<BlogEntity> data = blogService.queryAll();
        return CommonResult.success(data, "list");
    }



    /**
     * 根据id查询
     */
    @GetMapping("/getOneById/{id}")
    @Operation(summary = "根据id查询")
    @Parameter(name = "id", description = "id of the entity", in = ParameterIn.PATH, required = true, schema = @Schema(implementation = Long.class))
    public CommonResult<BlogEntity> getOneById(@PathVariable("id") Long id){
        if (id < 0){
            return CommonResult.error("id is null");
        }
		BlogEntity blog = blogService.queryById(id);
        return CommonResult.success(blog, "blog");
    }


    /**
     * 新增
     */
    @PostMapping("/save")
    @Operation(summary = "新增")
    public CommonResult save(@Validated({AddGroup.class, DefaultGroup.class}) @RequestBody BlogEntity blogentity){
        if (blogentity == null){
            return CommonResult.error("blogentity must not be null");
        }
        boolean flag = blogService.add(blogentity);
        if (flag) {
            return CommonResult.success("add successfully");
        }else {
            return CommonResult.error("add failed");
        }
    }


    /**
     * 批量新增
     */
    @PostMapping("/saveList")
    @Operation(summary = "批量新增")
    public CommonResult saveList(@Validated({AddGroup.class, DefaultGroup.class}) @RequestBody List<BlogEntity> data){
        if (data.size() == 0) {
            return CommonResult.error("data must not be null");
        }
        boolean flag = blogService.addList(data);
        if (flag) {
            return CommonResult.success("adds successfully");
        }else {
            return CommonResult.error("adds failed");
        }
    }



    /**
     * 修改
     */
    @PostMapping("/update")
    @Operation(summary = "修改")
    public CommonResult update(@Validated({UpdateGroup.class, DefaultGroup.class}) @RequestBody BlogEntity blogentity){
        if (blogentity == null){
            return CommonResult.error("blogentity must not be null");
        }
        boolean flag = blogService.replace(blogentity);
        if (flag){
            return CommonResult.success("update successfully");
        }else {
            return CommonResult.error("Update failed");
        }
    }


    /**
     * 批量修改
     */
    @PostMapping("/updateList")
    @Operation(summary = "批量修改")
    public CommonResult updateList(@Validated({UpdateGroup.class, DefaultGroup.class}) @RequestBody List<BlogEntity> data) {
        if (data.size() == 0) {
            return CommonResult.error("data must not be null");
        }
        boolean flag = blogService.replaceList(data);
        if (flag){
            return CommonResult.success("updates successfully");
        }else {
            return CommonResult.error("Updates failed");
        }
    }


    /**
     * 删除
     */
    @Operation(summary = "删除")
    @Parameter(name = "id", description = "id数组", required = true, in = ParameterIn.QUERY)
    @DeleteMapping("/delete")
    public CommonResult delete(@Validated({DeleteGroup.class, DefaultGroup.class}) @RequestParam("id") Long id) {
        if (id < 0) {
            return CommonResult.error("Delete failed id is null");
        }
        boolean delete = blogService.deleteById(id);
        if (delete){
            return CommonResult.success("Delete successfully");
        }else {
            return CommonResult.error("Delete failed");
        }
    }


    /**
     * 批量删除
     */
    @Operation(summary = "批量删除")
    @Parameter(name = "ids", description = "id数组", required = true, in = ParameterIn.QUERY)
    @DeleteMapping("/deleteList")
    public CommonResult deleteList(@Validated({DeleteGroup.class, DefaultGroup.class}) @RequestParam("ids") List<Long> ids) {
        if (ids.size() == 0) {
            return CommonResult.error("Delete failed ids is null");
        }
        boolean removes = blogService.deleteByIds(ids);
        if (removes){
            return CommonResult.success("Deletes successfully");
        }else {
            return CommonResult.error("Deletes failed");
        }
    }


    /**
     * 导出
     */
    @GetMapping("/export")
    @Operation(summary = "导出Excel")
    @Parameters({
            @Parameter(name = "fileName", in = ParameterIn.QUERY, description = "文件名"),
            @Parameter(name = "sheetName", in = ParameterIn.QUERY, description = "工作表名")
    })
    public void export(HttpServletResponse response,
                                 @RequestParam Map<String, Object> params) throws Exception{
        String fileName = (String) params.get("fileName");
        String sheetName = (String) params.get("sheetName");
        if (!StringUtils.hasText(fileName)) {
            CommonResult.error("请输入 fileName 参数");
        }
        if (fileName == null) {
            throw new Exception("File name cannot be null");
        } else {
            List<BlogExcel> data = blogService.queryExcelByConditions();
            EasyExcelUtils<BlogExcel> easyExcel = new EasyExcelUtils<>(BlogExcel.class);
            easyExcel.exportExcel(response, data, sheetName, fileName);
        }
    }
}
