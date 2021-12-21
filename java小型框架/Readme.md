# java小型框架代码审计

>[java危险函数](./java危险函数.md)
>[代码审计基础](./代码审计基础.pdf)

+ 2021/6/1   [JAVA代码审计的一些Tips(附脚本)](https://xz.aliyun.com/t/1633)
+ 2021/6/1   [java代码审计之租车系统](./java代码审计之租车系统.pdf)  [框架下载连接](http://down.chinaz.com/soft/38425.htm)
+ 2021/6/1   [JAVA代码审计之铁人下载系统 v1.0](http://foreversong.cn/archives/1005)
+ 2021/6/2   [JAVA代码审计 | 因酷网校在线教育系统](https://xz.aliyun.com/t/2646)
+ 2021/6/3   [Java 代码审计入门-02 SQL 漏洞原理与实际案例介绍](https://xz.aliyun.com/t/6872)             [流程图CVE-2019-9615](./img/CVE-2019-9615.png)
+ 2021/6/3   [Java 代码审计入门-03 XSS 漏洞原理与实际案例介绍](https://xz.aliyun.com/t/6937) [漏洞代码CVE_2018_19178](./code/CVE_2018_19178.java)
+ 2021/6/4   [Java 代码审计入门-04 SSRF 漏洞原理与实际案例介绍](https://xz.aliyun.com/t/7186)
+ 2021/10/6  学习了java代码审计书中的jspxcms,发现其中的**conn.getContentType('image')可以通过自己搭建的http服务器实现**。 evilserver.php
+ 2021/11/01  突然间看了一篇文章[代码审计入门之Jeeplus代码审计](https://www.freebuf.com/articles/web/220066.html)
+ 2021/11/02 [CVE-2020-10189 Zoho ManageEngine反序列化RCE](https://xz.aliyun.com/t/7439) **对文件进行反序列化，绕过上传。**  [参考](https://www.anquanke.com/post/id/200474)


## 好文章

https://www.sec-in.com/author/8 这个师傅太猛了 

+ 2021/12/21 [SpringMVC寻找Controller技巧](https://www.sec-in.com/article/552) **@(.*?)Mapping\(**
+ 2021/12/21 [绕过后缀安全检查进行文件上传](https://sec-in.com/article/647) **解决了条件竞争不知道文件名的问题，通过异常报错让程序停止向下执行绕过。（在multipart做文章）**
+ 2021/12/21 [绕过后缀安全检查进行文件上传-2](https://www.sec-in.com/article/1328) **只能说非常np了，servlet单例，存在线程安全问题。扩展一下java中volatile有可能存在线程安全问题[参考](https://github.com/Firebasky/Java/blob/main/java%E6%97%A5%E5%B8%B8/Thinking_in_java%E9%AB%98%E7%BA%A7%E4%B9%8Bvolatile.md)**  看看能不能搭建一个环境复现一下。。。。


## 学习
[java设计模式](https://www.runoob.com/design-pattern/design-pattern-tutorial.html)
