package com.obeast.core.constant;

/**
 * @author wxl
 * Date 2022/12/8 16:53
 * @version 1.0
 * Description: 角色常量
 */
public interface RoleConstant {



    /**
     * 更新用户角色关系失败
     */
    String UPDATE_RELS_USER_FAIL = "更新用户角色关系失败";

    /**
     * 删除用户角色关系失败
     */
    String DEL_RELS_USER_FAIL = "删除用户角色关系失败";

    /**
     * 角色不存在
     * */
    String NOT_FOUND = "角色不存在";


    /**
     * 角色不存在
     * */
    String ROLE_EXIST = "角色已经存在";

    /**
     * 当前用户没有设置角色
     * */
    String USER_HAS_NOT_SET_ROLES = "当前用户没有设置角色";

    enum Role{
        ADMIN(1L, "管理员", "ROLE_ADMIN"),
        GENERAL_USER(2L, "普通用户", "GENERAL_USER")
        ;
        private final Long id;
        private final String name;
        private final String code;

        Role(Long id, String name, String code) {
            this.id = id;
            this.name = name;
            this.code = code;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }
    }

}
