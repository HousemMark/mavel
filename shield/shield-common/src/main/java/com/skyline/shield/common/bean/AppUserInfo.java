package com.skyline.shield.common.bean;

import lombok.Data;

@Data
public class AppUserInfo {

    private String token;

    private Long operatorId;

    private Long merchantId;

    private String account;

}
