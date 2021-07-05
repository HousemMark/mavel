package com.skyline.gateway.controller;

import com.skyline.gateway.core.MerchantCoreService;
import com.skyline.gateway.dto.req.LoginReq;
import com.skyline.gateway.dto.resp.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RequestMapping("/login")
@RestController
public class LoginController {

    @Autowired
    private MerchantCoreService merchantCoreService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginReq req, HttpServletRequest http) {
        return merchantCoreService.login(req, http);
    }

}
