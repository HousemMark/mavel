package com.skyline.shield.common.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class AppUserInfo implements Serializable {

    private String token;

    private Long operatorId;

    private Long merchantId;

    private String account;

}
