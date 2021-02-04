package com.skyline.common.utils;

import com.skyline.common.bo.RequestParamsBO;
import com.skyline.shield.common.utils.ThreadContextUtil;

import java.util.Map;

public class ApiAppUtil {

    private volatile static ApiAppUtil app = null;

    private ApiAppUtil() {
    }

    public static synchronized ApiAppUtil getInstance() {
        if (app == null) {
            synchronized (ApiAppUtil.class) {
                if (app == null) {
                    app = new ApiAppUtil();
                }
            }
        }
        return app;
    }

    public static Boolean getIsForwardTo() {
        return (Boolean) ThreadContextUtil.getInstance().get("isForwardTo");
    }

    public static void setIsForwardTo(Boolean isForwardTo) {
        ThreadContextUtil.getInstance().set("isForwardTo", isForwardTo);
    }

    public static RequestParamsBO getRequestParams() {
        return (RequestParamsBO) ThreadContextUtil.getInstance().get("requestParams");
    }

    public static void setRequestParams(RequestParamsBO requestParams) {
        ThreadContextUtil.getInstance().set("requestParams", requestParams);
    }

    public static Map<String, Object> getDataMap() {
        return (Map<String, Object>) ThreadContextUtil.getInstance().get("dataMap");
    }

    public static void setDataMap(Map<String, Object> dataMap) {
        ThreadContextUtil.getInstance().set("dataMap", dataMap);
    }

    public static void clean() {
        ThreadContextUtil.getInstance().clean();
    }
}
