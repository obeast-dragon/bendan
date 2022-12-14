package com.obeast.security.business.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.obeast.business.dto.SysRoleDTO;
import com.obeast.core.domain.PageObjects;
import com.obeast.business.entity.SysRoleEntity;
import com.obeast.core.exception.BendanException;

import java.util.List;


/**
 * @author obeast-dragon
 * Date 2022-11-30 10:42:42
 * @version 1.0
 * Description: 角色表
 */
public interface SysRoleService extends IService<SysRoleEntity> {


    /**
     * Description: 新增 用户 - 角色  关系
     * @author wxl
     * Date: 2022/12/8 16:48
     * @param userId   userId
     * @param roleId    roleId
     * @return java.lang.Boolean
     */
    Boolean addRoleUserRel(Long userId, Long roleId);

    /**
     * Description: 根据用户id查询角色
     * @author wxl
     * Date: 2022/12/8 15:45
     * @param userId  userId
     * @return java.util.List<com.worldintek.business.entity.SysRole>
     */
    List<SysRoleEntity> listRolesByUserId(Long userId);

    /**
     * Description: 分页查询
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param params 分页参数
     * @return PageObjects<SysRoleEntity>
     */
    PageObjects<SysRoleEntity> queryPage(JSONObject params);


    /**
     * Description: 根据Id查询
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param id id  of entity
     * @return SysRoleEntity
     */
    SysRoleEntity queryById(Long id);



    /**
     * Description: 查询所有
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @return List<SysRoleEntity>
     */
    List<SysRoleEntity> queryAll();



    /**
     * Description: 新增
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param sysRoleDto sysRoleDto
     * @return Boolean
     */
    Boolean add(SysRoleDTO sysRoleDto);




    /**
     * Description: 更新
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param sysRole entity object
     * @return boolean
     */
    Boolean replace(SysRoleDTO sysRole);




    /**
     * Description: 根据Id删除
     * @author obeast-dragon
     * Date: 2022-11-30 10:42:42
     * @param id id of entity
     * @return boolean
     */
    Boolean deleteById(Long id);



    /**
     * Description: 判断角色是否存在
     * @author wxl
     * Date: 2022/12/10 19:11
     * @param roleId roleId
     */
    Boolean isRoleExistById(Long roleId) throws BendanException;

    /**
     * Description: 新增角色用户关系
     *
     * @param roleIds roleIds
     * @param userId  userId
     * @return java.lang.Boolean
     * @author wxl
     * Date: 2022/12/11 0:33
     * @throws BendanException 角色不存在抛出异常
     */
    Boolean addRoleUserRels(Long userId, List<Long> roleIds) throws BendanException;

    /**
     * Description: 删除关系 by userId
     * @author wxl
     * Date: 2022/12/11 14:48
     * @param userId    userId
     * @return java.lang.Boolean
     */
    Boolean delRelsByUserId(Long userId);

    /**
     * Description: 更新角色用户关系
     * @author wxl
     * Date: 2022/12/11 14:41
     * @param roleIds  roleIds
     * @param userId id
     * @return java.lang.Boolean
     * @throws BendanException 角色不存在抛出异常
     */
    Boolean updateRolesUserRel(List<Long> roleIds, Long userId) throws BendanException;

    /**
     * Description: 通过名字获取角色
     * @author wxl
     * Date: 2022/12/11 23:48
     * @param roleName  roleName
     * @return com.worldintek.business.entity.SysRole
     */
    SysRoleEntity getByName(String roleName);

    /**
     * Description: 判断角色是否存在 通过名字
     * @author wxl
     * Date: 2022/12/11 23:49
     * @param roleName  roleName
     * @return java.lang.Boolean
     * @throws BendanException 角色不存在抛出异常
     */
    Boolean isRoleExistByName(String roleName) throws BendanException;

}

