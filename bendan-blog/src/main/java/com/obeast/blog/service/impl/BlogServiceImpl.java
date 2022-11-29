package com.obeast.blog.service.impl;

import com.obeast.blog.excel.BlogExcel;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.utils.PageQueryUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.obeast.blog.dao.BlogDao;
import com.obeast.blog.entity.BlogEntity;
import com.obeast.blog.service.BlogService;


/**
 * @author obeast-dragon
 * Date 2022-09-21 12:18:12
 * @version 1.0
 * Description: 
 */
@Service("blogService")
public class BlogServiceImpl extends ServiceImpl<BlogDao, BlogEntity> implements BlogService {


    @Override
    public PageObjects<BlogEntity> queryPage(Map<String, Object> params) {
        String key = (String) params.get("orderField");
        QueryWrapper<BlogEntity> queryWrapper = getWrapper();
        IPage<BlogEntity> page = this.page(
                new PageQueryUtils<BlogEntity>().getPage(params, key, false),
                queryWrapper
        );
        return new PageQueryUtils<>().getPageObjects(page, BlogEntity.class);
    }

    @Override
    public List<BlogEntity> queryByConditions() {
        QueryWrapper<BlogEntity> wrapper = getWrapper();
        return this.list(wrapper);
    }

    @Override
    public List<BlogExcel> queryExcelByConditions() {
        QueryWrapper<BlogEntity> wrapper = getExcelWrapper();
        return this.list(wrapper).stream().map(item -> {
            return new BlogExcel();
        }).collect(Collectors.toList());
    }

    @Override
    public List<BlogEntity> queryAll() {
        return this.list();
    }

    @Override
    public BlogEntity queryById(Long id) {
        return this.getById(id);
    }


    @Override
    public boolean add(BlogEntity blogEntity) {
        return this.save(blogEntity);
    }



    @Override
    public boolean addList(List<BlogEntity> data) {
        return this.saveBatch(data);
    }
    @Override
    public boolean replace(BlogEntity blogEntity) {
        return this.updateById(blogEntity);
    }

    @Override
    public boolean replaceList(List<BlogEntity> data) {
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
     * @return QueryWrapper<BlogEntity>
     */
    private QueryWrapper<BlogEntity> getWrapper() {
        return new QueryWrapper<>();
    }


    /**
     * Description: 自定义Excel  QueryWrapper
     * @author obeast-dragon
     * Date 2022-09-21 12:18:12
     * @return QueryWrapper<BlogEntity>
     */
    private QueryWrapper<BlogEntity> getExcelWrapper() {
        return new QueryWrapper<>();
    }

}
