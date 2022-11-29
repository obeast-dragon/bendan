package com.obeast.admin.business.service.impl;

import com.obeast.core.domain.PageObjects;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.core.utils.PageQueryUtils;

import com.obeast.admin.business.dao.ButtonPermissionDao;
import com.obeast.admin.business.entity.ButtonPermissionEntity;
import com.obeast.admin.business.service.ButtonPermissionService;


/**
 * @author obeast-dragon
 * Date 2022-10-12 20:10:00
 * @version 1.0
 * Description: 按钮权限表
 */
@Service("buttonPermissionService")
public class ButtonPermissionServiceImpl extends ServiceImpl<ButtonPermissionDao, ButtonPermissionEntity> implements ButtonPermissionService {


    @Override
    public PageObjects<ButtonPermissionEntity> queryPage(Map<String, Object> params) {
        String key = (String) params.get("orderField");
        QueryWrapper<ButtonPermissionEntity> queryWrapper = getWrapper();
        IPage<ButtonPermissionEntity> page = this.page(
                new PageQueryUtils<ButtonPermissionEntity>().getPage(params, key, false),
                queryWrapper
        );
        return new PageQueryUtils<>().getPageObjects(page, ButtonPermissionEntity.class);
    }

    @Override
    public List<ButtonPermissionEntity> queryByConditions() {
        QueryWrapper<ButtonPermissionEntity> wrapper = getWrapper();
        return this.list(wrapper);
    }



    @Override
    public List<ButtonPermissionEntity> queryAll() {
        return this.list();
    }

    @Override
    public ButtonPermissionEntity queryById(Long id) {
        return this.getById(id);
    }


    @Override
    public boolean add(ButtonPermissionEntity buttonPermissionEntity) {
        return this.save(buttonPermissionEntity);
    }



    @Override
    public boolean addList(List<ButtonPermissionEntity> data) {
        return this.saveBatch(data);
    }
    @Override
    public boolean replace(ButtonPermissionEntity buttonPermissionEntity) {
        return this.updateById(buttonPermissionEntity);
    }

    @Override
    public boolean replaceList(List<ButtonPermissionEntity> data) {
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
     * Date 2022-10-12 20:10:00
     * @return QueryWrapper<ButtonPermissionEntity>
     */
    private QueryWrapper<ButtonPermissionEntity> getWrapper() {
        return new QueryWrapper<>();
    }


    /**
     * Description: 自定义Excel  QueryWrapper
     * @author obeast-dragon
     * Date 2022-10-12 20:10:00
     * @return QueryWrapper<ButtonPermissionEntity>
     */
    private QueryWrapper<ButtonPermissionEntity> getExcelWrapper() {
        return new QueryWrapper<>();
    }

}
