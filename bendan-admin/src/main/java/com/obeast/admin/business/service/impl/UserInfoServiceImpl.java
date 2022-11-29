package com.obeast.admin.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.obeast.admin.business.vo.OAuth2Params;
import com.obeast.admin.business.service.remote.OAuth2Remote;
import com.obeast.admin.business.service.UserInfoService;
import com.obeast.core.base.CommonResult;
import com.obeast.core.domain.PageObjects;

import com.obeast.core.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.core.utils.PageQueryUtils;

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
    private OAuth2Remote OAuth2Remote;

    @Override
    public CommonResult<?> login(String username, String password) {
        OAuth2Params oAuth2Params = new OAuth2Params();
        oAuth2Params.setClient_id("messaging-client");
        oAuth2Params.setClient_secret("secret");
        oAuth2Params.setGrant_type("password");
        oAuth2Params.setUsername(username);
        oAuth2Params.setPassword(password);
        return OAuth2Remote.getAccessToken(oAuth2Params);
    }


    @Override
    public UserInfoDto loadUserByUsername(String username) {
        UserInfoEntity userInfoEntity = this.getOne(
                getLambdaQueryWrapper().eq(UserInfoEntity::getUsername, username)
        );
        if (userInfoEntity != null) {
            UserInfoDto userInfoDto = new UserInfoDto();
            userInfoDto.setId(userInfoEntity.getUserId());
            userInfoDto.setUsername(username);
            userInfoDto.setPassword(userInfoEntity.getPassword());
            userInfoDto.setStatus(userInfoEntity.getUseStatus());
            userInfoDto.setClientId(null);
            return userInfoDto;
        }
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
     *
     * @return QueryWrapper<UserInfoEntity>
     * @author obeast-dragon
     * Date 2022-10-11 21:02:40
     */
    private QueryWrapper<UserInfoEntity> getWrapper() {
        return new QueryWrapper<>();
    }


    private LambdaQueryWrapper<UserInfoEntity> getLambdaQueryWrapper() {
        return new LambdaQueryWrapper<UserInfoEntity>();
    }


    /**
     * Description: 自定义Excel  QueryWrapper
     *
     * @return QueryWrapper<UserInfoEntity>
     * @author obeast-dragon
     * Date 2022-10-11 21:02:40
     */
    private QueryWrapper<UserInfoEntity> getExcelWrapper() {
        return new QueryWrapper<>();
    }


}
