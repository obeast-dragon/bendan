package com.obeast.bendan.service.impl;

import com.obeast.bendan.common.domain.PageObjects;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.bendan.common.utils.PageQueryUtils;

import com.obeast.bendan.dao.TypeDao;
import com.obeast.bendan.entity.TypeEntity;
import com.obeast.bendan.excel.TypeExcel;
import com.obeast.bendan.service.TypeService;


/**
 * @author obeast-dragon
 * Date 2022-09-21 10:03:18
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
     * Date 2022-09-21 10:03:18
     * @return QueryWrapper<TypeEntity>
     */
    private QueryWrapper<TypeEntity> getWrapper() {
        return new QueryWrapper<>();
    }


    /**
     * Description: 自定义Excel  QueryWrapper
     * @author obeast-dragon
     * Date 2022-09-21 10:03:18
     * @return QueryWrapper<TypeEntity>
     */
    private QueryWrapper<TypeEntity> getExcelWrapper() {
        return new QueryWrapper<>();
    }

}
