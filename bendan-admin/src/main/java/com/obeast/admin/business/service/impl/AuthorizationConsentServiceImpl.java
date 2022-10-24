package com.obeast.admin.business.service.impl;

import com.obeast.common.domain.PageObjects;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.common.utils.PageQueryUtils;

import com.obeast.admin.business.dao.AuthorizationConsentDao;
import com.obeast.admin.business.entity.AuthorizationConsentEntity;
import com.obeast.admin.business.service.AuthorizationConsentService;


/**
 * @author obeast-dragon
 * Date 2022-10-11 21:02:40
 * @version 1.0
 * Description: 
 */
@Service("authorizationConsentService")
public class AuthorizationConsentServiceImpl extends ServiceImpl<AuthorizationConsentDao, AuthorizationConsentEntity> implements AuthorizationConsentService {


    @Override
    public PageObjects<AuthorizationConsentEntity> queryPage(Map<String, Object> params) {
        String key = (String) params.get("orderField");
        QueryWrapper<AuthorizationConsentEntity> queryWrapper = getWrapper();
        IPage<AuthorizationConsentEntity> page = this.page(
                new PageQueryUtils<AuthorizationConsentEntity>().getPage(params, key, false),
                queryWrapper
        );
        return new PageQueryUtils<>().getPageObjects(page, AuthorizationConsentEntity.class);
    }

    @Override
    public AuthorizationConsentEntity queryById(String registeredClientId) {
        return this.getById(registeredClientId);
    }

    @Override
    public List<AuthorizationConsentEntity> queryByConditions() {
        QueryWrapper<AuthorizationConsentEntity> wrapper = getWrapper();
        return this.list(wrapper);
    }



    @Override
    public List<AuthorizationConsentEntity> queryAll() {
        return this.list();
    }




    @Override
    public boolean add(AuthorizationConsentEntity authorizationConsentEntity) {
        return this.save(authorizationConsentEntity);
    }



    @Override
    public boolean addList(List<AuthorizationConsentEntity> data) {
        return this.saveBatch(data);
    }
    @Override
    public boolean replace(AuthorizationConsentEntity authorizationConsentEntity) {
        return this.updateById(authorizationConsentEntity);
    }

    @Override
    public boolean replaceList(List<AuthorizationConsentEntity> data) {
        return this.updateBatchById(data);
    }

    @Override
    public boolean deleteById(String registeredClientId) {
        return this.removeById(registeredClientId);
    }

    @Override
    public boolean deleteByIds(List<String> ids) {
        return this.removeByIds(ids);
    }


    /**
     * Description: 自定义QueryWrapper
     * @author obeast-dragon
     * Date 2022-10-11 21:02:40
     * @return QueryWrapper<AuthorizationConsentEntity>
     */
    private QueryWrapper<AuthorizationConsentEntity> getWrapper() {
        return new QueryWrapper<>();
    }


    /**
     * Description: 自定义Excel  QueryWrapper
     * @author obeast-dragon
     * Date 2022-10-11 21:02:40
     * @return QueryWrapper<AuthorizationConsentEntity>
     */
    private QueryWrapper<AuthorizationConsentEntity> getExcelWrapper() {
        return new QueryWrapper<>();
    }

}
