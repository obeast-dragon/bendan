package com.obeast.business.vo;

import com.obeast.business.entity.SysRoleEntity;
import com.obeast.business.entity.SysUserEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author wxl
 * Date 2022/11/30 14:15
 * @version 1.0
 * Description: 用户详情
 */
@Data
@Accessors(chain = true)
public class UserInfo  implements Serializable {

    /**
     * 系统用户
     * */
    private SysUserEntity sysUserEntity;

    /**
     * 用户角色
     * */
    private List<SysRoleEntity> sysRoleEntities;

    /**
     * 角色ids
     */
    private  List<Long> roleIds;

    /**
     * 用户菜单集合（权限）
     * */
    private String[] purviewNames;

}
