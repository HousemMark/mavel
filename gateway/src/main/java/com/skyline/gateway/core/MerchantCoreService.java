package com.skyline.gateway.core;

import com.skyline.gateway.dto.req.LoginReq;
import com.skyline.gateway.dto.resp.LoginResponse;

import javax.servlet.http.HttpServletRequest;

public interface MerchantCoreService {

    LoginResponse login(LoginReq req, HttpServletRequest request);

}
