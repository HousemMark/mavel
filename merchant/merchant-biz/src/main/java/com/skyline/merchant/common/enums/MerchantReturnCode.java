package com.skyline.merchant.common.enums;

import com.skyline.shield.common.bean.IReturnCode;

public enum MerchantReturnCode implements IReturnCode {
    ERROR_4001(4001, "雪花id生成失败");

    private int code;

    private String msg;

    MerchantReturnCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
