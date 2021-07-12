package com.skyline.merchant.controller;

import com.skyline.merchant.biz.MerchantLoginService;
import com.skyline.merchant.bo.CreateAccountBo;
import com.skyline.merchant.bo.LoginBo;
import com.skyline.merchant.bo.MerchantUserBo;
import com.skyline.merchant.dto.req.LoginReq;
import com.skyline.merchant.dto.resp.LoginResp;
import com.skyline.merchant.dto.resp.MerchantUserDto;
import com.skyline.shield.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/merchant")
public class MerchantLoginController {

    @Autowired
    private MerchantLoginService merchantLoginService;

    @PostMapping("/login")
    public LoginResp login(@RequestBody LoginReq req) {
        LoginBo bo = BeanUtils.copyBean(req, LoginBo.class);
        MerchantUserBo res = merchantLoginService.login(bo);
        return BeanUtils.copyBean(res, LoginResp.class);
    }

    @PostMapping("/create")
    public Boolean create(@RequestParam("account") String account, @RequestParam("password") String password) {
        return merchantLoginService.create(account, password);
    }
}
