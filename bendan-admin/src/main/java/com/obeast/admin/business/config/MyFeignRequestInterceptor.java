package com.obeast.admin.business.config;

import cn.hutool.core.codec.Base64;
import com.obeast.common.constant.RequestHeaderConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @author wxl
 * Date 2022/11/3 14:53
 * @version 1.0
 * Description: 自定义的Feign拦截器
 */
@Slf4j
public class MyFeignRequestInterceptor implements RequestInterceptor {


    /**
     * Description: 添加头部
     * @author wxl
     * Date: 2022/11/3 14:53
     * @param requestTemplate request template
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 1. obtain request
        final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        // 2. 兼容hystrix限流后，获取不到ServletRequestAttributes的问题（使拦截器直接失效）
        if (Objects.isNull(attributes)) {
            log.error("MyFeignRequestInterceptor is invalid!");
            return;
        }
        requestTemplate.header(RequestHeaderConstant.openFeignKey, Base64.encode(RequestHeaderConstant.openFeignValue));
        log.info("Initializing request header");
    }
}
