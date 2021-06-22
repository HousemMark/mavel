package com.skyline.merchant.controller;

import com.skyline.merchant.biz.MerchantLoginService;
import com.skyline.merchant.bo.CreateAccountBo;
import com.skyline.merchant.dto.req.CreateAccountReq;
import com.skyline.shield.common.utils.BeanUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/merchant/login")
public class MerchantLoginController {

    @Autowired
    private MerchantLoginService merchantLoginService;

    @PostMapping("/createAccount")
    public Boolean createAccount(@RequestBody CreateAccountReq req){
        CreateAccountBo bo = BeanUtils.copyBean(req, CreateAccountBo.class);
        Boolean res = merchantLoginService.createAccount(bo);
        return res;
    }

    @PostMapping("/login")
    public String login(@RequestBody CreateAccountReq req){
        CreateAccountBo bo = BeanUtils.copyBean(req, CreateAccountBo.class);
        merchantLoginService.login(bo);
        return null;
    }
}
