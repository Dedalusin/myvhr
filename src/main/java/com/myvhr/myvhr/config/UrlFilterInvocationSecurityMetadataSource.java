package com.myvhr.myvhr.config;

import com.myvhr.myvhr.mapper.MenuMapper;
import com.myvhr.myvhr.model.Menu;
import com.myvhr.myvhr.model.Role;
import com.myvhr.myvhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    MenuService menuService;
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * @param o
     * @return SecurityConfig类型的list,SecurityConfig主实例变量为final的string，此处为role
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //解析得到地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        if ("/login_p".equals(requestUrl)) {
            return null;
        }
        List<Menu> menuList = menuService.getAllMenu();
        for(Menu menu : menuList){
            if (antPathMatcher.match(menu.getUrl(), requestUrl)){
                //SecurityConfig的createList方法的参数为string数组，需要转换
                List<Role> temp = menu.getRoles();
                String[] roles = new String[temp.size()];
                for(int i = 0; i < temp.size(); i++){
                    roles[i] = temp.get(i).getName();
                }
                return SecurityConfig.createList(roles);
            }
        }
        //没有匹配上为最低权限
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        //判断是否为filterInvocation的子类
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
