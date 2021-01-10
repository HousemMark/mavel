package com.skyline.api.filter;

import com.netflix.zuul.ZuulFilter;
import com.skyline.common.constant.FiltersOrderConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * 权限过滤器
 */
@Slf4j
@Component
public class PermissionFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FiltersOrderConstant.PRE_PERMISSION_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        //TODO:根据平台不同，调用merchant的鉴权接口，判断该用户是否有权限调用该接口
        return null;
    }
}
