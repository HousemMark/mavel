package com.skyline.merchant.biz;

import com.skyline.merchant.bo.CreateAccountBo;
import com.skyline.merchant.bo.MerchantUserBo;

public interface MerchantLoginService {

    Boolean createAccount(CreateAccountBo bo);

    void login(CreateAccountBo bo);

    MerchantUserBo searchUserByName(String account);
}
