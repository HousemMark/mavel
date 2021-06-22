package com.skyline.shield.common.exception;

import com.skyline.shield.common.bean.IReturnCode;

public class ServiceException extends BaseException {

    private int code;

    public ServiceException(IReturnCode returnCode) {

    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    public ServiceException(int code) {
        this.code = code;
    }

    public ServiceException(int code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(int code, Throwable throwable, String message) {
        super(message, throwable);
        this.code = code;
    }

}
