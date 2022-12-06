package com.obeast.business.vo;

import com.obeast.business.entity.BendanSysMenu;
import com.obeast.business.entity.BendanSysRole;
import com.obeast.business.entity.BendanSysUser;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
    private BendanSysUser bendanSysUser;

    /**
     * 用户角色
     * */
    private List<BendanSysRole> bendanSysRoles;

    /**
     * 角色ids
     */
    private  List<Long> roleIds;

    /**
     * 用户菜单（权限）
     * */
    private Set<BendanSysMenu> bendanSysMenus;

}
