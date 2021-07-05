package com.skyline.merchant.controller;

import com.skyline.merchant.biz.MerchantLoginService;
import com.skyline.merchant.bo.CreateAccountBo;
import com.skyline.merchant.bo.MerchantUserBo;
import com.skyline.merchant.dto.req.CreateAccountReq;
import com.skyline.merchant.dto.resp.MerchantUserDto;
import com.skyline.shield.common.utils.BeanUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/searchUserByName")
    public MerchantUserDto searchUserByName(@RequestParam("account") String account){
        MerchantUserBo bo = merchantLoginService.searchUserByName(account);
        return BeanUtils.copyBean(bo, MerchantUserDto.class);
    }
}
