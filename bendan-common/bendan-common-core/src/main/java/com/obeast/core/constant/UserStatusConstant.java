package com.obeast.core.constant;

import com.obeast.core.exception.BendanException;

import java.util.Arrays;
import java.util.List;

/**
 * @author wxl
 * Date 2022/10/19 14:51
 * @version 1.0
 * Description: 用户状态
 */
public enum UserStatusConstant {

    /*删除*/
    DEL(true, 0),
    /*锁定*/
    LOCK(true, 2),
    /*使用*/
    DISABLE(true, 4),
    /*过期*/
    EXPIRED(true,6),
    /*正常*/
    NORMAL(false, 1,3,5,7)
    ;

    private final List<Integer> code;
    private final boolean enabled;

    UserStatusConstant(boolean enabled, Integer ...code) {
        this.code = Arrays.asList(code);
        this.enabled = enabled;
    }


    public List<Integer> getCode() {
        return code;
    }

    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Description: 状态映射
     * @author wxl
     * Date: 2022/10/19 15:41
     * @param code 状态码
     * @return boolean
     */

    public static boolean map(int code){
        UserStatusConstant[] values = UserStatusConstant.values();
        for (UserStatusConstant status : values) {
            if (status.code.contains(code)){
                return status.enabled;
            }
        }
        throw new BendanException("用户状态码不存在");
    }


}
