package com.skyline.gateway.common;

import com.skyline.shield.common.bean.IReturnCode;

public enum ApiReturnCode implements IReturnCode {
    NO_USER_ERROR(3001, "账户不存在");

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
