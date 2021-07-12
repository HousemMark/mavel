package com.skyline.merchant.dto.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginReq {

    @NotNull(message = "账号不能为空")
    private String account;

    @NotNull(message = "密码不能为空")
    private String password;

//    private String platformCode;

    private String loginIp;
}
