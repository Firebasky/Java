# Shiro权限绕过

## CVE-2020-1957

**payload：/demo/..;/admin/index**

简单的说因为有；导致截取了分号前面，也就是我们这里的 /demo/.. 然后和 /demo/** 匹配上了。

然后成功绕过shiro，在spring层;被忽视，然后成功匹配路径。

### 环境搭建

```xml
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-web</artifactId>
    <version>1.5.1</version>
</dependency>
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-spring</artifactId>
    <version>1.5.1</version>
</dependency>
```

### 利用条件

- Apache Shiro <= 1.5.1

- Spring 框架中只使用 Shiro 鉴权

  

### 漏洞修复

https://github.com/apache/shiro/commit/3708d7907016bf2fa12691dff6ff0def1249b8ce#diff-98f7bc5c0391389e56531f8b3754081aR139

将原先的 request.getRequestURI() 替换成了 getContextPath() 、getServletPath() 、getPathInfo() 的组合，这样就能获取我们想要的了，从而避免因为获取差异性而导致绕过，这样就与返回给 springboot 的路径保持一致了。

修复了因为;导致的截断问题。

## CVE-2020-11989

[Apache Shiro权限绕过漏洞CVE-2020-11989分析](https://www.anquanke.com/post/id/222489)

**payload：/admin/%252fxxx**

漏洞产生的原因是因为 Spring 与 Shiro 之间对 url 的处理不同从而导致权限绕过.

简单的说就是shiro层进行了2次url解码，spring层使用了一次。

### 环境搭建

```xml
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-web</artifactId>
    <version>1.5.2</version>
</dependency>
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-spring</artifactId>
    <version>1.5.2</version>
</dependency>
```

### 利用条件

- Apache Shiro <= 1.5.2
- Spring 框架中只使用 Shiro 鉴权

- 需要后端特定的格式才可进行触发

- 即：Shiro权限配置必须为 /xxxx/* ，同时后端逻辑必须是 /xxx/{variable} 且 variable 的类型必须是 String

### 漏洞修复

在 Shiro 1.5.3 版本中对getPathWithinApplication进行了修改，取消了url 解码的函数.



## CVE-2020-13933

**payload：/admin/%3Bxx**

简单的说就是url解码了之后如果存在；就截断前面的路径并且会将截断的uri最后的 / 进行去除,然后就匹配不到/admin/*,因为是/admin

所以我们只要利用 **/admin/%3Bxx**这样的结构就可以绕过 Shiro 的权限校验。

### 环境搭建

同CVE-2020-11989

pom.xml 中版本修改为1.5.3 

### 利用条件

- Apache Shiro < 1.6.0
- Spring 框架中只使用 Shiro 鉴权

- 需要后端特定的格式才可进行触发

- 即：Shiro权限配置必须为 /xxxx/* ，同时后端逻辑必须是 /xxx/{variable} 且 variable 的类型必须是 String

### 漏洞修复

https://github.com/apache/shiro/commit/dc194fc977ab6cfbf3c1ecb085e2bac5db14af6d

增加了 InvalidRequestFilter 类来对一些特殊情况进行处理，遇到特殊字符直接400，同时增加了 /** 的规则，来防止一些匹配不到的情况。

## CVE-2020-17523

**payload：/admin/%20**

简单的说就是我们传递/admin/%20 url解编之后在进行匹配的时候去除了空格，/admin/ 与/admin/*不匹配造成绕过。

### 漏洞环境

同 CVE-2020-11989 环境

pom.xml 中版本修改为 1.7.0 或及以下即可 

### 利用条件

- Apache Shiro < 1.7.1
- Spring 框架中只使用 Shiro 鉴权

- 需要后端特定的格式才可进行触发

- 即：Shiro权限配置必须为 /xxxx/* ，同时后端逻辑必须是 /xxx/{variable} 且 variable 的类型必须是 String

### 漏洞修复

增加了选项，tokenizeToStringArray 函数中默认不会进行 trim,默认情况下 token = token.trim() 并不会被执行


## CVE-2021-41303

![image](https://user-images.githubusercontent.com/63966847/133890465-dfbd4a6b-524d-4f5a-96d6-2b2dcb10e27f.png)

## 字典

```
/index/..;/admin/index
/login/..;/admin/index
/index/..;/admin/
/login/..;/admin/
/login/..;/json

/actuator/;/env/
/admin/;/xxx
/admin/%3b/xxx
/admin/%252fxxx
/admin/%3Bxx
/admin/%20
```

## CVE-2022-32532

[CVE-2022-32532](https://github.com/4ra1n/CVE-2022-32532)
原理参考[CVE-2022-22978 Spring Security RegexRequestMatcher 认证绕过及转发流程分析](https://xz.aliyun.com/t/11473)

>参考：
>
>https://www.yuque.com/tianxiadamutou/zcfd4v/emcdeq
