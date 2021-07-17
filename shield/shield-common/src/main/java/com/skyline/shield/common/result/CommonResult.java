package com.skyline.shield.common.result;


import com.skyline.shield.common.emuns.ReturnCode;

public class CommonResult extends ToString {
    private static final long serialVersionUID = -5922291019518100856L;
    /**
     * 返回code
     */
    public Integer code;

    /**
     * 结果描述
     */
    public String msg;

    /**
     * 相应时间
     */
    public String sysTime;

    public String getSysTime() {
        return sysTime;
    }

    public void setSysTime(String sysTime) {
        this.sysTime = sysTime;
    }

    public CommonResult() {
    }

    public CommonResult(ReturnCode returnCode) {
        setCode(returnCode.getCode());
        setMsg(returnCode.getMsg());
        this.sysTime = String.valueOf(System.currentTimeMillis());
    }


    public CommonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.sysTime = String.valueOf(System.currentTimeMillis());
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
