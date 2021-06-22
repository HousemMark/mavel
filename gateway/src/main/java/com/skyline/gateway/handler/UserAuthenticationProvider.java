package com.skyline.gateway.handler;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 自定义登录验证
 */
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取表单输入中返回的用户名
        String userName = (String) authentication.getPrincipal();
        // 获取表单中输入的密码
        String password = (String) authentication.getCredentials();
        // 查询用户是否存在
        // 判断密码是否正确，这里我们的密码使用BCryptPasswordEncoder进行加密的
        // 还可以加一些其他信息的判断，比如用户账号已停用，或者登录次数上限等判断
        // 查询用户角色
        // 进行登录
//        return new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
