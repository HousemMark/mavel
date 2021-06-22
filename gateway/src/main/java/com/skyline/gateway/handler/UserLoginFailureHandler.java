package com.skyline.gateway.handler;

import com.skyline.gateway.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class UserLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.warn("【登录失败】" + e.getMessage());
        // 对不同异常进行不同处理
        if (e instanceof UsernameNotFoundException) {
            ResultUtil.responseJson(response, ResultUtil.resultCode(500, "用户名不存在"));
        }
        if (e instanceof LockedException) {
            ResultUtil.responseJson(response, ResultUtil.resultCode(500, "用户被冻结"));
        }
        if (e instanceof BadCredentialsException) {
            ResultUtil.responseJson(response, ResultUtil.resultCode(500, "密码错误"));
        }
        ResultUtil.responseJson(response, ResultUtil.resultCode(500, "登录失败"));
    }
}
