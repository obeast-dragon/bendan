package com.obeast.admin.business.controller;

import cn.hutool.json.JSONObject;
import com.obeast.core.base.CommonResult;
import com.obeast.core.constant.PageConstant;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.validation.group.AddGroup;
import com.obeast.core.validation.group.DefaultGroup;
import com.obeast.core.validation.group.DeleteGroup;
import com.obeast.core.validation.group.UpdateGroup;
import com.obeast.business.entity.SysMenuEntity;
import com.obeast.security.business.service.BendanSysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**
 * @author obeast-dragon
 * Date 2022-12-04 22:35:16
 * @version 1.0
 * Description: 菜单(权限)表
 */
@RestController
@RequestMapping("/bendansysmenu")
@Tag(name = "BendanSysMenu接口")
@RequiredArgsConstructor
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class BendanSysMenuController {

    private final BendanSysMenuService bendanSysMenuService;

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
    public CommonResult<PageObjects<SysMenuEntity>> list(@RequestParam JSONObject params) {
        if (params.size() == 0) {
            return CommonResult.error("params is null");
        }
        PageObjects<SysMenuEntity> page = bendanSysMenuService.queryPage(params);
        return CommonResult.success(page, "page");
    }


    /**
     * 查询所有
     * */
    @GetMapping("/listAll")
    @Operation(summary = "查询所有")
    public CommonResult<List<SysMenuEntity>> listAll() {
        List<SysMenuEntity> data = bendanSysMenuService.queryAll();
        return CommonResult.success(data, "list");
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
		SysMenuEntity sysMenuEntity = bendanSysMenuService.queryById(id);
        return CommonResult.success(sysMenuEntity, "bendanSysMenu");
    }


    /**
     * 新增
     */
    @PostMapping("/save")
    @Operation(summary = "新增")
    public CommonResult<?> save(@Validated({AddGroup.class, DefaultGroup.class}) @RequestBody SysMenuEntity sysMenuEntity){
        if (sysMenuEntity == null){
            return CommonResult.error("bendanSysMenu must not be null");
        }
        boolean flag = bendanSysMenuService.add(sysMenuEntity);
        if (flag) {
            return CommonResult.success("add successfully");
        }else {
            return CommonResult.error("add failed");
        }
    }



    /**
     * 修改
     */
    @PostMapping("/update")
    @Operation(summary = "修改")
    public CommonResult<?> update(@Validated({UpdateGroup.class, DefaultGroup.class}) @RequestBody SysMenuEntity sysMenuentity){
        if (sysMenuentity == null){
            return CommonResult.error("bendanSysMenuentity must not be null");
        }
        boolean flag = bendanSysMenuService.replace(sysMenuentity);
        if (flag){
            return CommonResult.success("update successfully");
        }else {
            return CommonResult.error("Update failed");
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
        boolean delete = bendanSysMenuService.deleteById(id);
        if (delete){
            return CommonResult.success("Delete successfully");
        }else {
            return CommonResult.error("Delete failed");
        }
    }


}
