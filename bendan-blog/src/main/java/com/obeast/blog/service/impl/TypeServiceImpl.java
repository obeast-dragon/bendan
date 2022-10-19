package com.obeast.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.blog.entity.TypeEntity;
import com.obeast.blog.excel.TypeExcel;
import com.obeast.common.domain.PageObjects;
import com.obeast.common.utils.PageQueryUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import com.obeast.blog.dao.TypeDao;

import com.obeast.blog.service.TypeService;


/**
 * @author obeast-dragon
 * Date 2022-09-21 12:18:12
 * @version 1.0
 * Description: 
 */
@Service("typeService")
public class TypeServiceImpl extends ServiceImpl<TypeDao, TypeEntity> implements TypeService {


    @Override
    public PageObjects<TypeEntity> queryPage(Map<String, Object> params) {
        String key = (String) params.get("orderField");
        QueryWrapper<TypeEntity> queryWrapper = getWrapper();
        IPage<TypeEntity> page = this.page(
                new PageQueryUtils<TypeEntity>().getPage(params, key, false),
                queryWrapper
        );
        return new PageQueryUtils<>().getPageObjects(page, TypeEntity.class);
    }

    @Override
    public List<TypeEntity> queryByConditions() {
        QueryWrapper<TypeEntity> wrapper = getWrapper();
        return this.list(wrapper);
    }

    @Override
    public List<TypeExcel> queryExcelByConditions() {
        QueryWrapper<TypeEntity> wrapper = getExcelWrapper();
        return this.list(wrapper).stream().map(item -> {
            return new TypeExcel();
        }).collect(Collectors.toList());
    }

    @Override
    public List<TypeEntity> queryAll() {
        return this.list();
    }

    @Override
    public TypeEntity queryById(Long id) {
        return this.getById(id);
    }


    @Override
    public boolean add(TypeEntity typeEntity) {
        return this.save(typeEntity);
    }



    @Override
    public boolean addList(List<TypeEntity> data) {
        return this.saveBatch(data);
    }
    @Override
    public boolean replace(TypeEntity typeEntity) {
        return this.updateById(typeEntity);
    }

    @Override
    public boolean replaceList(List<TypeEntity> data) {
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
     * Date 2022-09-21 12:18:12
     * @return QueryWrapper<TypeEntity>
     */
    private QueryWrapper<TypeEntity> getWrapper() {
        return new QueryWrapper<>();
    }


    /**
     * Description: 自定义Excel  QueryWrapper
     * @author obeast-dragon
     * Date 2022-09-21 12:18:12
     * @return QueryWrapper<TypeEntity>
     */
    private QueryWrapper<TypeEntity> getExcelWrapper() {
        return new QueryWrapper<>();
    }

}
