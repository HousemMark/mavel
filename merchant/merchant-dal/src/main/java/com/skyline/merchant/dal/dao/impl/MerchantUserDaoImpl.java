package com.skyline.merchant.dal.dao.impl;

import com.skyline.merchant.dal.dao.MerchantUserDao;
import com.skyline.merchant.dal.entity.MerchantUser;
import com.skyline.merchant.dal.mapper.MerchantUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MerchantUserDaoImpl implements MerchantUserDao {

    @Autowired
    private MerchantUserMapper mapper;

    @Override
    public MerchantUser getUserById(Long userId) {
        return mapper.selectByPrimaryKey(userId);
    }
}
