# Spring

+ [aop技术](./spring-aop底层.pdf)
+ [cve-2016-4977]()
+ [cve-2017-4971]()
+ [cve-2018-1270]()

## Spring Security
+ [Spring Security / MVC Path Matching Inconsistency(CVE-2016-5007)](https://mp.weixin.qq.com/s?__biz=MzAwMzI0MTMwOQ==&mid=2650173852&idx=1&sn=6b4a6c36c456b5e475b5247451c6dd81&chksm=833cf5aeb44b7cb895e1f67f8f6680e1a22124ce5e9e38d8a5e5321099f40e8acc01ac9e3c85&scene=4#wechat_redirect)

```
/%0dadmin
```

+ [CVE-2022-22978 Spring Security RegexRequestMatcher 认证绕过漏洞与利用场景分析](https://mp.weixin.qq.com/s?__biz=Mzg3MTU0MjkwNw==&mid=2247490023&idx=1&sn=f7e654f69ceca1ff437d9431bdd8ffa7&chksm=cefda0f3f98a29e5556a31b28ba231613e49b0ff40fcee651fac351adc6376e2ad2b72509dbf&mpshare=1&scene=23&srcid=0521LQrB49HRCgrnaPZOD2ys&sharer_sharetime=1653110684149&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd)

原理就是默认情况下, 正则表达式中点(.)不会匹配换行符, 设置了Pattern.DOTALL模式, 才会匹配所有字符包括换行符。从而绕过

![image](https://user-images.githubusercontent.com/63966847/169652431-125a8ebd-251d-4fec-a8dd-be20a3c60da5.png)


小知识：[Java中正则表达式(regex)匹配多行(Pattern.MULTILINE和Pattern.DOTALL模式)](https://www.cjavapy.com/article/68/)
