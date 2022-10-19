package com.obeast.auth.business.controller;

import com.obeast.auth.business.entity.SysMenuEntity;
import com.obeast.auth.business.service.SysMenuService;
import com.obeast.common.base.CommonResult;
import com.obeast.common.constant.PageConstant;
import com.obeast.common.domain.PageObjects;
import com.obeast.common.utils.EasyExcelUtils;
import com.obeast.common.validation.group.AddGroup;
import com.obeast.common.validation.group.DefaultGroup;
import com.obeast.common.validation.group.UpdateGroup;
import com.obeast.common.validation.group.DeleteGroup;
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

import javax.servlet.http.HttpServletResponse;


/**
 * @adminor obeast-dragon
 * Date 2022-10-12 20:10:00
 * @version 1.0
 * Description: 菜单表
 */
@RestController
@RequestMapping("/sysMenu")
@Tag(name = "SysMenu接口")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

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
    public CommonResult<PageObjects<SysMenuEntity>> list(@RequestParam Map<String, Object> params) {
        if (params.size() == 0) {
            return CommonResult.error("params is null");
        }
        PageObjects<SysMenuEntity> page = sysMenuService.queryPage(params);
        return CommonResult.success(page, "page");
    }


    /**
     * 查询所有
     * */
    @GetMapping("/menu/list")
    @Operation(summary = "查询所有菜单树")
    public CommonResult<List<SysMenuEntity>> listAll() {
        List<SysMenuEntity> data = sysMenuService.queryAllTree();
        return CommonResult.success(data, "data");
    }



    /**
     * 根据id查询
     */
    @GetMapping("/getOneById/{id}")
    @Operation(summary = "根据id查询")
    @Parameter(name = "id", description = "id of the entity", in = ParameterIn.PATH, required = true, schema = @Schema(implementation = Long.class))
    public CommonResult<SysMenuEntity> getOneById(@PathVariable("id") Long id){
        if (id < 0){
            return CommonResult.error("id is null");
        }
		SysMenuEntity sysMenu = sysMenuService.queryById(id);
        return CommonResult.success(sysMenu, "sysMenu");
    }


    /**
     * 新增
     */
    @PostMapping("/save")
    @Operation(summary = "新增")
    public CommonResult<?> save(@Validated({AddGroup.class, DefaultGroup.class}) @RequestBody SysMenuEntity sysMenuentity){
        if (sysMenuentity == null){
            return CommonResult.error("sysMenuentity must not be null");
        }
        boolean flag = sysMenuService.add(sysMenuentity);
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
    public CommonResult<?> saveList(@Validated({AddGroup.class, DefaultGroup.class}) @RequestBody List<SysMenuEntity> data){
        if (data.size() == 0) {
            return CommonResult.error("data must not be null");
        }
        boolean flag = sysMenuService.addList(data);
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
    public CommonResult<?> update(@Validated({UpdateGroup.class, DefaultGroup.class}) @RequestBody SysMenuEntity sysMenuentity){
        if (sysMenuentity == null){
            return CommonResult.error("sysMenuentity must not be null");
        }
        boolean flag = sysMenuService.replace(sysMenuentity);
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
    public CommonResult<?>  updateList(@Validated({UpdateGroup.class, DefaultGroup.class}) @RequestBody List<SysMenuEntity> data) {
        if (data.size() == 0) {
            return CommonResult.error("data must not be null");
        }
        boolean flag = sysMenuService.replaceList(data);
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
    public CommonResult<?> delete(@Validated({DeleteGroup.class, DefaultGroup.class}) @RequestParam("id") Long id) {
        if (id < 0) {
            return CommonResult.error("Delete failed id is null");
        }
        boolean delete = sysMenuService.deleteById(id);
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
    public CommonResult<?> deleteList(@Validated({DeleteGroup.class, DefaultGroup.class}) @RequestParam("ids") List<Long> ids) {
        if (ids.size() == 0) {
            return CommonResult.error("Delete failed ids is null");
        }
        boolean removes = sysMenuService.deleteByIds(ids);
        if (removes){
            return CommonResult.success("Deletes successfully");
        }else {
            return CommonResult.error("Deletes failed");
        }
    }

}
