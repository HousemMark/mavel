package com.skyline.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.skyline.gateway.common.ApiConstants;
import com.skyline.gateway.common.ApiReturnCode;
import com.skyline.shield.common.bean.AppUserInfo;
import com.skyline.shield.common.result.SingleResult;
import com.skyline.shield.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * token过滤器
 */
@Slf4j
@Component
public class TokenFilter extends ApiFilter {

    @Autowired
    private RedisService redisService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.debug("进入登录过滤器");
        // 拿到header里面的token，查询token是否合法
        ServerHttpResponse response = exchange.getResponse();
        String token = getToken(exchange);
        AppUserInfo appUserInfo = null;
        try {
            appUserInfo = (AppUserInfo)redisService.get(token);
        } catch (Exception e) {
            log.error(ApiReturnCode.CHECK_TOKEN_FAILED.getMsg(), e);
            return authError(response, ApiReturnCode.CHECK_TOKEN_FAILED);
        }
        if (appUserInfo ==  null) {
            log.error(ApiReturnCode.CHECK_TOKEN_FAILED.getMsg());
            return authError(response, ApiReturnCode.CHECK_TOKEN_FAILED);
        }
        log.debug("userInfo: {}", JSONObject.toJSONString(appUserInfo));
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return ApiConstants.TOKEN_FILTER_ORDERED;
    }

//    private Mono<Void> authError(ServerHttpResponse response, ApiReturnCode apiResultCodeEnum) {
//        response.setStatusCode(HttpStatus.UNAUTHORIZED);
//        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
//        SingleResult<String> resultData = new SingleResult<>(org.apache.http.HttpStatus.SC_UNAUTHORIZED, apiResultCodeEnum.getMsg());
//        String returnStr = "";
//        try {
//            returnStr = JSONObject.toJSONString(resultData);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//        DataBuffer buffer = response.bufferFactory().wrap(returnStr.getBytes(StandardCharsets.UTF_8));
//        return response.writeWith(Flux.just(buffer));
//    }
}
