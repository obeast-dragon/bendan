package com.obeast.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.blog.entity.TagsEntity;
import com.obeast.blog.excel.TagsExcel;
import com.obeast.oss.domain.PageObjects;
import com.obeast.oss.utils.PageQueryUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import com.obeast.blog.dao.TagsDao;

import com.obeast.blog.service.TagsService;


/**
 * @author obeast-dragon
 * Date 2022-09-21 12:18:12
 * @version 1.0
 * Description: 
 */
@Service("tagsService")
public class TagsServiceImpl extends ServiceImpl<TagsDao, TagsEntity> implements TagsService {


    @Override
    public PageObjects<TagsEntity> queryPage(Map<String, Object> params) {
        String key = (String) params.get("orderField");
        QueryWrapper<TagsEntity> queryWrapper = getWrapper();
        IPage<TagsEntity> page = this.page(
                new PageQueryUtils<TagsEntity>().getPage(params, key, false),
                queryWrapper
        );
        return new PageQueryUtils<>().getPageObjects(page, TagsEntity.class);
    }

    @Override
    public List<TagsEntity> queryByConditions() {
        QueryWrapper<TagsEntity> wrapper = getWrapper();
        return this.list(wrapper);
    }

    @Override
    public List<TagsExcel> queryExcelByConditions() {
        QueryWrapper<TagsEntity> wrapper = getExcelWrapper();
        return this.list(wrapper).stream().map(item -> {
            return new TagsExcel();
        }).collect(Collectors.toList());
    }

    @Override
    public List<TagsEntity> queryAll() {
        return this.list();
    }

    @Override
    public TagsEntity queryById(Long id) {
        return this.getById(id);
    }


    @Override
    public boolean add(TagsEntity tagsEntity) {
        return this.save(tagsEntity);
    }



    @Override
    public boolean addList(List<TagsEntity> data) {
        return this.saveBatch(data);
    }
    @Override
    public boolean replace(TagsEntity tagsEntity) {
        return this.updateById(tagsEntity);
    }

    @Override
    public boolean replaceList(List<TagsEntity> data) {
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
     * @return QueryWrapper<TagsEntity>
     */
    private QueryWrapper<TagsEntity> getWrapper() {
        return new QueryWrapper<>();
    }


    /**
     * Description: 自定义Excel  QueryWrapper
     * @author obeast-dragon
     * Date 2022-09-21 12:18:12
     * @return QueryWrapper<TagsEntity>
     */
    private QueryWrapper<TagsEntity> getExcelWrapper() {
        return new QueryWrapper<>();
    }

}
