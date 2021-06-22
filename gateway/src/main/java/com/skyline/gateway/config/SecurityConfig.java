package com.skyline.gateway.config;

import com.skyline.gateway.filter.JWTAuthenticationTokenFilter;
import com.skyline.gateway.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
@EnableWebFluxSecurity
//开启权限注解,默认是关闭的
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义登录成功处理器
     */
    @Autowired
    private UserLoginSuccessHandler userLoginSuccessHandler;
    /**
     * 自定义登录失败处理器
     */
    @Autowired
    private UserLoginFailureHandler userLoginFailureHandler;
    /**
     * 自定义注销成功处理器
     */
    @Autowired
    private UserLogoutSuccessHandler userLogoutSuccessHandler;
    /**
     * 自定义暂无权限处理器
     */
    @Autowired
    private UserAuthAccessDeniedHandler userAuthAccessDeniedHandler;
    /**
     * 自定义未登录的处理器
     */
    @Autowired
    private UserAuthenticationEntryPointHandler userAuthenticationEntryPointHandler;
    /**
     * 自定义登录逻辑验证器
     */
    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    /**
     * 加密方式
     *
     * @Author mark
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 注入自定义PermissionEvaluator
     */
    @Bean
    public DefaultWebSecurityExpressionHandler userSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new UserPermissionEvaluator());
        return handler;
    }

    /**
     * 配置登录验证逻辑
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        //这里可启用我们自己的登陆验证逻辑
        auth.authenticationProvider(userAuthenticationProvider);
    }

    /**
     * 配置security的控制逻辑
     *
     * @Param http 请求
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().
                // 不进行权限验证的请求或资源(从配置文件中读取)
                        antMatchers(JWTConfig.antMatchers.split(",")).permitAll().
                // .antMatchers("/*").permitAll()
                // 其他的需要登陆后才能访问
                        anyRequest().authenticated().
                //配置未登录自定义处理类
                        and().httpBasic().authenticationEntryPoint(userAuthenticationEntryPointHandler).
                // 登录相关配置
                        and().formLogin().loginProcessingUrl("/login/userLogin").
                // 配置登录成功自定义类
                        successHandler(userLoginSuccessHandler).
                // 配置登录失败自定义类
                        failureHandler(userLoginFailureHandler).
                // 登出相关配置
                        and().logout().logoutUrl("/login/userLogout").
                // 配置登出成功自定义类
                        logoutSuccessHandler(userLogoutSuccessHandler).
                // 配置没有权限自定义处理类
                        and().exceptionHandling().accessDeniedHandler(userAuthAccessDeniedHandler).
                // 开启跨域
                        and().cors().
                // 取消跨站请求伪造防护
                        and().csrf().disable();

        // 基于token登录不需要session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 禁用缓存
        http.headers().cacheControl();

        // 添加JWT过滤器
        http.addFilter(new JWTAuthenticationTokenFilter(authenticationManager()));
    }
}
