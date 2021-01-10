package com.skyline.common.constant;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

/**
 * 过滤器顺序
 */
public class FiltersOrderConstant {

    // -----------------------------PRE---------------------------------
    /**
     * FORWARD_FILTER_ORDER = 7
     */
    public static int PRE_FORWARD_FILTER_ORDER = FilterConstants.RIBBON_ROUTING_FILTER_ORDER - 3;

    /**
     * PARAMETER_CHECK_FILTER_ORDER = 8
     */
    public static int PRE_PARAMETER_CHECK_FILTER_ORDER = FilterConstants.RIBBON_ROUTING_FILTER_ORDER - 2;

    /**
     * PERMISSION_FILTER_ORDER = 9
     */
    public static int PRE_PERMISSION_FILTER_ORDER = FilterConstants.RIBBON_ROUTING_FILTER_ORDER - 1;

    // -----------------------------ROUTE---------------------------------
    /**
     * URL_REDIRECT_FILTER_ORDER = 2
     */
    public static int ROUTE_URL_REDIRECT_FILTER_ORDER = FilterConstants.DEBUG_FILTER_ORDER + 1;



    // -----------------------------POST---------------------------------
    /**
     * ERROR_RESPONSE_FILTER_ORDER = 998
     */
    public static int POST_ERROR_RESPONSE_FILTER_ORDER = FilterConstants.SEND_RESPONSE_FILTER_ORDER - 2;

    /**
     * PACKAGE_RESPONSE_FILTER_ORDER = 999
     */
    public static int POST_PACKAGE_RESPONSE_FILTER_ORDER = FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
}
