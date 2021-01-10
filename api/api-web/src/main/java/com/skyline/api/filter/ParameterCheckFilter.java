package com.skyline.api.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.skyline.common.bo.RequestParamsBO;
import com.skyline.common.constant.FiltersOrderConstant;
import com.skyline.shield.common.utils.ThreadContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 参数校验过滤器
 */
@Slf4j
@Component
public class ParameterCheckFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FiltersOrderConstant.PRE_PARAMETER_CHECK_FILTER_ORDER;
    }

    /**
     * 过滤器是否生效
     * 返回true代表需要权限校验，false代表不需要用户校验即可访问
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 业务逻辑
     * 只有上面返回true的时候，才会进入到该方法
     */
    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        // 请求参数转格式
        RequestParamsBO requestParamsBO = new RequestParamsBO();

        return null;
    }
}
