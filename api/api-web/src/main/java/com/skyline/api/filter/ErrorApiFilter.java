package com.skyline.api.filter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.netflix.client.ClientException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.skyline.common.constant.FiltersOrderConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.conn.HttpHostConnectException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;

@Component
@Slf4j
public class ErrorApiFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return FiltersOrderConstant.POST_ERROR_RESPONSE_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return null != ctx.getThrowable();
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ZuulException exception = findZuulException(ctx.getThrowable());

        Throwable cause = exception.getCause();

        if(null != cause) {

            if (cause instanceof SocketTimeoutException){
                log.error("--->>> ErrorApiFilter: 接口调用超时。method:{},apiReadTimeout:{}ms", ctx.getZuulRequestHeaders().get("method"), ctx.getZuulRequestHeaders().get("apiReadTimeOut"), cause);
                wrapperErrorResponse("500", "接口调用超时，请重试。", ctx);

                return null;
            }

            if (cause instanceof HttpHostConnectException) {
                log.error("--->>> ErrorApiFilter: 接口调用连接超时。method:{}", ctx.getZuulRequestHeaders().get("method"), cause);
                wrapperErrorResponse("500", "接口调用连接超时，请重试。", ctx);

                return null;
            }

            if (cause instanceof ClientException) {
                log.error("--->>> ErrorApiFilter: 跳转错误。method:{}", ctx.getZuulRequestHeaders().get("method"), cause);
                wrapperErrorResponse("500", "请求跳转错误。", ctx);

                return null;
            }

            log.error("{}接口请求错误", ctx.getZuulRequestHeaders().get("method"), cause);
            wrapperErrorResponse("500", "网关其他异常，请反馈优化。", ctx);
            return null;
        }

        return null;
    }

    ZuulException findZuulException(Throwable throwable) {
        if (throwable.getCause() instanceof ZuulRuntimeException) {
            // this was a failure initiated by one of the local filters
            return (ZuulException) throwable.getCause().getCause();
        }

        if (throwable.getCause() instanceof ZuulException) {
            // wrapped zuul exception
            return (ZuulException) throwable.getCause();
        }

        if (throwable instanceof ZuulException) {
            // exception thrown by zuul lifecycle
            return (ZuulException) throwable;
        }

        // fallback, should never get here
        return new ZuulException(throwable, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
    }

    private void wrapperErrorResponse(String code,String msg, RequestContext ctx) {
        JSONObject resultJsonObject = new JSONObject();
        resultJsonObject.put("code", code);
        resultJsonObject.put("msg", msg);
        String responseJson = JSONObject.toJSONString(resultJsonObject, SerializerFeature.WriteMapNullValue);

        ctx.setResponseBody(responseJson);
        ctx.remove("throwable");
        ctx.getResponse().setContentType("text/html;charset=UTF-8");
        log.info("--->>> ErrorApiFilter: response {}", responseJson);
        ctx.setResponseDataStream(IOUtils.toInputStream(responseJson, Charset.forName("UTF-8")));
    }
}