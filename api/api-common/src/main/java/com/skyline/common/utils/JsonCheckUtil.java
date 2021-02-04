package com.skyline.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skyline.common.enums.JSONObjectTypeEnum;

public class JsonCheckUtil {

    public static JSONObjectTypeEnum identifyJsonObjectType(String json){
        if (json.indexOf("[") == 0) {
            JSONArray jsonArray = JSONArray.parseArray(json);
            if(null != jsonArray){
                return JSONObjectTypeEnum.JSON_ARRAY;
            }
        } else {
            JSONObject jsonData = JSONObject.parseObject(json);
            if(null != jsonData){
                return JSONObjectTypeEnum.JSON_OBJECT;
            }
        }

        return JSONObjectTypeEnum.NOT_JSON;
    }
}
