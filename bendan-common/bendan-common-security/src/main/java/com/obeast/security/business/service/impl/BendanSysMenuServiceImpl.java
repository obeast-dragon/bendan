package com.obeast.security.business.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.utils.PageQueryUtils;
import com.obeast.business.entity.BendanSysMenu;
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
public class BendanSysMenuServiceImpl extends ServiceImpl<BendanSysMenuDao, BendanSysMenu>
        implements BendanSysMenuService {

    private final BendanSysMenuDao bendanSysMenuDao;



    @Override
    public Set<BendanSysMenu> getMenusByRoleIds(List<Long> roleIds) {
        if (!roleIds.isEmpty()){
            Set<BendanSysMenu> menus = new HashSet<>();
            for (Long roleId : roleIds) {
                Set<BendanSysMenu> tempMenu = bendanSysMenuDao.listMenusByRoleId(roleId);
                menus.addAll(tempMenu);
            }
            return menus;
        }
        return new HashSet<>();
    }

    @Override
    public Long getIdByTitle(String title) throws NullPointerException{
        LambdaQueryWrapper<BendanSysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BendanSysMenu::getName, title);
        BendanSysMenu bendanSysMenu = this.getOne(wrapper);
        if (bendanSysMenu != null) {
            return Optional.ofNullable(bendanSysMenu.getId()).orElseThrow(NullPointerException::new);
        }
        return null;
    }

    @Override
    public PageObjects<BendanSysMenu> queryPage(JSONObject params) {
        String key =  params.getStr("orderField");
        QueryWrapper<BendanSysMenu> queryWrapper = getWrapper();
        IPage<BendanSysMenu> page = this.page(
                new PageQueryUtils<BendanSysMenu>().getPage(params, key, false),
                queryWrapper
        );
        return new PageQueryUtils<>().getPageObjects(page, BendanSysMenu.class);
    }

    @Override
    public List<BendanSysMenu> queryByConditions() {
        QueryWrapper<BendanSysMenu> wrapper = getWrapper();
        return this.list(wrapper);
    }



    @Override
    public List<BendanSysMenu> queryAll() {
        return this.list();
    }

    @Override
    public BendanSysMenu queryById(Long id) {
        return this.getById(id);
    }


    @Override
    public boolean add(BendanSysMenu bendanSysMenu) {
        return this.save(bendanSysMenu);
    }



    @Override
    public boolean addList(List<BendanSysMenu> data) {
        return this.saveBatch(data);
    }
    @Override
    public boolean replace(BendanSysMenu bendanSysMenu) {
        return this.updateById(bendanSysMenu);
    }

    @Override
    public boolean replaceList(List<BendanSysMenu> data) {
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
    private QueryWrapper<BendanSysMenu> getWrapper() {
        return new QueryWrapper<>();
    }


    /**
     * Description: 自定义Excel  QueryWrapper
     * @author obeast-dragon
     * Date 2022-11-30 10:42:42
     * @return QueryWrapper<SysMenuEntity>
     */
    private QueryWrapper<BendanSysMenu> getExcelWrapper() {
        return new QueryWrapper<>();
    }

}
