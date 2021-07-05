package com.skyline.merchant.dal.dao.impl;

import com.skyline.merchant.dal.dao.MerchantLoginDao;
import com.skyline.merchant.dal.entity.MerchantLogin;
import com.skyline.merchant.dal.entity.MerchantLoginExample;
import com.skyline.merchant.dal.mapper.MerchantLoginMapper;
import com.skyline.shield.common.exception.DAOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.skyline.shield.common.emuns.ReturnCode.FAIL_INSERT;

@Slf4j
@Component
public class MerchantLoginDaoImpl implements MerchantLoginDao {

    @Autowired
    private MerchantLoginMapper mapper;

    @Override
    public Boolean addMerchantLogin(MerchantLogin merchantLogin) {
        Integer i = 0;
        try {
            i = mapper.insertSelective(merchantLogin);
        } catch (Exception e) {
            log.error("数据库插入异常", e);
            throw new DAOException(FAIL_INSERT);
        }
        return i == 0 ? false : true;
    }

    @Override
    public List<MerchantLogin> searchByAccount(String account) {
        MerchantLoginExample example = new MerchantLoginExample();
        example.createCriteria().andAccountEqualTo(account);
        return mapper.selectByExample(example);
    }
}