//package com.obeast.gateway.filter;
//
//import cn.hutool.core.codec.Base64;
//import cn.hutool.json.JSONUtil;
//import com.obeast.common.base.CommonResult;
//import com.obeast.common.base.ResultCode;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.util.StringUtils;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.stream.Collectors;
//
///**
// * @author wxl
// * Date 2022/11/1 16:17
// * @version 1.0
// * Description: 全局过滤器
// */
//public class BendanRequestGlobalFilter implements GlobalFilter, Ordered {
//    private static final Logger log = LoggerFactory.getLogger(BendanRequestGlobalFilter.class);
//
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        System.out.println("BendanRequestGlobalFilter");
//
//        // TODO: 2022/11/2 加密配置
//        String gateway = Base64.encode("gateway");
////Z2F0ZXdheQ==
//
//        // 跨域放行
//        ServerHttpRequest request = exchange.getRequest();
//        ServerHttpResponse response = exchange.getResponse();
//        String url = request.getURI().getPath();
//
//        String newPath = "/" + Arrays.stream(StringUtils.tokenizeToStringArray(url, "/")).skip(1L)
//                .collect(Collectors.joining("/"));
//
//        log.info("接收到请求：{}", url);
//        log.info("重写StripPrefix：{}", newPath);
//        if (request.getMethod() == HttpMethod.OPTIONS) {
//            response.setStatusCode(HttpStatus.OK);
//            return Mono.empty();
//        }
//        ServerHttpRequest exchangeRequest = request
//                .mutate()
//                .headers(httpHeaders -> {
//                    httpHeaders.put("Id", Collections.singletonList(gateway));
//                }).build();
//        return chain.filter(
//                exchange
//                        .mutate()
//                        .request(exchangeRequest.mutate().build()).build()
//        );
//
////        // 授权
////        if (!this.auth(exchange, chain)) {
////            return this.responseBody(exchange,  "请先登录");
////        }
////        return chain.filter(exchange);
//    }
//
//    /**
//     * 认证
//     */
//    private boolean auth(ServerWebExchange exchange, GatewayFilterChain chain) {
//        // 逻辑自行实现
//        String token = this.getToken(exchange.getRequest());
//        log.info("token:{}", token);
//        return true;
//    }
//
//    /**
//     * 获取token
//     */
//    public String getToken(ServerHttpRequest request) {
//        String token = request.getHeaders().getFirst("token");
//
//        return token;
//    }
//
//    /**
//     * 设置响应体
//     **/
//    public Mono<Void> responseBody(ServerWebExchange exchange, String msg) {
//        String str = JSONUtil.toJsonStr(CommonResult.error(ResultCode.UN_AUTHORIZED, msg));
//        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
//        return this.responseHeader(exchange).getResponse()
//                .writeWith(Flux.just(exchange.getResponse().bufferFactory().wrap(bytes)));
//    }
//
//    /**
//     * 设置响应体的请求头
//     */
//    public ServerWebExchange responseHeader(ServerWebExchange exchange) {
//        ServerHttpResponse response = exchange.getResponse();
//        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
//        return exchange.mutate().response(response).build();
//    }
//
//    @Override
//    public int getOrder() {
//        return 10;
//    }
//}
