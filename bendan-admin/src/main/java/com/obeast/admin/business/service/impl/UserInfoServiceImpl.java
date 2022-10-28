package com.obeast.admin.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.obeast.admin.business.service.remote.OAuth2Remote;
import com.obeast.admin.business.service.UserInfoService;
import com.obeast.common.base.CommonResult;
import com.obeast.common.constant.OAuth2Constant;
import com.obeast.common.domain.PageObjects;

import com.obeast.common.to.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    private OAuth2Remote OAuth2Remote;

    @Override
    public CommonResult<?> login(String username, String password) {
//        Map<String, String> param = Map.of(
//                OAuth2Constant.RequestTokenParam.CLIENT_ID.getName(), "messaging-client",
//                OAuth2Constant.RequestTokenParam.CLIENT_SECRET.getName(), "secret",
//                OAuth2Constant.RequestTokenParam.GRANT_TYPE.getName(), OAuth2Constant.GrantType.PASSWORD.getName(),
//                OAuth2Constant.RequestTokenParam.USERNAME.getName(), username,
//                OAuth2Constant.RequestTokenParam.PASSWORD.getName(), password
//        );
        Map<String, String> params = new HashMap<>();
        params.put("client_id", "messaging-client" );
        params.put("client_secret","secret");
        params.put("grant_type","password");
        params.put("username",username);
        params.put("password",password);
//        return OAuth2Remote.getAccessToken(null);
        return null;
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
     * @author obeast-dragon
     * Date 2022-10-11 21:02:40
     * @return QueryWrapper<UserInfoEntity>
     */
    private QueryWrapper<UserInfoEntity> getWrapper() {
        return new QueryWrapper<>();
    }


    private LambdaQueryWrapper<UserInfoEntity> getLambdaQueryWrapper() {
        return new LambdaQueryWrapper<UserInfoEntity>();
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
