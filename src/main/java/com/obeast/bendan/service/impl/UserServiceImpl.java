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

import com.obeast.bendan.dao.UserDao;
import com.obeast.bendan.entity.UserEntity;
import com.obeast.bendan.excel.UserExcel;
import com.obeast.bendan.service.UserService;


/**
 * @author obeast-dragon
 * Date 2022-09-21 10:03:18
 * @version 1.0
 * Description: 
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {


    @Override
    public PageObjects<UserEntity> queryPage(Map<String, Object> params) {
        String key = (String) params.get("orderField");
        QueryWrapper<UserEntity> queryWrapper = getWrapper();
        IPage<UserEntity> page = this.page(
                new PageQueryUtils<UserEntity>().getPage(params, key, false),
                queryWrapper
        );
        return new PageQueryUtils<>().getPageObjects(page, UserEntity.class);
    }

    @Override
    public List<UserEntity> queryByConditions() {
        QueryWrapper<UserEntity> wrapper = getWrapper();
        return this.list(wrapper);
    }

    @Override
    public List<UserExcel> queryExcelByConditions() {
        QueryWrapper<UserEntity> wrapper = getExcelWrapper();
        return this.list(wrapper).stream().map(item -> {
            return new UserExcel();
        }).collect(Collectors.toList());
    }

    @Override
    public List<UserEntity> queryAll() {
        return this.list();
    }

    @Override
    public UserEntity queryById(Long id) {
        return this.getById(id);
    }


    @Override
    public boolean add(UserEntity userEntity) {
        return this.save(userEntity);
    }



    @Override
    public boolean addList(List<UserEntity> data) {
        return this.saveBatch(data);
    }
    @Override
    public boolean replace(UserEntity userEntity) {
        return this.updateById(userEntity);
    }

    @Override
    public boolean replaceList(List<UserEntity> data) {
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
     * @return QueryWrapper<UserEntity>
     */
    private QueryWrapper<UserEntity> getWrapper() {
        return new QueryWrapper<>();
    }


    /**
     * Description: 自定义Excel  QueryWrapper
     * @author obeast-dragon
     * Date 2022-09-21 10:03:18
     * @return QueryWrapper<UserEntity>
     */
    private QueryWrapper<UserEntity> getExcelWrapper() {
        return new QueryWrapper<>();
    }

}
