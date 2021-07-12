package com.skyline.merchant.dal.dao;

import com.skyline.merchant.dal.entity.MerchantUser;

public interface MerchantUserDao {

    MerchantUser getUserById(Long userId);
}
