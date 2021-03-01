# Struts2-004

## 漏洞概要

Struts2-004是一个目录遍历漏洞。

影响版本： **Struts 2.0.0 - 2.0.11.2、Struts 2.1.0 - 2.1.2** 。

官方通告：https://cwiki.apache.org/confluence/display/WW/S2-004 

## 漏洞原理

本次漏洞，主要问题出现在 **FilterDispatcher** 类对静态资源文件的处理。当请求资源文件路径以 **/struts** 开头时，就会调用 **findStaticResource** 方法寻找资源。

```java
if (serveStatic && resourcePath.startsWith("/struts")) {
    String name = resourcePath.substring("/struts".length());
    this.findStaticResource(name, request, response);
} else {
    chain.doFilter(request, response);
}
```

跟进 **findStaticResource** 方法，我们发现如果文件后缀不为 **.class** ，则调用 **findInputStream** 来读取文件内容，而且文件路径会进行一次 **URLdecode** ，但是这里没有对文件名进行任何过滤，这也导致了路径穿越问题。

```java
if (!name.endsWith(".class")) {
    String[] arr$ = this.pathPrefixes;
    int len$ = arr$.length;

    for(int i$ = 0; i$ < len$; ++i$) {
        String pathPrefix = arr$[i$];
        InputStream is = this.findInputStream(name, pathPrefix);//读文件
        ...
    }
```

poc：`http://localhost:8081/struts/..%2f..%2f`

## 漏洞修复

修复代码,使用 **URL.getFile()** 获取文件的真实路径，然后在用 **endWith** 来判断后缀，二者结合可以有效解决路径穿越问题。

## 参考

>https://mochazz.github.io/2020/06/28/Java%E4%BB%A3%E7%A0%81%E5%AE%A1%E8%AE%A1%E4%B9%8BStruts2-004/
