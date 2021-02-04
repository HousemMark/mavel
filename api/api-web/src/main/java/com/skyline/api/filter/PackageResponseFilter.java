package com.skyline.api.filter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.skyline.common.constant.FiltersOrderConstant;
import com.skyline.common.utils.ApiAppUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

/**
 * 响应拦截器
 */
@Component
@Slf4j
public class PackageResponseFilter extends ZuulFilter {

    private final String UN_PRINT_METHOD = "collection_download_base64";

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FiltersOrderConstant.POST_PACKAGE_RESPONSE_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().sendZuulResponse();
    }

    @Override
    public Object run() {
        // 获取返回流对象
        RequestContext ctx = RequestContext.getCurrentContext();
        try {
            InputStream responseDataStream = ctx.getResponseDataStream();
            if (responseDataStream != null) {
                String responseJson = IOUtils.toString(responseDataStream, Charset.forName("UTF-8"));
                JSONObject jsonObject = JSONObject.parseObject(responseJson);
                JSONObject resultJsonObject = new JSONObject();

                Map<String, String> zuulRequestHeaders = ctx.getZuulRequestHeaders();
                String method = zuulRequestHeaders.get("method");

                String data = "";
                try {
                    data = JSONObject.toJSONString(jsonObject.getJSONObject("data"), SerializerFeature.WriteMapNullValue);
                } catch (ClassCastException e) {
                    try {
                        data = JSONObject.toJSONString(jsonObject.getJSONArray("data"), SerializerFeature.WriteMapNullValue);
                    } catch (ClassCastException e1) {
                        data = transformData(jsonObject);
                    }
                } catch (JSONException e) {
                    data = transformData(jsonObject);
                }

                String str = "";

                String responseName = method + "_response";
                resultJsonObject.put(responseName, str);

                if (data != null && !StringUtils.equals(data, "null")) {
                    resultJsonObject = putToResultJsonObject(data, resultJsonObject, responseName);
                } else {
                    try {
                        data = JSONObject.toJSONString(jsonObject.getJSONArray("dataList"), SerializerFeature.WriteMapNullValue);
                    } catch (ClassCastException e) {
                        data = JSONObject.toJSONString(jsonObject.getJSONObject("dataList"), SerializerFeature.WriteMapNullValue);
                    }
                    if (data != null && !StringUtils.equals(data, "null")) {
                        resultJsonObject = putToResultJsonObject(data, resultJsonObject, responseName);
                    }
                }

                str = JSONObject.toJSONString(resultJsonObject, SerializerFeature.WriteMapNullValue);
                ctx.setResponseBody(str);

                if (StringUtils.equals(method, UN_PRINT_METHOD)) {
                    log.debug("--->>> PackageResponseFilter: response 不输出下载文件的出参");
                } else {
                    log.debug("--->>> PackageResponseFilter: response {}", str);
                }

            }
        } catch (IOException e) {
            log.error("--->>> PackageResponseFilter ERROR: {}", e);
        } finally {
            ApiAppUtil.clean();
        }
        return null;
    }

    private JSONObject putToResultJsonObject(String data, JSONObject resultJsonObject, String responseName) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonData = new JSONObject();

        try {
            if (data.indexOf("[") == 0) {
                jsonArray = JSONArray.parseArray(data);
                resultJsonObject.put(responseName, jsonArray);
            } else {
                jsonData = JSONObject.parseObject(data);
                resultJsonObject.put(responseName, jsonData);
            }
        } catch (Exception e) {
            log.info("{0}转换jsonobject时出错，原data:{1}", responseName, data);
        }

        return resultJsonObject;
    }

    private String transformData(JSONObject param) {
        JSONObject newJO = new JSONObject();
        newJO.put("data", param.get("data"));
        return newJO.toJSONString();
    }
}
