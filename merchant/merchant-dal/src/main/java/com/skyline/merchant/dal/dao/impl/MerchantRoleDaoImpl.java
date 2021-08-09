package com.skyline.merchant.dal.dao.impl;

import com.skyline.merchant.dal.dao.MerchantRoleDao;
import com.skyline.merchant.dal.mapper.ext.MerchantRoleExtMapper;
import com.skyline.shield.common.emuns.ReturnCode;
import com.skyline.shield.common.exception.DAOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MerchantRoleDaoImpl implements MerchantRoleDao {

    @Autowired
    private MerchantRoleExtMapper extMapper;

    @Override
    public List<String> searchRolesByUserId(Long userId) {
        try {
            List<String> roles = extMapper.searchRolesByUserId(userId);
            return roles;
        } catch (Exception e) {
            log.error("数据库查询异常。");
            throw new DAOException(ReturnCode.ERROR_107.getCode(), e);
        }
    }
}
