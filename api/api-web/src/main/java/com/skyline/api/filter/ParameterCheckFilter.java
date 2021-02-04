package com.skyline.api.filter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import com.skyline.common.bo.RequestParamsBO;
import com.skyline.common.constant.FiltersOrderConstant;
import com.skyline.common.enums.JSONObjectTypeEnum;
import com.skyline.common.utils.ApiAppUtil;
import com.skyline.common.utils.JsonCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * 参数校验过滤器
 */
@Slf4j
@Component
public class ParameterCheckFilter extends ZuulFilter {

    private static String REQUEST_PARAMS_METHOD = "method";
    private static String REQUEST_PARAMS_TIMESTAMP = "timestamp";
    private static String REQUEST_PARAMS_TOKEN = "token";
    private final Set<String> UN_PRINT_METHOD_SET = Sets.newHashSet("upload_base64", "upload_multipart_file");

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FiltersOrderConstant.PRE_PARAMETER_CHECK_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        // 参数转换，适配下游接口
        RequestParamsBO requestParamsBO = new RequestParamsBO();

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        if (request.getMethod().equals(HttpMethod.GET.name())) {
            convertRequestParams(request, requestParamsBO);
            StringBuffer params = new StringBuffer("?");
            Enumeration<String> parameterNames = request.getParameterNames();

            // 解析请求参数
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String paramValue = request.getParameter(paramName);
                params.append(paramName);
                params.append("=");
                params.append(paramValue);
                params.append("&");

                if (StringUtils.equals("data", paramName)) {
                    Map<String, Object> dataMap = JSONObject.toJavaObject(JSONObject.parseObject(paramValue), Map.class);
                    if (dataMap != null) {
                        // 设置Thread-local参数
                        ApiAppUtil.setDataMap(dataMap);
                    }
                }
            }
            log.debug("--->>> ParameterCheckFilter: request {}", params.toString());

            if (params.length() > 0) {
                params.delete(params.length() - 1, params.length());
            }
        } else if (request.getMethod().equals(HttpMethod.POST.name())) {
            // POST请求参数校验是否传输完毕
            if (ctx.isChunkedRequestBody()) {
                return null;
            }
            try {
                HttpServletRequestWrapper httpServletRequestWrapper = (HttpServletRequestWrapper) ctx.getRequest();
                // 判断请求内容类型
                String contentType = httpServletRequestWrapper.getContentType();
                if (contentType != null && contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
                    // 正常表单
                    HttpServletRequest httpServletRequest = httpServletRequestWrapper.getRequest();
                    convertRequestParams(httpServletRequest, requestParamsBO);
                    ApiAppUtil.setRequestParams(requestParamsBO);
                } else {
                    ServletInputStream inputStream = request.getInputStream();
                    if (inputStream == null) {
                        return null;
                    }

                    String body = IOUtils.toString(inputStream, Charset.forName("UTF-8"));
                    requestParamsBO = JSONObject.parseObject(body, RequestParamsBO.class);
                    ApiAppUtil.setRequestParams(requestParamsBO);

                    // 文件请求不打印入参
                    String method = requestParamsBO.getMethod();
                    if (UN_PRINT_METHOD_SET.contains(method)) {
                        log.debug("--->>> ParameterCheckFilter: request 不输出上传文件的入参");
                    } else {
                        log.debug("--->>> ParameterCheckFilter: request {}", JSONObject.toJSONString(requestParamsBO));
                    }

                    // 请求实体处理
                    String data = requestParamsBO.getData();
                    if (StringUtils.isNotBlank(data)) {
                        // 判断是否合法Json对象
                        JSONObjectTypeEnum jsonType = JsonCheckUtil.identifyJsonObjectType(data);
                        if (JSONObjectTypeEnum.JSON_OBJECT.equals(jsonType)) {
                            JSONObject obj = (JSONObject) JSONObject.parse(data);
                            Map<String, Object> dataMap = JSONObject.toJavaObject(obj, Map.class);
                            ApiAppUtil.setDataMap(dataMap);
                        }
                        setRequest(ctx, request, data);
                    } else {
                        setRequest(ctx, request, "{}");
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    private void setRequest(RequestContext ctx, HttpServletRequest request, String data) {
        final byte[] reqBodyBytes = data.getBytes();

        ctx.setRequest(new HttpServletRequestWrapper(request) {
            @Override
            public ServletInputStream getInputStream() throws IOException {
                return new ServletInputStreamWrapper(reqBodyBytes);
            }

            @Override
            public int getContentLength() {
                return reqBodyBytes.length;
            }

            @Override
            public long getContentLengthLong() {
                return reqBodyBytes.length;
            }
        });
    }

    private void convertRequestParams(HttpServletRequest httpServletRequest, RequestParamsBO requestParamsBO) {
        String method = httpServletRequest.getParameter(REQUEST_PARAMS_METHOD);
        String timestamp = httpServletRequest.getParameter(REQUEST_PARAMS_TIMESTAMP);
        String token = httpServletRequest.getParameter(REQUEST_PARAMS_TOKEN);

        requestParamsBO.setMethod(method);
        requestParamsBO.setTimestamp(timestamp);
        requestParamsBO.setToken(token);

        ApiAppUtil.setRequestParams(requestParamsBO);
    }
}
