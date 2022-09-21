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

import com.obeast.bendan.dao.CommentDao;
import com.obeast.bendan.entity.CommentEntity;
import com.obeast.bendan.excel.CommentExcel;
import com.obeast.bendan.service.CommentService;


/**
 * @author obeast-dragon
 * Date 2022-09-21 10:03:18
 * @version 1.0
 * Description: 
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {


    @Override
    public PageObjects<CommentEntity> queryPage(Map<String, Object> params) {
        String key = (String) params.get("orderField");
        QueryWrapper<CommentEntity> queryWrapper = getWrapper();
        IPage<CommentEntity> page = this.page(
                new PageQueryUtils<CommentEntity>().getPage(params, key, false),
                queryWrapper
        );
        return new PageQueryUtils<>().getPageObjects(page, CommentEntity.class);
    }

    @Override
    public List<CommentEntity> queryByConditions() {
        QueryWrapper<CommentEntity> wrapper = getWrapper();
        return this.list(wrapper);
    }

    @Override
    public List<CommentExcel> queryExcelByConditions() {
        QueryWrapper<CommentEntity> wrapper = getExcelWrapper();
        return this.list(wrapper).stream().map(item -> {
            return new CommentExcel();
        }).collect(Collectors.toList());
    }

    @Override
    public List<CommentEntity> queryAll() {
        return this.list();
    }

    @Override
    public CommentEntity queryById(Long id) {
        return this.getById(id);
    }


    @Override
    public boolean add(CommentEntity commentEntity) {
        return this.save(commentEntity);
    }



    @Override
    public boolean addList(List<CommentEntity> data) {
        return this.saveBatch(data);
    }
    @Override
    public boolean replace(CommentEntity commentEntity) {
        return this.updateById(commentEntity);
    }

    @Override
    public boolean replaceList(List<CommentEntity> data) {
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
     * @return QueryWrapper<CommentEntity>
     */
    private QueryWrapper<CommentEntity> getWrapper() {
        return new QueryWrapper<>();
    }


    /**
     * Description: 自定义Excel  QueryWrapper
     * @author obeast-dragon
     * Date 2022-09-21 10:03:18
     * @return QueryWrapper<CommentEntity>
     */
    private QueryWrapper<CommentEntity> getExcelWrapper() {
        return new QueryWrapper<>();
    }

}
