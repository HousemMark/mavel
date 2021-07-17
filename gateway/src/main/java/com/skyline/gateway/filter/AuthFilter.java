package com.skyline.gateway.filter;

import com.skyline.gateway.common.ApiConstants;
import com.skyline.gateway.common.ApiReturnCode;
import com.skyline.shield.common.bean.AppUserInfo;
import com.skyline.shield.common.bean.IReturnCode;
import com.skyline.shield.common.constants.CacheCommonConstants;
import com.skyline.shield.common.emuns.ReturnCode;
import com.skyline.shield.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 鉴权过滤器
 */
@Slf4j
@Component
public class AuthFilter extends ApiFilter {

    @Value("${token.valid.timeout.seconds:600}")
    private int tokenValidTimeoutSeconds;

    @Autowired
    private RedisService redisService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.debug("进入权限过滤器");
        // 鉴权
        ServerHttpResponse response = exchange.getResponse();
        String token = getToken(exchange);
        // TODO: 调用merchant服务查询角色是否有该权限

        // 刷新key的时间
        AppUserInfo appUserInfo = null;
        try {
            appUserInfo = (AppUserInfo) redisService.get(token);
            redisService.expire(token, tokenValidTimeoutSeconds);
            String userIdCache = CacheCommonConstants.USER_ID_CACHE + appUserInfo.getOperatorId();
            redisService.set(userIdCache, token, tokenValidTimeoutSeconds);
        } catch (Exception e) {
            log.error(ApiReturnCode.EXPIRED_TOKEN_FAILED.getMsg());
            return authError(response, ApiReturnCode.AUTH_USER_FAILED);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return ApiConstants.AUTH_FILTER_ORDERED;
    }
}
