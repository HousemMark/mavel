package com.skyline.shield.snowflake.enums;

public enum  SnowFlakeEntityEnum {
    COMMON(0, "通用");

    private Integer entityId;

    private String msg;

    SnowFlakeEntityEnum(int entityId, String msg) {
        this.entityId = entityId;
        this.msg = msg;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public String getMsg() {
        return msg;
    }
}
