package com.myvhr.myvhr.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
@Component
public class UrlAccessDecisionManager implements AccessDecisionManager {
    /**
     *
     * @param authentication
     * @param o
     * @param collection
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     * @description 逻辑分为三层: needRole 为ROLE_LOGIN，未登录; collection和认证匹配有足够权限; 没有足够权限
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        Iterator<ConfigAttribute> iterator = collection.iterator();
        while (iterator.hasNext()){
            String needRole = iterator.next().getAttribute();
            if (needRole.equals("ROLE_LOGIN")){
                //是否登录
                if (authentication instanceof AnonymousAuthenticationToken){
                    //没有匿名认证
                    throw new BadCredentialsException("未登录!");
                }else{
                    return;
                }
            }
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities){
                //只要有一个角色匹配即可
                if (authority.getAuthority().equals(needRole)){
                    return;
                }
            }
        }
        throw  new AccessDeniedException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
