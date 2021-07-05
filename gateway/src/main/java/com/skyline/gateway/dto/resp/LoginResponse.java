package com.skyline.gateway.dto.resp;

import lombok.Data;

@Data
public class LoginResponse {

    private String account;

    private String status;

    private String role;

    private String token;
}
