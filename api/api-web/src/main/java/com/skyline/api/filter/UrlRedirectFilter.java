package com.skyline.api.filter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.skyline.common.constant.FiltersOrderConstant;
import com.skyline.common.utils.ApiAppUtil;
import com.skyline.shield.redis.cache.Cache;
import com.skyline.shield.zk.confcenter.ConfigurationCenter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Optional;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 重定向过滤器
 */

@Component
@Slf4j
public class UrlRedirectFilter extends ZuulFilter {

    @Autowired
    public ConfigurationCenter configurationCenter;

    @Autowired
    private Cache redisService;

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
    public Object run() {return null;}

}
