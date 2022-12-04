package com.obeast.security.business.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.utils.PageQueryUtils;
import com.obeast.entity.BendanSysRole;
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
public class BendanSysRoleServiceImpl extends ServiceImpl<BendanSysRoleDao, BendanSysRole>
        implements BendanSysRoleService {


    @Override
    public PageObjects<BendanSysRole> queryPage(JSONObject params) {
        String key =  params.getStr("orderField");
        QueryWrapper<BendanSysRole> queryWrapper = getWrapper();
        IPage<BendanSysRole> page = this.page(
                new PageQueryUtils<BendanSysRole>().getPage(params, key, false),
                queryWrapper
        );
        return new PageQueryUtils<>().getPageObjects(page, BendanSysRole.class);
    }

    @Override
    public List<BendanSysRole> queryByConditions() {
        QueryWrapper<BendanSysRole> wrapper = getWrapper();
        return this.list(wrapper);
    }


    @Override
    public List<BendanSysRole> queryAll() {
        return this.list();
    }

    @Override
    public BendanSysRole queryById(Long id) {
        return this.getById(id);
    }


    @Override
    public boolean add(BendanSysRole bendanSysRole) {
        return this.save(bendanSysRole);
    }



    @Override
    public boolean addList(List<BendanSysRole> data) {
        return this.saveBatch(data);
    }
    @Override
    public boolean replace(BendanSysRole bendanSysRole) {
        return this.updateById(bendanSysRole);
    }

    @Override
    public boolean replaceList(List<BendanSysRole> data) {
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
    private QueryWrapper<BendanSysRole> getWrapper() {
        return new QueryWrapper<>();
    }


    /**
     * Description: 自定义Excel  QueryWrapper
     * @author obeast-dragon
     * Date 2022-11-30 10:42:42
     * @return QueryWrapper<SysRoleEntity>
     */
    private QueryWrapper<BendanSysRole> getExcelWrapper() {
        return new QueryWrapper<>();
    }

}
