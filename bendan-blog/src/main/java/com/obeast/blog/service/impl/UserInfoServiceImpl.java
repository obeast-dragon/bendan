package com.obeast.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.blog.dao.UserInfoDao;
import com.obeast.blog.entity.UserInfoEntity;
import com.obeast.blog.excel.UserInfoExcel;
import com.obeast.oss.domain.PageObjects;
import com.obeast.oss.utils.PageQueryUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.obeast.blog.service.UserInfoService;


/**
 * @author obeast-dragon
 * Date 2022-09-21 12:18:12
 * @version 1.0
 * Description: 
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfoEntity> implements UserInfoService {


    @Override
    public PageObjects<UserInfoEntity> queryPage(Map<String, Object> params) {
        String key = (String) params.get("orderField");
        QueryWrapper<UserInfoEntity> queryWrapper = getWrapper();
        IPage<UserInfoEntity> page = this.page(
                new PageQueryUtils<UserInfoEntity>().getPage(params, key, false),
                queryWrapper
        );
        return new PageQueryUtils<>().getPageObjects(page, UserInfoEntity.class);
    }

    @Override
    public List<UserInfoEntity> queryByConditions() {
        QueryWrapper<UserInfoEntity> wrapper = getWrapper();
        return this.list(wrapper);
    }

    @Override
    public List<UserInfoExcel> queryExcelByConditions() {
        QueryWrapper<UserInfoEntity> wrapper = getExcelWrapper();
        return this.list(wrapper).stream().map(item -> {
            return new UserInfoExcel();
        }).collect(Collectors.toList());
    }

    @Override
    public List<UserInfoEntity> queryAll() {
        return this.list();
    }

    @Override
    public UserInfoEntity queryById(Long id) {
        return this.getById(id);
    }


    @Override
    public boolean add(UserInfoEntity userInfoEntity) {
        return this.save(userInfoEntity);
    }



    @Override
    public boolean addList(List<UserInfoEntity> data) {
        return this.saveBatch(data);
    }
    @Override
    public boolean replace(UserInfoEntity userInfoEntity) {
        return this.updateById(userInfoEntity);
    }

    @Override
    public boolean replaceList(List<UserInfoEntity> data) {
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
     * @return QueryWrapper<UserInfoEntity>
     */
    private QueryWrapper<UserInfoEntity> getWrapper() {
        return new QueryWrapper<>();
    }


    /**
     * Description: 自定义Excel  QueryWrapper
     * @author obeast-dragon
     * Date 2022-09-21 12:18:12
     * @return QueryWrapper<UserInfoEntity>
     */
    private QueryWrapper<UserInfoEntity> getExcelWrapper() {
        return new QueryWrapper<>();
    }

}
