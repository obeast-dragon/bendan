package com.obeast.security.business.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.business.dto.SysRoleDTO;
import com.obeast.core.constant.RoleConstant;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.exception.BendanException;
import com.obeast.core.utils.PageQueryUtils;
import com.obeast.business.entity.SysRoleEntity;
import com.obeast.security.business.dao.SysRoleDao;
import com.obeast.security.business.service.SysMenuService;
import com.obeast.security.business.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author obeast-dragon
 * Date 2022-11-30 10:42:42
 * @version 1.0
 * Description: 角色表
 */
@Service("sysRoleService")
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity>
        implements SysRoleService {


    private final SysRoleDao sysRoleDao;

    private final SysMenuService sysMenuService;

    @Override
    public Boolean addRoleUserRel(Long userId, Long roleId) {
        return sysRoleDao.addRelation(userId, roleId);
    }

    @Override
    public List<SysRoleEntity> listRolesByUserId(Long userId) {
        if (userId == null){
            return null;
        }
        return sysRoleDao.listRolesByUserId(userId);
    }

    /*--------------------------------------------------default-------------------------------------------------------------*/



    @Override
    public PageObjects<SysRoleEntity> queryPage(JSONObject params) {
        String key =  params.getStr("orderField");
        QueryWrapper<SysRoleEntity> queryWrapper = Wrappers.query();
        IPage<SysRoleEntity> page = this.page(
                new PageQueryUtils<SysRoleEntity>().getPage(params, key, false),
                queryWrapper
        );
        return new PageQueryUtils<>().getPageObjects(page, SysRoleEntity.class);
    }


    @Override
    public List<SysRoleEntity> queryAll() {
        return this.list();
    }

    @Override
    public SysRoleEntity queryById(Long id) {
        Assert.notNull(id, "id cannot be null");
        return this.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean add(SysRoleDTO sysRoleDto) {
        Assert.notNull(sysRoleDto , "sysRoleDto cannot be null");
        /*判断角色是否存在*/
        Assert.isTrue(!this.isRoleExistByName(sysRoleDto.getName()), RoleConstant.ROLE_EXIST);
        /*添加权限*/
        List<Long> menuIds = sysRoleDto.getMenuIds();
        boolean save = this.save(sysRoleDto);
        Assert.isTrue(save, "保存失败");
        Boolean addRoleMenuRels = sysMenuService.addRoleMenuRels(menuIds, sysRoleDto.getId());
        Assert.isTrue(addRoleMenuRels, "保存角色权限关系失败");
        return Boolean.TRUE;
    }


    @Override
    public Boolean replace(SysRoleDTO sysRole) {
        return this.updateById(sysRole);
    }


    @Override
    public Boolean deleteById(Long id) {
        Assert.notNull(id, "id cannot be null");
        sysMenuService.delRelsByRoleId(id);
        return this.removeById(id);
    }

    @Override
    public Boolean delRelsByUserId(Long userId) {
        return sysRoleDao.delRelsByUserId(userId);
    }

    @Override
    public Boolean updateRolesUserRel(List<Long> roleIds, Long userId) {
        this.delRelsByUserId(userId);
        return this.addRoleUserRels(userId, roleIds);
    }


    @Override
    public Boolean addRoleUserRels(Long userId, List<Long> roleIds) throws BendanException{
        Boolean addRelation = Boolean.FALSE;
        if(ArrayUtil.isNotEmpty(roleIds)){
            /*去重*/
            roleIds = roleIds.stream().distinct().toList();
            for (Long roleId : roleIds) {
                if (!this.isRoleExistById(roleId)){
                    throw new BendanException(RoleConstant.NOT_FOUND);
                }
                addRelation = this.addRoleUserRel(
                        userId,
                        roleId
                );
            }
            return addRelation;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean isRoleExistById(Long roleId) throws BendanException {
        return this.getById(roleId) != null;
    }

    @Override
    public SysRoleEntity getByName (String roleName) {
        Assert.notNull(roleName, "roleName cannot be null");
        return this.getOne(
                Wrappers.<SysRoleEntity>lambdaQuery()
                        .eq(SysRoleEntity::getName, roleName)
        );

    }

    @Override
    public Boolean isRoleExistByName(String roleName) throws BendanException {
        return this.getByName(roleName) != null;
    }
}
