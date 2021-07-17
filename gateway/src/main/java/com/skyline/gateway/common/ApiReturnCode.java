package com.skyline.gateway.common;

import com.skyline.shield.common.bean.IReturnCode;

public enum ApiReturnCode implements IReturnCode {
    NO_USER_ERROR(3001, "账户不存在"),
    CHECK_TOKEN_FAILED(3002,"您的认证权限已过期，请重新进行认证"),
    AUTH_USER_FAILED(3003,"您没有该权限，请联系管理员。"),
    EXPIRED_TOKEN_FAILED(3004,"更新token时间失败。");

    private int code;

    private String msg;

    ApiReturnCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMsg() {
        return null;
    }
}
