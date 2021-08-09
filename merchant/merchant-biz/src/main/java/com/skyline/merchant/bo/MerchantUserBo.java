package com.skyline.merchant.bo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
public class MerchantUserBo {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态
     */
    private String status;

    /**
     * 商户ID
     */
    private Long merchantId;

    /**
     * 账号名称
     */
    private String account;

    /**
     * 角色
     */
    private List<String> roles;

    /**
     * 权限资源
     */
    private Collection<String> resources;
}
