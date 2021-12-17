# Springcolud

## CVE-2021-22053

>今天有幸看到了三梦师傅写的[CVE-2021-22053: Spring Cloud Netflix Dashboard template resolution vulnerability](https://github.com/SecCoder-Security-Lab/spring-cloud-netflix-hystrix-dashboard-cve-2021-22053) poc，在好自己最近在看spring-cloud这些微服务，然后就简单的看了看学习。

先简单的介绍一下**hystrix**

## Hystrix

容错监控机制

也就是微服务的容错机制是提前预设解决⽅案，系统进⾏⾃主调节，遇到问题及时处理

### Hystrix的优点

```
服务隔离机制
服务降级机制
熔断机制
提供实时的监控和报警功能
提供实时的配置修改功能
```

而hystrix-dashboard 就是可视化界⾯组件。

所以简单的说spring-cloud-starter-netflix-hystrix-dashboard 就是springcolud中的一个组件，是**Hystrix** 容错监控机制的可视化界⾯组件。



## 复现

三梦师傅也说明了漏洞版本

漏洞版本：spring-cloud-starter-netflix-hystrix-dashboard  **2.2.0.RELEASE to 2.2.9.RELEASE**

并且三梦师傅提供了漏洞环境，本地搭建起测试了一下，成功利用。

![image-20211123210133522](https://user-images.githubusercontent.com/63966847/146506766-df253d5b-d032-43cf-8019-d4af56376cca.png)


## 分析

还是经典的对比分析，对比漏洞版本和fix版本

![image-20211123210337765](https://user-images.githubusercontent.com/63966847/146506804-c8600043-5e0a-4d87-b3be-3230a1fd8587.png)


可以发现漏洞版本对{path}变量可以控制，熟悉**Thymeleaf**模板注入的师傅一眼就可以看出来了。

在该版本依赖的Thymeleaf组件版本是3.0.12。正好三梦师傅师傅之前写过文章bypass。前几天panda师傅也发了文章关于这部分进行介绍。

![image-20211123211222190](https://user-images.githubusercontent.com/63966847/146506787-871d831a-19bd-4571-a06b-19a65bdf080f.png)


poc

```http
http://127.0.0.1:8080/hystrix/;/__$%7BT%20(java.lang.Runtime).getRuntime().exec(%22calc%22)%7D__::.x/
```

