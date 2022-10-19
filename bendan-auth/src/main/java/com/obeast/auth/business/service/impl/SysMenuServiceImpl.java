package com.obeast.auth.business.service.impl;

import com.obeast.common.domain.PageObjects;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.common.utils.PageQueryUtils;

import com.obeast.auth.business.dao.SysMenuDao;
import com.obeast.auth.business.entity.SysMenuEntity;
import com.obeast.auth.business.service.SysMenuService;


/**
 * @adminor obeast-dragon
 * Date 2022-10-12 20:10:00
 * @version 1.0
 * Description: 菜单表
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {


    @Override
    public PageObjects<SysMenuEntity> queryPage(Map<String, Object> params) {
        String key = (String) params.get("orderField");
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
    public List<SysMenuEntity> queryAllTree() {
        List<SysMenuEntity> entities = this.list();
        List<SysMenuEntity> level = entities.stream().filter(sysMenuEntity ->
                sysMenuEntity.getParentId() == 0
        ).peek(menu -> {
            menu.setChildren(getMenuChildren(menu, entities));
        }).sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))).collect(Collectors.toList());
        return level;
    }
    private List<SysMenuEntity> getMenuChildren(SysMenuEntity root, List<SysMenuEntity> all){
        List<SysMenuEntity> collect = all.stream().filter(sysMenuEntity ->
                sysMenuEntity.getParentId().equals(root.getId())
        ).peek(sysMenuEntity -> {
            sysMenuEntity.setChildren(getMenuChildren(sysMenuEntity, all));
        }).sorted(Comparator
                .comparingInt(menu -> menu.getSort() == null ? 0 : menu.getSort())
        ).collect(Collectors.toList());
        return collect;
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
     * @adminor obeast-dragon
     * Date 2022-10-12 20:10:00
     * @return QueryWrapper<SysMenuEntity>
     */
    private QueryWrapper<SysMenuEntity> getWrapper() {
        return new QueryWrapper<>();
    }


    /**
     * Description: 自定义Excel  QueryWrapper
     * @adminor obeast-dragon
     * Date 2022-10-12 20:10:00
     * @return QueryWrapper<SysMenuEntity>
     */
    private QueryWrapper<SysMenuEntity> getExcelWrapper() {
        return new QueryWrapper<>();
    }

}
