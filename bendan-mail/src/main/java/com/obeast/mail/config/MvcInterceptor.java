package com.obeast.mail.config;

import cn.hutool.core.codec.Base64;
import cn.hutool.json.JSONUtil;
import com.obeast.common.base.CommonResult;
import com.obeast.common.constant.RequestHeaderConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义MVC拦截器
 *
 * @author Saint
 */
@Slf4j
public class MvcInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String openFeign = request.getHeader(RequestHeaderConstant.openFeignKey);
            String gateway = request.getHeader(RequestHeaderConstant.gatewayKey);
            if (Base64.decodeStr(openFeign).equals(RequestHeaderConstant.openFeignValue)) {
                log.info("openfeign Request header check success");
                return true;
            }
            else if (Base64.decodeStr(gateway).equals(RequestHeaderConstant.gatewayValue)) {
                log.info("gateway Request header check success");
                return true;
            }
            log.info("Request header check failed");
            return false;
        }catch (Exception e) {
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*");
            response.setHeader(HttpHeaders.CACHE_CONTROL,"no-cache");
            response.setCharacterEncoding("UTF-8");
            String body = JSONUtil.toJsonStr(CommonResult.remoteFailed());
            response.getWriter().println(body);
            response.getWriter().flush();
            return false;
        }
    }

}
