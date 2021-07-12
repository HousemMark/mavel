package com.skyline.merchant.bo;

import lombok.Data;

@Data
public class LoginBo {

    private String account;

    private String password;

//    private String platformCode;

    private String loginIp;
}
