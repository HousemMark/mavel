package com.skyline.merchant.biz;

import com.skyline.merchant.bo.LoginBo;
import com.skyline.merchant.bo.MerchantUserBo;

public interface MerchantLoginService {

    MerchantUserBo login(LoginBo bo);

    Boolean create(String account, String password);
}
