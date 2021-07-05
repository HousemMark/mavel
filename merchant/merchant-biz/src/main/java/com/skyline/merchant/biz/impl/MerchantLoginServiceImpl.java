package com.skyline.merchant.biz.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import com.skyline.merchant.biz.MerchantLoginService;
import com.skyline.merchant.bo.CreateAccountBo;
import com.skyline.merchant.bo.MerchantUserBo;
import com.skyline.merchant.common.CommonFields;
import com.skyline.merchant.dal.dao.MerchantLoginDao;
import com.skyline.merchant.dal.entity.MerchantLogin;
import com.skyline.shield.common.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
@Slf4j
public class MerchantLoginServiceImpl implements MerchantLoginService {

    @Autowired
    private MerchantLoginDao merchantLoginDao;

    @Override
    public Boolean createAccount(CreateAccountBo bo) {
        long id = IdUtil.createSnowflake(CommonFields.workerId, CommonFields.dataCenterId).nextId();
        encryptPassword(bo);
        MerchantLogin merchantLogin = BeanUtils.copyBean(bo, MerchantLogin.class);
        merchantLogin.setId(id);
        merchantLogin.setUserId(0L);
        merchantLogin.setCreator(0L);
        merchantLogin.setModifier(0L);
        Boolean result = merchantLoginDao.addMerchantLogin(merchantLogin);
        return result;
    }

    private void encryptPassword(CreateAccountBo bo) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(bo.getPassword());
//        String salt = IdUtil.randomUUID().replaceAll(CommonFields.DASH, CommonFields.EMPTY_STRING);
//        String password = DigestUtils.md5DigestAsHex(bo.getPassword().concat(salt).getBytes());
//        log.debug("----Mark's record: salt = " + salt);
        log.debug("----Mark's record: password = " + password);
        bo.setSalt("");
        bo.setPassword(password);
    }

    @Override
    public void login(CreateAccountBo bo) {
        String pwd = bo.getPassword() + "1d2c14897e6f4ff9ad686e675d8eea43";
        String password = DigestUtils.md5DigestAsHex(pwd.getBytes());
        log.debug("----Mark's record: isEqual = " + password.equals("e6a1155cac692d97000f61c2a4b17239"));
    }

    @Override
    public MerchantUserBo searchUserByName(String account) {
        List<MerchantLogin> accountList = merchantLoginDao.searchByAccount(account);
        MerchantLogin merchantLogin = accountList.get(0);
        MerchantUserBo merchantUserBo = BeanUtils.copyBean(merchantLogin, MerchantUserBo.class);
        merchantUserBo.setUsername(merchantLogin.getAccount());
        merchantUserBo.setDisplayName(merchantLogin.getAccount());
        return merchantUserBo;
    }

}
