package com.skyline.merchant.biz.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.skyline.merchant.biz.MerchantLoginService;
import com.skyline.merchant.bo.CreateAccountBo;
import com.skyline.merchant.bo.LoginBo;
import com.skyline.merchant.bo.MerchantUserBo;
import com.skyline.merchant.dal.dao.MerchantLoginDao;
import com.skyline.merchant.dal.dao.MerchantRoleDao;
import com.skyline.merchant.dal.dao.MerchantUserDao;
import com.skyline.merchant.dal.entity.MerchantLogin;
import com.skyline.merchant.dal.entity.MerchantRole;
import com.skyline.merchant.dal.entity.MerchantUser;
import com.skyline.shield.common.exception.BusinessException;
import com.skyline.shield.common.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.skyline.merchant.common.enums.MerchantReturnCode.ERROR_4002;

@Service
@Slf4j
public class MerchantLoginServiceImpl implements MerchantLoginService {

    @Autowired
    private MerchantLoginDao merchantLoginDao;
    @Autowired
    private MerchantUserDao merchantUserDao;
    @Autowired
    private MerchantRoleDao merchantRoleDao;

    @Override
    public MerchantUserBo login(LoginBo bo) {
        List<MerchantLogin> merchantLogins = merchantLoginDao.searchByAccount(bo.getAccount());
        // 无此用户
        if (CollectionUtils.isEmpty(merchantLogins)) {
            return null;
        }
        MerchantLogin login = merchantLogins.get(0);
        if (!login.getPassword().equals(bo.getPassword())) {
            throw new BusinessException(ERROR_4002);
        }
        Assert.notNull(login.getUserId(), "用户信息不合法");
        MerchantUser user = merchantUserDao.getUserById(login.getUserId());
        Assert.notNull(user, "用户信息不合法");
        List<String> roles = merchantRoleDao.searchRolesByUserId(login.getUserId());
        MerchantUserBo userBo = BeanUtils.copyBean(login, MerchantUserBo.class);
        userBo.setUsername(user.getName());
        userBo.setRoles(roles);
        return userBo;
    }

    @Override
    public Boolean create(String account, String password) {
        long id = IdUtil.createSnowflake(1, 1).nextId();
        String pwd = SecureUtil.md5(password);
        MerchantLogin merchantLogin = new MerchantLogin();
        merchantLogin.setUserId(0L);
        merchantLogin.setId(id);
        merchantLogin.setAccount(account);
        merchantLogin.setPassword(pwd);
        merchantLogin.setSalt("");
        merchantLogin.setCreator(0L);
        merchantLogin.setModifier(0L);
        return merchantLoginDao.addMerchantLogin(merchantLogin);
    }
}
