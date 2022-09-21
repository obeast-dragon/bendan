package com.obeast.blog.controller;


import com.obeast.blog.entity.TagsEntity;
import com.obeast.blog.excel.TagsExcel;
import com.obeast.oss.base.R;
import com.obeast.oss.constant.PageConstant;
import com.obeast.oss.domain.PageObjects;
import com.obeast.oss.utils.EasyExcelUtils;
import com.obeast.oss.validation.group.AddGroup;
import com.obeast.oss.validation.group.DefaultGroup;
import com.obeast.oss.validation.group.DeleteGroup;
import com.obeast.oss.validation.group.UpdateGroup;
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


import com.obeast.blog.service.TagsService;

import javax.servlet.http.HttpServletResponse;


/**
 * @author obeast-dragon
 * Date 2022-09-21 12:18:12
 * @version 1.0
 * Description: 
 */
@RestController
@RequestMapping("/tags")
@Tag(name = "Tags接口")
public class TagsController {

    @Autowired
    private TagsService tagsService;

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
    public R<PageObjects<TagsEntity>> list(@RequestParam Map<String, Object> params) {
        if (params.size() == 0) {
            return R.error("params is null");
        }
        PageObjects<TagsEntity> page = tagsService.queryPage(params);
        return R.success(page, "page");
    }


    /**
     * 查询所有
     * */
    @GetMapping("/listAll")
    @Operation(summary = "查询所有")
    public R<List<TagsEntity>> listAll() {
        List<TagsEntity> data = tagsService.queryAll();
        return R.success(data, "list");
    }



    /**
     * 根据id查询
     */
    @GetMapping("/getOneById/{id}")
    @Operation(summary = "根据id查询")
    @Parameter(name = "id", description = "id of the entity", in = ParameterIn.PATH, required = true, schema = @Schema(implementation = Long.class))
    public R<TagsEntity> getOneById(@PathVariable("id") Long id){
        if (id < 0){
            return R.error("id is null");
        }
		TagsEntity tags = tagsService.queryById(id);
        return R.success(tags, "tags");
    }


    /**
     * 新增
     */
    @PostMapping("/save")
    @Operation(summary = "新增")
    public R save(@Validated({AddGroup.class, DefaultGroup.class}) @RequestBody TagsEntity tagsentity){
        if (tagsentity == null){
            return R.error("tagsentity must not be null");
        }
        boolean flag = tagsService.add(tagsentity);
        if (flag) {
            return R.success("add successfully");
        }else {
            return R.error("add failed");
        }
    }


    /**
     * 批量新增
     */
    @PostMapping("/saveList")
    @Operation(summary = "批量新增")
    public R saveList(@Validated({AddGroup.class, DefaultGroup.class}) @RequestBody List<TagsEntity> data){
        if (data.size() == 0) {
            return R.error("data must not be null");
        }
        boolean flag = tagsService.addList(data);
        if (flag) {
            return R.success("adds successfully");
        }else {
            return R.error("adds failed");
        }
    }



    /**
     * 修改
     */
    @PostMapping("/update")
    @Operation(summary = "修改")
    public R update(@Validated({UpdateGroup.class, DefaultGroup.class}) @RequestBody TagsEntity tagsentity){
        if (tagsentity == null){
            return R.error("tagsentity must not be null");
        }
        boolean flag = tagsService.replace(tagsentity);
        if (flag){
            return R.success("update successfully");
        }else {
            return R.error("Update failed");
        }
    }


    /**
     * 批量修改
     */
    @PostMapping("/updateList")
    @Operation(summary = "批量修改")
    public R updateList(@Validated({UpdateGroup.class, DefaultGroup.class}) @RequestBody List<TagsEntity> data) {
        if (data.size() == 0) {
            return R.error("data must not be null");
        }
        boolean flag = tagsService.replaceList(data);
        if (flag){
            return R.success("updates successfully");
        }else {
            return R.error("Updates failed");
        }
    }


    /**
     * 删除
     */
    @Operation(summary = "删除")
    @Parameter(name = "id", description = "id数组", required = true, in = ParameterIn.QUERY)
    @DeleteMapping("/delete")
    public R delete(@Validated({DeleteGroup.class, DefaultGroup.class}) @RequestParam("id") Long id) {
        if (id < 0) {
            return R.error("Delete failed id is null");
        }
        boolean delete = tagsService.deleteById(id);
        if (delete){
            return R.success("Delete successfully");
        }else {
            return R.error("Delete failed");
        }
    }


    /**
     * 批量删除
     */
    @Operation(summary = "批量删除")
    @Parameter(name = "ids", description = "id数组", required = true, in = ParameterIn.QUERY)
    @DeleteMapping("/deleteList")
    public R deleteList(@Validated({DeleteGroup.class, DefaultGroup.class}) @RequestParam("ids") List<Long> ids) {
        if (ids.size() == 0) {
            return R.error("Delete failed ids is null");
        }
        boolean removes = tagsService.deleteByIds(ids);
        if (removes){
            return R.success("Deletes successfully");
        }else {
            return R.error("Deletes failed");
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
            R.error("请输入 fileName 参数");
        }
        if (fileName == null) {
            throw new Exception("File name cannot be null");
        } else {
            List<TagsExcel> data = tagsService.queryExcelByConditions();
            EasyExcelUtils<TagsExcel> easyExcel = new EasyExcelUtils<>(TagsExcel.class);
            easyExcel.exportExcel(response, data, sheetName, fileName);
        }
    }
}