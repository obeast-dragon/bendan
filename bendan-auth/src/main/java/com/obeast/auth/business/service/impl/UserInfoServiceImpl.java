package com.obeast.auth.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.obeast.auth.business.service.UserInfoService;
import com.obeast.common.constant.UserStatusConstant;
import com.obeast.common.domain.PageObjects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.common.utils.PageQueryUtils;

import com.obeast.auth.business.dao.UserInfoDao;
import com.obeast.auth.business.entity.UserInfoEntity;

import javax.validation.constraints.NotNull;


/**
 * @author obeast-dragon
 * Date 2022-10-11 21:02:40
 * @version 1.0
 * Description: 
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfoEntity> implements UserInfoService, UserDetailsService {


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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfoEntity user = findUserByUsername(username);

        if (Objects.isNull(user)){
            throw new UsernameNotFoundException("Username not found");
        }

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.NO_AUTHORITIES;
        return User.builder()
                .username(username)
                .password(user.getPassword())
                .authorities(grantedAuthorities)
                .accountLocked(UserStatusConstant.map(user.getUseStatus()))
                .accountExpired(UserStatusConstant.map(user.getUserExpired()))
                .disabled(UserStatusConstant.map(user.getUserAble()))
                .build();
    }

    /**
     * Description: 根据用户名查找用户
     * @author wxl
     * Date: 2022/10/19 14:34
     * @param username 用户名
     * @return com.obeast.auth.business.entity.UserInfoEntity
     */
    private UserInfoEntity findUserByUsername(String username) throws UsernameNotFoundException {
        return this.getOne(
                new LambdaQueryWrapper<UserInfoEntity>()
                        .eq(UserInfoEntity::getUsername, username)
        );
    }
}
