package com.obeast.security.business.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.utils.PageQueryUtils;
import com.obeast.business.entity.SysMenuEntity;
import com.obeast.security.business.dao.BendanSysMenuDao;
import com.obeast.security.business.service.BendanSysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * @author obeast-dragon
 * Date 2022-11-30 10:42:42
 * @version 1.0
 * Description: 菜单(权限)表
 */
@Service("sysMenuService")
@RequiredArgsConstructor
public class BendanSysMenuServiceImpl extends ServiceImpl<BendanSysMenuDao, SysMenuEntity>
        implements BendanSysMenuService {

    private final BendanSysMenuDao bendanSysMenuDao;



    @Override
    public Set<SysMenuEntity> getMenusByRoleIds(List<Long> roleIds) {
        if (!roleIds.isEmpty()){
            Set<SysMenuEntity> menus = new HashSet<>();
            for (Long roleId : roleIds) {
                Set<SysMenuEntity> tempMenu = bendanSysMenuDao.listMenusByRoleId(roleId);
                menus.addAll(tempMenu);
            }
            return menus;
        }
        return new HashSet<>();
    }

    @Override
    public Long getIdByTitle(String title) throws NullPointerException{
        LambdaQueryWrapper<SysMenuEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenuEntity::getName, title);
        SysMenuEntity sysMenuEntity = this.getOne(wrapper);
        if (sysMenuEntity != null) {
            return Optional.ofNullable(sysMenuEntity.getId()).orElseThrow(NullPointerException::new);
        }
        return null;
    }

    @Override
    public PageObjects<SysMenuEntity> queryPage(JSONObject params) {
        String key =  params.getStr("orderField");
        QueryWrapper<SysMenuEntity> queryWrapper = getWrapper();
        IPage<SysMenuEntity> page = this.page(
                new PageQueryUtils<SysMenuEntity>().getPage(params, key, false),
                queryWrapper
        );
        return new PageQueryUtils<>().getPageObjects(page, SysMenuEntity.class);
    }

    @Override
    public List<SysMenuEntity> queryByConditions() {
        QueryWrapper<SysMenuEntity> wrapper = getWrapper();
        return this.list(wrapper);
    }



    @Override
    public List<SysMenuEntity> queryAll() {
        return this.list();
    }

    @Override
    public SysMenuEntity queryById(Long id) {
        return this.getById(id);
    }


    @Override
    public boolean add(SysMenuEntity sysMenuEntity) {
        return this.save(sysMenuEntity);
    }



    @Override
    public boolean addList(List<SysMenuEntity> data) {
        return this.saveBatch(data);
    }
    @Override
    public boolean replace(SysMenuEntity sysMenuEntity) {
        return this.updateById(sysMenuEntity);
    }

    @Override
    public boolean replaceList(List<SysMenuEntity> data) {
        return this.updateBatchById(data);
    }

    @Override
    public boolean deleteById(Long id) {
        return this.removeById(id);
    }


    @Override
    public boolean deleteByIds(List<Long> ids) {
        return this.removeByIds(ids);
    }


    /**
     * Description: 自定义QueryWrapper
     * @author obeast-dragon
     * Date 2022-11-30 10:42:42
     * @return QueryWrapper<SysMenuEntity>
     */
    private QueryWrapper<SysMenuEntity> getWrapper() {
        return new QueryWrapper<>();
    }


    /**
     * Description: 自定义Excel  QueryWrapper
     * @author obeast-dragon
     * Date 2022-11-30 10:42:42
     * @return QueryWrapper<SysMenuEntity>
     */
    private QueryWrapper<SysMenuEntity> getExcelWrapper() {
        return new QueryWrapper<>();
    }

}
