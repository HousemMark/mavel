package com.skyline.gateway.core.impl;

import com.skyline.gateway.common.ApiReturnCode;
import com.skyline.gateway.core.MerchantCoreService;
import com.skyline.gateway.dto.req.LoginReq;
import com.skyline.gateway.dto.resp.LoginResp;
import com.skyline.gateway.dto.resp.LoginResponse;
import com.skyline.gateway.feign.MerchantUserFeignClient;
import com.skyline.shield.common.bean.AppUserInfo;
import com.skyline.shield.common.constants.BasicsConstant;
import com.skyline.shield.common.constants.CacheCommonConstants;
import com.skyline.shield.common.constants.ProductionCodeConstants;
import com.skyline.shield.common.emuns.ReturnCode;
import com.skyline.shield.common.exception.ServiceException;
import com.skyline.shield.common.utils.BeanUtils;
import com.skyline.shield.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Slf4j
@Service
public class MerchantCoreServiceImpl implements MerchantCoreService {

    @Value("${token.valid.timeout.seconds:600}")
    private int tokenValidTimeoutSeconds;

    @Autowired
    private MerchantUserFeignClient merchantUserFeignClient;

    @Autowired
    private RedisService redisService;

    @Override
    public LoginResponse login(LoginReq req, HttpServletRequest request) {
        LoginResp resp = null;
        try {
            resp = merchantUserFeignClient.login(req);
        } catch (Exception e) {
            log.error("请求merchant服务器失败...");
            throw new ServiceException(ReturnCode.ERROR_01);
        }
        if (resp == null) {
            log.error(ApiReturnCode.NO_USER_ERROR.getMsg());
            throw new ServiceException(ApiReturnCode.NO_USER_ERROR);
        }
        // 生成token
        String token = CacheCommonConstants.USER_TOKEN_CACHE + ProductionCodeConstants.MALL_CODE + BasicsConstant.STRING_SPLIT_COLON + UUID.randomUUID().toString();
        AppUserInfo appUserInfo = BeanUtils.copyBean(resp, AppUserInfo.class);
        appUserInfo.setToken(token);
        appUserInfo.setOperatorId(resp.getUserId());
        redisService.set(token, appUserInfo, tokenValidTimeoutSeconds);

        String userIdCache = CacheCommonConstants.USER_ID_CACHE + resp.getUserId();
        redisService.set(userIdCache, token, tokenValidTimeoutSeconds);
        // 返回结果
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccount(resp.getAccount());
        loginResponse.setToken(token);
        loginResponse.setStatus(resp.getStatus());
        loginResponse.setRole(resp.getRole());
        return loginResponse;
    }
}
