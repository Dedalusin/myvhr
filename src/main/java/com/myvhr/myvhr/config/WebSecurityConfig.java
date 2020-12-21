package com.myvhr.myvhr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myvhr.myvhr.common.HrUtils;
import com.myvhr.myvhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    HrService hrService;
    @Autowired
    AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;
    @Autowired
    UrlAccessDecisionManager urlAccessDecisionManager;
    @Autowired
    UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html", "/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o){
                        o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                        o.setAccessDecisionManager(urlAccessDecisionManager);
                        return o;
                    }
                })
                .and()
                .formLogin()
                .loginPage("/login_p")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll().failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest resq, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        StringBuffer sb = new StringBuffer();
                        sb.append("{\"status\":\"error\",\"msg\":\"");
                        //根据传入的异常判断
                        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException){
                            sb.append("用户名或密码输入不正确");
                        } else if (e instanceof DisabledException) {
                            sb.append("账户被禁用，登录失败，请联系管理员!");
                        } else {
                            sb.append("登录失败!!");
                        }
                        sb.append("\"}");
                        out.write(sb.toString());
                        out.flush();
                        out.close();
                    }
                })
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest resq, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        ObjectMapper objectMapper = new ObjectMapper();
                        String s = "{\"status\":\"success\",\"msg\":" + objectMapper.writeValueAsString(HrUtils.getCurrentHr()) + "}";
                        out.write(s);
                        out.flush();
                        out.close();
                    }
                })
                .and().logout().permitAll()
                .and().csrf().disable()
                //在accessDecisionManager中定义了异常
                .exceptionHandling().accessDeniedHandler(authenticationAccessDeniedHandler);
    }
}
