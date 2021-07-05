package com.skyline.merchant.bo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
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
     * 显示名称
     */
    private String displayName;

    /**
     * 用户参数
     */
    private Map<String, String> userParamMap;

    /**
     * 用户角色
     */
    private Collection<GrantedAuthority> authorities;
}
