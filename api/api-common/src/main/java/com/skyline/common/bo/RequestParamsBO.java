package com.skyline.common.bo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestParamsBO {
    /**
     * API接口名称
     * 必填
     * exp: collection.sensitiveword.query
     */
    private String method;

    /**
     * 用户登录授权成功后，颁发给应用的授权信息。
     * 选填
     * exp: 2a72fed8-b12f-48e2-9afc-eb1dad0b5460
     */
    private String token;

    /**
     * 时间戳，时区为GMT+8，格式为yyyy-mm-dd hh:mm:ss，服务端允许客户端请求最大时间误差为10分钟。
     * 必填
     * exp: 2019-05-10 10:23:30
     */
    private String timestamp;

    /**
     * 业务参数,json格式
     * 选填
     * exp: {"name":"Mark"}
     */
    private String data;
}
