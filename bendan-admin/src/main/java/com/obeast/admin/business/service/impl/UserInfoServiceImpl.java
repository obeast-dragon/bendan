package com.obeast.admin.business.service.impl;

import com.obeast.admin.business.remote.AuthRemote;
import com.obeast.admin.business.service.UserInfoService;
import com.obeast.common.base.CommonResult;
import com.obeast.common.constant.AuthConstant;
import com.obeast.common.domain.PageObjects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.common.utils.PageQueryUtils;

import com.obeast.admin.business.dao.UserInfoDao;
import com.obeast.admin.business.entity.UserInfoEntity;


/**
 * @author obeast-dragon
 * Date 2022-10-11 21:02:40
 * @version 1.0
 * Description: 
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfoEntity> implements UserInfoService {

    @Autowired
    private AuthRemote authRemote;

    @Override
    public CommonResult<?> login(String username, String password) {
        Map<String, String> param = Map.of(
                AuthConstant.RequestTokenParam.CLIENT_ID.getName(), AuthConstant.Type.WEB_BROWSER.getName(),
                AuthConstant.RequestTokenParam.CLIENT_SECRET.getName(), "123456",
                AuthConstant.RequestTokenParam.GRANT_TYPE.getName(), AuthConstant.GrantType.PASSWORD.getName(),
                AuthConstant.RequestTokenParam.USERNAME.getName(), username,
                AuthConstant.RequestTokenParam.PASSWORD.getName(), password
        );
        authRemote.getAccessToken(param);
        return null;
    }



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
    public boolean deleteById(Long userId) {
        return this.removeById(userId);
    }



    @Override
    public boolean deleteByIds(List<Long> ids) {
        return this.removeByIds(ids);
    }


    /**
     * Description: 自定义QueryWrapper
     * @author obeast-dragon
     * Date 2022-10-11 21:02:40
     * @return QueryWrapper<UserInfoEntity>
     */
    private QueryWrapper<UserInfoEntity> getWrapper() {
        return new QueryWrapper<>();
    }


    /**
     * Description: 自定义Excel  QueryWrapper
     * @author obeast-dragon
     * Date 2022-10-11 21:02:40
     * @return QueryWrapper<UserInfoEntity>
     */
    private QueryWrapper<UserInfoEntity> getExcelWrapper() {
        return new QueryWrapper<>();
    }


}
