package com.obeast.blog.controller;


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


import com.obeast.blog.service.TypeService;

import javax.servlet.http.HttpServletResponse;


/**
 * @author obeast-dragon
 * Date 2022-09-21 12:18:12
 * @version 1.0
 * Description: 
 */
@RestController
@RequestMapping("/type")
@Tag(name = "Type接口")
public class TypeController {

    @Autowired
    private TypeService typeService;

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
    public R<PageObjects<TypeEntity>> list(@RequestParam Map<String, Object> params) {
        if (params.size() == 0) {
            return R.error("params is null");
        }
        PageObjects<TypeEntity> page = typeService.queryPage(params);
        return R.success(page, "page");
    }


    /**
     * 查询所有
     * */
    @GetMapping("/listAll")
    @Operation(summary = "查询所有")
    public R<List<TypeEntity>> listAll() {
        List<TypeEntity> data = typeService.queryAll();
        return R.success(data, "list");
    }



    /**
     * 根据id查询
     */
    @GetMapping("/getOneById/{id}")
    @Operation(summary = "根据id查询")
    @Parameter(name = "id", description = "id of the entity", in = ParameterIn.PATH, required = true, schema = @Schema(implementation = Long.class))
    public R<TypeEntity> getOneById(@PathVariable("id") Long id){
        if (id < 0){
            return R.error("id is null");
        }
		TypeEntity type = typeService.queryById(id);
        return R.success(type, "type");
    }


    /**
     * 新增
     */
    @PostMapping("/save")
    @Operation(summary = "新增")
    public R save(@Validated({AddGroup.class, DefaultGroup.class}) @RequestBody TypeEntity typeentity){
        if (typeentity == null){
            return R.error("typeentity must not be null");
        }
        boolean flag = typeService.add(typeentity);
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
    public R saveList(@Validated({AddGroup.class, DefaultGroup.class}) @RequestBody List<TypeEntity> data){
        if (data.size() == 0) {
            return R.error("data must not be null");
        }
        boolean flag = typeService.addList(data);
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
    public R update(@Validated({UpdateGroup.class, DefaultGroup.class}) @RequestBody TypeEntity typeentity){
        if (typeentity == null){
            return R.error("typeentity must not be null");
        }
        boolean flag = typeService.replace(typeentity);
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
    public R updateList(@Validated({UpdateGroup.class, DefaultGroup.class}) @RequestBody List<TypeEntity> data) {
        if (data.size() == 0) {
            return R.error("data must not be null");
        }
        boolean flag = typeService.replaceList(data);
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
        boolean delete = typeService.deleteById(id);
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
        boolean removes = typeService.deleteByIds(ids);
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
            List<TypeExcel> data = typeService.queryExcelByConditions();
            EasyExcelUtils<TypeExcel> easyExcel = new EasyExcelUtils<>(TypeExcel.class);
            easyExcel.exportExcel(response, data, sheetName, fileName);
        }
    }
}
