package com.obeast.admin.exception;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.obeast.core.base.CommonResult;
import com.obeast.core.constant.WebResultEnum;
import com.obeast.core.exception.BendanException;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author wxl
 * Date 2022/12/8 15:36
 * @version 1.0
 * Description: 全局接口返回值处理
 */
@RestControllerAdvice(basePackages = {"com.obeast.admin.business.controller"})
public class ControllerResponseAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter methodParameter, @NonNull Class<? extends HttpMessageConverter<?>> aClass) {
        return !(methodParameter.getParameterType().isAssignableFrom(CommonResult.class)
                || methodParameter.hasMethodAnnotation(IgnoreResponseAdvice.class));
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object data,
                                  @NonNull MethodParameter methodParameter,
                                  @NonNull MediaType mediaType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> aClass,
                                  @NonNull ServerHttpRequest serverHttpRequest,
                                  @NonNull ServerHttpResponse serverHttpResponse) {
        // String类型不能直接包装
        if (methodParameter.getGenericParameterType().equals(String.class)) {
            try {
                ServletServerHttpResponse httpResponse = (ServletServerHttpResponse) serverHttpResponse;
                HttpServletResponse response = httpResponse.getServletResponse();
                response.setStatus(HttpStatus.OK.value());
                response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Cache-Control", "no-cache");
                String body = JSONUtil.toJsonStr(CommonResult.success(data));
                response.getWriter().write(body);
                return null;
            } catch (JsonProcessingException e) {
                throw new BendanException(WebResultEnum.FAILURE.getMessage());
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            }
        }
        return CommonResult.success(data);
    }
}