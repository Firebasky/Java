 package com.firebasky.shiro721.config;

 import org.apache.shiro.codec.Base64;
 import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
 import org.apache.shiro.web.mgt.CookieRememberMeManager;
 import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
 import org.apache.shiro.web.servlet.SimpleCookie;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;

 import java.util.LinkedHashMap;
 import java.util.Map;

 @Configuration
 public class ShiroConfig
 {
   @Bean
   MyRealm myRealm()
   {
     return new MyRealm();
   }
   
   @Bean
   DefaultWebSecurityManager securityManager() {
    DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
    manager.setRememberMeManager(getCookieRememberMeManager());
    manager.setRealm(myRealm());
     return manager;
   }
   
   @Bean
   ShiroFilterFactoryBean shiroFilterFactoryBean() {
     ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
    bean.setSecurityManager(securityManager());
     bean.setLoginUrl("/login");
     bean.setSuccessUrl("/index");
    bean.setUnauthorizedUrl("/unauthorizedurl");
    Map<String, String> map = new LinkedHashMap();
     map.put("/doLogin", "anon");
     map.put("/admin/*", "authc");
    bean.setFilterChainDefinitionMap(map);
    return bean;
   }

//     如下两个bean配置rememberMe功能的cookie，不配置默认有效期1d
     @Bean
     public CookieRememberMeManager getCookieRememberMeManager() {
         CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
         cookieRememberMeManager.setCookie(getSimpleCookie());
         cookieRememberMeManager.setCipherKey(Base64.decode("Z3VucwAAAAAAAAAAAAAAAA=="));
         return cookieRememberMeManager;
     }
     @Bean
     public SimpleCookie getSimpleCookie() {
         SimpleCookie cookie = new SimpleCookie("rememberMe");
         cookie.setHttpOnly(true);
         cookie.setMaxAge(1 * 24 * 60 * 60);//7天默认1天
         return cookie;
     }

 }
