package com.skyline.shield.common.result;

import com.skyline.shield.common.emuns.ReturnCode;

public class SingleResult<T> extends CommonResult {
    private static final long serialVersionUID = 3847004683544003631L;
    /**
     * 结果对象
     */
    private T data;

    public SingleResult() {
    }

    public SingleResult(ReturnCode ReturnCode) {
        super(ReturnCode);
    }

    public SingleResult(int code, String msg) {
        super( code, msg);
    }

    public SingleResult(int code, String msg, T data) {
        super(code, msg);
        setData(data);
    }

    public SingleResult(T data) {
        setData(data);
    }

    public SingleResult(ReturnCode ReturnCode, T data) {
        this(ReturnCode);
        setData(data);
    }

    public static <T> SingleResult<T> of(ReturnCode ReturnCode) {
        return new SingleResult<>(ReturnCode);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
