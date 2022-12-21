package com.obeast.admin.business.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.obeast.core.base.CommonResult;
import com.obeast.security.business.service.SysMenuService;
import com.obeast.business.dto.SysMenuDTO;
import com.obeast.business.entity.SysMenuEntity;
import com.obeast.core.validation.group.AddGroup;
import com.obeast.core.validation.group.DefaultGroup;
import com.obeast.core.validation.group.UpdateGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/sysMenu")
@Tag(name = "路由(权限)管理接口")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService sysMenuService;



    @GetMapping("/auth/buttons")
    public JSONObject button () {
        String jsonStr = """
                {
                 "useHooks": {
                    "add": true,
                    "delete": true
                 }
                 }
                """;
        JSONObject obj = JSONUtil.parseObj(jsonStr);
        return obj;
    }


    /**
     * 根据角色id查询
     */
    @GetMapping("/treeByRoleIds")
    @Operation(summary = "根据角色id查询Tree")
    public List<SysMenuEntity> treeByRoleIds(@RequestParam("roleIds") List<Long> roleIds, @RequestParam("isLazy") Boolean isLazy){
        return sysMenuService.treeByRoleId(roleIds, isLazy);
    }



    /*-------------------------程序员修改--------------------*/
    /**
     * 新增
     */
    @PostMapping("/save")
    @Operation(summary = "新增")
    public Boolean save(@Validated({AddGroup.class, DefaultGroup.class}) @RequestBody List<SysMenuEntity> sysMenuEntities){
        return sysMenuService.addMenus(sysMenuEntities);
    }


    /**
     * 修改
     */
    @PostMapping("/update")
    @Operation(summary = "修改")
    public Boolean update(@Validated({UpdateGroup.class, DefaultGroup.class}) @RequestBody SysMenuDTO sysMenuDTO){
        return sysMenuService.replace(sysMenuDTO);
    }


    /**
     * 删除
     */
    @Operation(summary = "删除")
    @DeleteMapping("/delete")
    public Boolean delete( @RequestParam("id") Long id) {
        return sysMenuService.deleteById(id);
    }


}
