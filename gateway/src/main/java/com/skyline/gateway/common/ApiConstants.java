package com.skyline.gateway.common;

import com.skyline.shield.common.constants.BasicsConstant;

public class ApiConstants extends BasicsConstant {

    public static String HEADERS_TOKEN = "token";

    // ordered值越小，优先级越高
    public static Integer TOKEN_FILTER_ORDERED = -10;

    public static Integer AUTH_FILTER_ORDERED = -9;

}
