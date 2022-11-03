package com.obeast.gateway.filter;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.time.LocalDateTime;

/**
 * @author wxl
 * Date 2022/11/2 15:43
 * @version 1.0
 * Description:
 */
@Slf4j
public class ValidateGatewayFilter extends AbstractGatewayFilterFactory<Object> {
    @Override
    public GatewayFilter apply(Object config) {
        System.out.println("ValidateGatewayFilter");
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            boolean isAuthToken = CharSequenceUtil.containsAnyIgnoreCase(request.getURI().getPath(),
                    "/oauth2/token");
            return chain.filter(exchange);
        };
    }
}
