# jboss介绍：

JBoss 是一个基于J2EE的[开放源代码](https://baike.baidu.com/item/开放源代码/114160)的[应用服务器](https://baike.baidu.com/item/应用服务器/4971773)。 JBoss代码遵循LGPL许可，可以在任何商业应用中免费使用。JBoss是一个管理EJB的容器和服务器，支持EJB 1.1、EJB  2.0和EJB3的规范。但JBoss核心服务不包括支持servlet/JSP的WEB容器，一般与Tomcat或Jetty绑定使用。

自己测试了网上很多工具发现不是特别好用 而且不集中。。。。
所以自己想写一个综合利用的工具。。。

+ [JBOSS CVE-2017-12149 WAF绕过之旅](https://www.yulegeyu.com/2021/03/05/JBOSS-CVE-2017-12149-WAF%E7%BB%95%E8%BF%87%E4%B9%8B%E6%97%85/)

## CVE-2017-12149
bypass  请求方式是HEAD

**endpoint**
```
/invoker/readonly
/invoker/EJBInvokerServlet
/invoker/JMXInvokerServlet
/invoker/readonly/JMXInvokerServlet
/invoker/restricted/JMXInvokerServlet
```
