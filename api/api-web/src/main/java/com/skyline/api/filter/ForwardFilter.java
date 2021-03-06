package com.skyline.api.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.skyline.common.constant.FiltersOrderConstant;
import com.skyline.common.utils.ApiAppUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * 转发过滤器
 */
@Slf4j
@Component
public class ForwardFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FiltersOrderConstant.PRE_FORWARD_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();

        //直接调用zuul内部controller
        if (null != ctx.get("forward.to")) {
            ApiAppUtil.setIsForwardTo(true);
        } else {
            ApiAppUtil.setIsForwardTo(false);
        }
        return null;
    }
}
