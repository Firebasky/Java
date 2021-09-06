package com.firebasky.shiro721.config;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MyRealm extends AuthorizingRealm
        {
      protected org.apache.shiro.authz.AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
      {
            return null;
          }

      protected org.apache.shiro.authc.AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws org.apache.shiro.authc.AuthenticationException {
          String username = (String)token.getPrincipal();
           if (!"admin".equals(username)) {
                throw new org.apache.shiro.authc.UnknownAccountException("账户不存在!");
                }
          return new org.apache.shiro.authc.SimpleAuthenticationInfo(username, "123456", getName());
}
}

