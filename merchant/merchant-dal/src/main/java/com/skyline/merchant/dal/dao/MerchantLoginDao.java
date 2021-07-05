package com.skyline.merchant.dal.dao;

import com.skyline.merchant.dal.entity.MerchantLogin;

import java.util.List;

public interface MerchantLoginDao {
    Boolean addMerchantLogin(MerchantLogin merchantLogin);

    List<MerchantLogin> searchByAccount(String account);
}
