package com.skyline.merchant.biz;

import com.skyline.merchant.bo.CreateAccountBo;
import org.springframework.stereotype.Service;

public interface MerchantLoginService {

    Boolean createAccount(CreateAccountBo bo);

    void login(CreateAccountBo bo);
}
