package com.skyline.merchant.dal.dao;

import java.util.List;

public interface MerchantRoleDao {
    List<String> searchRolesByUserId(Long userId);
}
