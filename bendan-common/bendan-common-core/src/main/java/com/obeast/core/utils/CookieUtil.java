package com.obeast.core.utils;

import cn.hutool.core.util.ArrayUtil;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wxl
 * Date 2022/12/13 14:38
 * @version 1.0
 * Description: Cookie工具类
 */
public class CookieUtil {



    /**
     * Description: 获取cookie映射
     * @author wxl
     * Date: 2022/12/13 14:39
     * @param cookies cookies
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    public static Map<String, String> getMapCookie (Cookie[] cookies) {
        if (ArrayUtil.isNotEmpty(cookies)){
            Map<String, String> res = new HashMap<>();
            for (Cookie cookie : cookies) {
                res.put(cookie.getName(), cookie.getValue());
            }
            return res;
        }
        return new HashMap<>();
    }
}
