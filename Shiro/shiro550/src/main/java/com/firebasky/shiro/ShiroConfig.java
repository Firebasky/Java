package com.firebasky.shiro;

import java.util.LinkedHashMap;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.vulhub.shirodemo.MainRealm;

@Configuration
public class ShiroConfig {

    @Bean
    MainRealm mainRealm() {
        return new MainRealm();
    }

    @Bean
    RememberMeManager cookieRememberMeManager() {
        return new CookieRememberMeManager();
    }


    @Bean
    SecurityManager securityManager(MainRealm mainRealm, RememberMeManager cookieRememberMeManager) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm((Realm)mainRealm);
        manager.setRememberMeManager(cookieRememberMeManager);
        return manager;
    }

    @Bean(name={"shiroFilter"})
    ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl("/login");
        bean.setUnauthorizedUrl("/unauth");
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("/doLogin", "anon");
        map.put("/**", "user");
        bean.setFilterChainDefinitionMap(map);
        return bean;
        /**
         * anon：无需认证。
         * authc：必须认证。
         * authcBasic：需要通过 HTTPBasic 认证。
         * user：不一定通过认证，只要曾经被 Shiro 记录即可，比如：记住我。
         */
        /**
         * perms：必须拥有某个权限才能访问。
         * role：必须拥有某个角色才能访问。
         * port：请求的端口必须是指定值才可以。
         * rest：请求必须基于 RESTful，POST、PUT、GET、DELETE。
         * ssl：必须是安全的 URL 请求，协议 HTTPS。
         */
    }
}