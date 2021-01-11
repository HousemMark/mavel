package com.skyline.shield.redis.exception;

/**
 * 操作Redis异常
 */
public class RedisOperateException extends RuntimeException {

    private static final long serialVersionUID = 8963159005957128635L;

    private String code;

    private String message;

    public RedisOperateException() {
        super();
    }

    public RedisOperateException(Throwable cause) {
        super(cause);
    }

    public RedisOperateException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisOperateException(String message) {
        super(message);
        this.message = message;
    }

    public RedisOperateException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public RedisOperateException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public RedisOperateException(String code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
