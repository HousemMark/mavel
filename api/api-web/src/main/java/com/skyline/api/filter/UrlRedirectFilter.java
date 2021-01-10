package com.skyline.api.filter;

import com.netflix.zuul.ZuulFilter;
import com.skyline.common.constant.FiltersOrderConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 重定向过滤器
 */

@Component
@Slf4j
public class UrlRedirectFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FiltersOrderConstant.ROUTE_URL_REDIRECT_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        return null;
    }
}
