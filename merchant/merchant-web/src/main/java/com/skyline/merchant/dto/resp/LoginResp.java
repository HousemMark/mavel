package com.skyline.merchant.dto.resp;

import lombok.Data;

import java.util.Collection;

@Data
public class LoginResp {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 商户ID
     */
    private Long merchantId;

    /**
     * 状态
     */
    private String status;

    /**
     * 账号名称
     */
    private String account;

    /**
     * 角色
     */
    private String role;

    /**
     * 权限资源
     */
    private Collection<String> resources;
}
