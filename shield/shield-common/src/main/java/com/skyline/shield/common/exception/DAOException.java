package com.skyline.shield.common.exception;


import com.skyline.shield.common.bean.IReturnCode;
import com.skyline.shield.common.emuns.ReturnCode;

public class DAOException extends BaseException {

    private int code;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    public DAOException(IReturnCode iReturnCode){
        this.code = iReturnCode.getCode();
        this.setMessage(iReturnCode.getMsg());
    }

    public DAOException(String message) {
        super(ReturnCode.BUSINESS_ERROR.getCode(), message);
    }


    public DAOException(int code, Throwable throwable) {
        super(throwable);
        this.code = code;
        this.setMessage(throwable.getMessage());
    }

    public DAOException(int code, String message) {
        super(message);
        this.code = code;
        this.setMessage(message);
    }

    public DAOException(int code, Throwable throwable, String message) {
        super(message, throwable);
        this.code = code;
        this.setMessage(message);
    }
}
