package com.obeast.security.business.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.utils.PageQueryUtils;
import com.obeast.business.entity.SysRoleEntity;
import com.obeast.security.business.dao.BendanSysRoleDao;
import com.obeast.security.business.service.BendanSysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author obeast-dragon
 * Date 2022-11-30 10:42:42
 * @version 1.0
 * Description: 角色表
 */
@Service("sysRoleService")
public class BendanSysRoleServiceImpl extends ServiceImpl<BendanSysRoleDao, SysRoleEntity>
        implements BendanSysRoleService {


    @Override
    public PageObjects<SysRoleEntity> queryPage(JSONObject params) {
        String key =  params.getStr("orderField");
        QueryWrapper<SysRoleEntity> queryWrapper = getWrapper();
        IPage<SysRoleEntity> page = this.page(
                new PageQueryUtils<SysRoleEntity>().getPage(params, key, false),
                queryWrapper
        );
        return new PageQueryUtils<>().getPageObjects(page, SysRoleEntity.class);
    }

    @Override
    public List<SysRoleEntity> queryByConditions() {
        QueryWrapper<SysRoleEntity> wrapper = getWrapper();
        return this.list(wrapper);
    }


    @Override
    public List<SysRoleEntity> queryAll() {
        return this.list();
    }

    @Override
    public SysRoleEntity queryById(Long id) {
        return this.getById(id);
    }


    @Override
    public boolean add(SysRoleEntity sysRoleEntity) {
        return this.save(sysRoleEntity);
    }



    @Override
    public boolean addList(List<SysRoleEntity> data) {
        return this.saveBatch(data);
    }
    @Override
    public boolean replace(SysRoleEntity sysRoleEntity) {
        return this.updateById(sysRoleEntity);
    }

    @Override
    public boolean replaceList(List<SysRoleEntity> data) {
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
     * Date 2022-11-30 10:42:42
     * @return QueryWrapper<SysRoleEntity>
     */
    private QueryWrapper<SysRoleEntity> getWrapper() {
        return new QueryWrapper<>();
    }


    /**
     * Description: 自定义Excel  QueryWrapper
     * @author obeast-dragon
     * Date 2022-11-30 10:42:42
     * @return QueryWrapper<SysRoleEntity>
     */
    private QueryWrapper<SysRoleEntity> getExcelWrapper() {
        return new QueryWrapper<>();
    }

}
