package com.skyline.gateway.feign;

import com.skyline.gateway.dto.req.LoginReq;
import com.skyline.gateway.dto.resp.LoginResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "MERCHANT-SERVER")
public interface MerchantUserFeignClient {

    @PostMapping("/merchant/login")
    LoginResp login(@RequestBody LoginReq req);
}
