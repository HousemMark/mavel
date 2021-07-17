package com.skyline.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.skyline.gateway.common.ApiConstants;
import com.skyline.gateway.common.ApiReturnCode;
import com.skyline.shield.common.result.SingleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public abstract class ApiFilter implements GlobalFilter, Ordered {

    Logger logger = LoggerFactory.getLogger(ApiFilter.class);

    protected String getToken(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String token = headers.get(ApiConstants.HEADERS_TOKEN).get(0);
        return token;
    }

    protected Mono<Void> authError(ServerHttpResponse response, ApiReturnCode apiResultCodeEnum) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        SingleResult<String> resultData = new SingleResult<>(org.apache.http.HttpStatus.SC_UNAUTHORIZED, apiResultCodeEnum.getMsg());
        String returnStr = "";
        try {
            returnStr = JSONObject.toJSONString(resultData);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        DataBuffer buffer = response.bufferFactory().wrap(returnStr.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(buffer));
    }

}
