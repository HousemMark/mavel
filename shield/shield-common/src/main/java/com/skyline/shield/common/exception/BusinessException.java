package com.skyline.shield.common.exception;

import com.skyline.shield.common.bean.IReturnCode;
import com.skyline.shield.common.emuns.ReturnCode;

public class BusinessException extends BaseException {

    private static final long serialVersionUID = -9103217173522466144L;

    public BusinessException() {
    }

    public BusinessException(IReturnCode iReturnCode) {
        super(iReturnCode.getCode(), iReturnCode.getMsg());
    }

    public BusinessException(String message) {
        super(ReturnCode.BUSINESS_ERROR.getCode(), message);
    }

    public BusinessException(IReturnCode iReturnCode, String message) {
        super(iReturnCode.getCode(), message);
    }

    public BusinessException(int code, String message) {
        super(code, message);
    }

    public BusinessException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public BusinessException(int code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(code, message, cause, enableSuppression, writableStackTrace);
    }
}
