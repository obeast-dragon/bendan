package com.obeast.admin.service.impl;

import com.obeast.oss.domain.PageObjects;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.oss.utils.PageQueryUtils;

import com.obeast.admin.dao.AdminUserDao;
import com.obeast.admin.entity.AdminUserEntity;
import com.obeast.admin.excel.AdminUserExcel;
import com.obeast.admin.service.AdminUserService;


/**
 * @author obeast-dragon
 * Date 2022-10-04 00:20:25
 * @version 1.0
 * Description: 管理系统用户
 */
@Service("adminUserService")
public class AdminUserServiceImpl extends ServiceImpl<AdminUserDao, AdminUserEntity> implements AdminUserService {


    @Override
    public PageObjects<AdminUserEntity> queryPage(Map<String, Object> params) {
        String key = (String) params.get("orderField");
        QueryWrapper<AdminUserEntity> queryWrapper = getWrapper();
        IPage<AdminUserEntity> page = this.page(
                new PageQueryUtils<AdminUserEntity>().getPage(params, key, false),
                queryWrapper
        );
        return new PageQueryUtils<>().getPageObjects(page, AdminUserEntity.class);
    }

    @Override
    public List<AdminUserEntity> queryByConditions() {
        QueryWrapper<AdminUserEntity> wrapper = getWrapper();
        return this.list(wrapper);
    }

    @Override
    public List<AdminUserExcel> queryExcelByConditions() {
        QueryWrapper<AdminUserEntity> wrapper = getExcelWrapper();
        return this.list(wrapper).stream().map(item -> {
            return new AdminUserExcel();
        }).collect(Collectors.toList());
    }

    @Override
    public List<AdminUserEntity> queryAll() {
        return this.list();
    }

    @Override
    public AdminUserEntity queryById(Long id) {
        return this.getById(id);
    }


    @Override
    public boolean add(AdminUserEntity adminUserEntity) {
        return this.save(adminUserEntity);
    }



    @Override
    public boolean addList(List<AdminUserEntity> data) {
        return this.saveBatch(data);
    }
    @Override
    public boolean replace(AdminUserEntity adminUserEntity) {
        return this.updateById(adminUserEntity);
    }

    @Override
    public boolean replaceList(List<AdminUserEntity> data) {
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
     * Date 2022-10-04 00:20:25
     * @return QueryWrapper<AdminUserEntity>
     */
    private QueryWrapper<AdminUserEntity> getWrapper() {
        return new QueryWrapper<>();
    }


    /**
     * Description: 自定义Excel  QueryWrapper
     * @author obeast-dragon
     * Date 2022-10-04 00:20:25
     * @return QueryWrapper<AdminUserEntity>
     */
    private QueryWrapper<AdminUserEntity> getExcelWrapper() {
        return new QueryWrapper<>();
    }

}
