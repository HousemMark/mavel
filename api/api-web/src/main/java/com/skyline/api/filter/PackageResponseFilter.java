package com.skyline.api.filter;

import com.netflix.zuul.ZuulFilter;
import com.skyline.common.constant.FiltersOrderConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

/**
 * 包装相应拦截器
 */
@Component
@Slf4j
public class PackageResponseFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FiltersOrderConstant.POST_PACKAGE_RESPONSE_FILTER_ORDER;
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
