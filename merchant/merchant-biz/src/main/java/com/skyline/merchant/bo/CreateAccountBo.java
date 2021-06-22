package com.skyline.merchant.bo;

import lombok.Data;

@Data
public class CreateAccountBo {

    private Long id;

    private String account;

    private String password;

    private String salt;

}
