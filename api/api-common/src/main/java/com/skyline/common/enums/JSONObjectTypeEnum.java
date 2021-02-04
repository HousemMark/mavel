package com.skyline.common.enums;

public enum JSONObjectTypeEnum {
    NOT_JSON("notJSON"),
    JSON_OBJECT("JSONObject"),
    JSON_ARRAY("JSONArray");

    private String code;

    JSONObjectTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
