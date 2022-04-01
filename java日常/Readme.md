# java日常

>有一些不知道这么分类就放里面吧
>
## 2021

+ 2021/7/27 [java-fix序列化漏洞](java-fix序列化漏洞.md)
+ 2021/7/28 [java执行js导致命令执行](java执行js导致命令执行.pdf)
+ 2021/7/28 [java反射](./img/反射.png)
+ 2021/7/29 [反射newInstance的使用方式](反射newInstance的使用方式.md)
+ 2021/8/18 [java-maven打包学习](java-maven打包学习.md)
+ 2021/8/18 [java基础-编译运行原理](https://fantiq.github.io/2019/08/13/java%E5%9F%BA%E7%A1%80-%E7%BC%96%E8%AF%91%E8%BF%90%E8%A1%8C%E5%8E%9F%E7%90%86/)
+ 2021/8/19 [java-xxe学习1](https://github.com/gyyyy/footprint/blob/master/articles/2018/xxe-injection-overview.md)
+ 2021/8/19 [探究Java中XXE漏洞的深层原理](https://gv7.me/articles/2019/study-the-deep-principle-of-xxe-vulnerability-in-java/)
+ 2021/8/19 [xxe流程图](./img/xxe.png)
+ 2021/8/20 [jep290的实现图片](./img/jep290.png)   [文章](https://y4er.com/post/bypass-jep290/)
+ 2021/8/31 [java写文件rce](java写文件rce.md)
+ 2021/8/31 [java协议](java协议.md)
+ 2021/9/01 [java反序列化编码绕过](java反序列化编码绕过.md)
+ 2021/9/03 [javabean与内省](javabean与内省.md)
+ 2021/9/03 [marshalsec工具使用](marshalsec.md)
+ 2021/9/03 [readobject深入](readobject深入.md)
+ 2021/9/03 [Thinking in java 高级之volatile](Thinking_in_java高级之volatile.md)   volatile如果对应单线程会不会存在条件竞争问题 参考d3ctf no rce
+ 2021/9/03 [yso搭建](yso搭建.md)
+ 2021/9/14 [yso中gadget的问题，有一个gadget正在泄露你的ID](https://mp.weixin.qq.com/s?__biz=Mzg3NjA4MTQ1NQ==&mid=2247484138&idx=1&sn=55d82300e8ffd567610926d887b42afc&chksm=cf36faaaf84173bc733c94198df766fd02f0309dd48882aba5847e0dcc8d6b57a0141183c4f3&mpshare=1&scene=23&srcid=0914UihVLrgENHy1xcbuIGIX&sharer_sharetime=1631594366441&sharer_shareid=33a823b10ae99f33a60db621d83241cb#rd)         个人感觉思路非常非常好。思路开拓。
+ 2021/9/14 [Java动态类加载，当FastJson遇到内网](https://kingx.me/Exploit-FastJson-Without-Reverse-Connect.html)   **个人感觉其中的fastjson触发特定的getter说的比较清楚，如果是parseObject，就会将Java对象转为JSONObject对象即调用JSON.toJSON，在处理过程中会调用所有的 setter 和 getter 方法。而如果是parse方法，原理是fastjson反序列化是生成了一个jsonobject,而JSONObject放在JSON Key的位置上，在 JSON 反序列化的时候，FastJson又会对JSON Key 自动调用 toString() 方法**
+ 2021/9/15 [FastJson反序列化漏洞利用的三个细节 - TemplatesImpl的利用链](https://kingx.me/Details-in-FastJson-RCE.html)
+ 2021/9/18 [Java 反序列化漏洞始末（3）— fastjson](https://b1ue.cn/archives/184.html)
+ 2021/9/18 [log4j<=1.2.17反序列化漏洞（CVE-2019-17571）分析](https://mp.weixin.qq.com/s?__biz=Mzg3NjA4MTQ1NQ==&mid=2247483962&idx=1&sn=0e059564c368b84e3483704821aac06b&chksm=cf36fa7af841736c622b957459091f3dd994adbfbc8bf8bcab032995c0885776c62530eaf465&mpshare=1&scene=23&srcid=0918r2rgVPTbTKFRbVikY7cS&sharer_sharetime=1631972571155&sharer_shareid=33a823b10ae99f33a60db621d83241cb#rd)  **log4j组件存在反序列化漏洞端口4560**
+ 2021/9/24 [搞懂RMI、JRMP、JNDI-终结篇](https://threedr3am.github.io/2020/03/03/%E6%90%9E%E6%87%82RMI%E3%80%81JRMP%E3%80%81JNDI-%E7%BB%88%E7%BB%93%E7%AF%87/)  **LDAP服务攻击一般是先测Reference再测deserializeObject**
+ 2021/9/25 [JAVA Apache-CommonsCollections 序列化漏洞分析以及漏洞高级利用](https://www.iswin.org/2015/11/13/Apache-CommonsCollections-Deserialized-Vulnerability/)  [实现代码](https://github.com/Firebasky/Java/tree/main/java%E5%9B%9E%E6%98%BE)
+ 2021/9/26 [gradle和maven有什么用？分别有什么区别](https://www.zhihu.com/question/29338218)  **简单的了解了一下gradle项目搭建**
+ 2021/9/27 [java 反序列化 ysoserial exploit/JRMPClient 原理剖析](https://dandelioncloud.cn/article/details/1432371613173100545/) **简单的说让靶机反序列化payloads/JRMPListener进行开启rmi端口，然后使用exploit/JRMPClient去连接并且发送payload反序列化攻击，是利用DGC通信的。JEP 290之后就不行了。**
+ 2021/9/27 [java 反序列化 ysoserial exploit/JRMPListener 原理剖析](https://dandelioncloud.cn/article/details/1432371613252792321) **简单的说自己的vps上开启jrmp服务监听，靶机反序列化payloads/JRMPClient 然后来连接我们的vps，我们在发送payload进行反序列化，原理也是利用DGC通信的。在使用this.classLoader.loadClass加载时，可以来绕过。** 见：
+ 2021/10/2 [缩小ysoserial payload体积的几个方法](https://xz.aliyun.com/t/6227) **原理上是通过createTemplatesImpl去实现的** 说不定有时候会遇到呢？
+ 2021/10/3 [Java 实现后台执行](https://jayl1n.github.io/2020/02/13/java-nohup-implementation/)
+ 2021/10/3 [Java项目中常见jar包的说明](https://www.mi1k7ea.com/2019/11/25/%EF%BC%88%E8%BD%AC%EF%BC%89Java%E9%A1%B9%E7%9B%AE%E4%B8%AD%E5%B8%B8%E8%A7%81jar%E5%8C%85%E7%9A%84%E8%AF%B4%E6%98%8E/)  **熟悉一些jar包了解了解**
+ 2021/10/3 [XXL-JOB Hessian2反序列化漏洞](https://www.mi1k7ea.com/2021/04/22/XXL-JOB-Hessian2%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E6%BC%8F%E6%B4%9E/)
+ 2021/10/3 [Java代码反反编译对抗思路](https://www.mi1k7ea.com/2020/05/01/Java%E4%BB%A3%E7%A0%81%E5%8F%8D%E5%8F%8D%E7%BC%96%E8%AF%91%E6%80%9D%E8%B7%AF/) **之前自己想通过加密class实现，不过不是特别通用就没有写了。。。。**
+ 2021/10/10 [XXE漏洞利用技巧：从XML到远程代码执行](https://blog.csdn.net/u012206617/article/details/109038388) **其中总结的xxe利用非常好，还有挖掘思路和netdoc协议和jar协议，还有自定义http访问延迟连接保证临时文件不被快速删除**
+ 2021/10/19 [JAVA代码审计业务安全Checklist](https://mp.weixin.qq.com/s?__biz=MzI5MDU1NDk2MA==&mid=2247500712&idx=1&sn=13027edf1e9d3385b650e611e9f559ab&chksm=ec1c9697db6b1f812fd88463a8d8301303b8c7cc364497d2ce1ca2190cf96701ad25463fc01d&mpshare=1&scene=23&srcid=1018GCa0aDvbQenw0fTuSv6F&sharer_sharetime=1634527194186&sharer_shareid=33a823b10ae99f33a60db621d83241cb#rd)  **代码审计比较帮助**
+ 2021/10/19 [在Jfinal使用redis缓存时存在的反序列化问题](https://b1eed.github.io/2020/12/05/Jfinal_readObject/) **这个和CVE-2020-26945 mybatis二级缓存反序列化有异曲同工之妙**
+ 2021/10/21 [自定义ClassLoader绕过poc为什么很多人执行出现问题的缘由](https://github.com/codeplutos/java-security-manager-bypass/issues/2) **可能以后会遇到。**
+ 2021/10/25 [defineClass在java反序列化当中的利用](https://paper.seebug.org/572/)  defineClass可以从byte[]还原出一个Class对象 **org.mozilla.classfile.DefiningClassLoader#defineClass**
+ 2021/10/26 [浅析JDWP远程命令执行漏洞](https://www.mi1k7ea.com/2021/08/06/%E6%B5%85%E6%9E%90JDWP%E8%BF%9C%E7%A8%8B%E4%BB%A3%E7%A0%81%E6%89%A7%E8%A1%8C%E6%BC%8F%E6%B4%9E/) 大概就是开启了远程Debugger..
+ 2021/10/26 [浅析Ofbiz反序列化漏洞CVE-2020-9496](https://www.mi1k7ea.com/2021/09/21/%E6%B5%85%E6%9E%90Ofbiz%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E6%BC%8F%E6%B4%9E%EF%BC%88CVE-2020-9496%EF%BC%89/) 版本小于17.12.04 
+ 2021/10/26 [Hessian 原理分析](https://www.cnblogs.com/shangxiaofei/p/4222170.html) 大概就是以二进制数组传输的rpc，存在反序列化问题。
+ 2021/10/26 [XXL-JOB Hessian2反序列化漏洞](https://www.mi1k7ea.com/2021/04/22/XXL-JOB-Hessian2%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E6%BC%8F%E6%B4%9E/)
+ 2021/10/30 [Mojarra JSF ViewState 反序列化漏洞](https://blog.csdn.net/xuandao_ahfengren/article/details/113135364) 
+ 2021/11/02 [关于Java 中 XXE 的利用限制探究](https://www.freebuf.com/articles/web/284225.html) **使用http外带数据不能有换行，使用ftp可以解决，但是ftp在java 8u131修复了这个漏洞 CVE-2017-3533**
+ 2021/11/02 [Adobe ColdFusion 反序列化漏洞（CVE-2017-3066）](https://github.com/vulhub/vulhub/blob/master/coldfusion/CVE-2017-3066/README.zh-cn.md) 暴露接口反序列化。。。
+ 2021/11/03 [浅谈Liferay Portal JSON Web Service未授权反序列化远程代码执行漏洞](https://xz.aliyun.com/t/7485)
+ 2021/11/03 [H2 Database Console 未授权访问](https://github.com/vulhub/vulhub/blob/master/h2database/h2-console-unacc/README.zh-cn.md)
+ 2021/11/30 [wsdl相关](wsdl.md) **接触到一些soap协议。。。**
+ 2021/12/11 [log4j 漏洞一些特殊的利用方式](https://mp.weixin.qq.com/s?__biz=Mzg4OTExMjE2Mw==&mid=2247483945&idx=1&sn=b15b68d95da83bb20f1b3496396f823a&chksm=cff19125f88618338373a32f98be3d2a9497b464d6531658c2aa96f4872c23eed294441917b5&mpshare=1&scene=23&srcid=1211aS0Tghr1agBnBRlwwGTw&sharer_sharetime=1639232420884&sharer_shareid=33a823b10ae99f33a60db621d83241cb#rd) **自己也发现了这个利用思路，不过不知道ResourceBundle类是干什么的。。。。** https://www.cnblogs.com/jona-test/p/11399218.html  **用来读取项目中后缀为properties的配置文件，然后通过dnslog或者ldap回显示**
+ 2021/12/13 [Java读文件](https://www.cnblogs.com/hkgov/p/14707726.html) **方便操作。。。**
+ 2021/12/14 [不使用new创建对象](https://zhuanlan.zhihu.com/p/214093086) **unsafe.allocateInstance()** [unsafe使用](https://tech.meituan.com/2019/02/14/talk-about-java-magic-class-unsafe.html)
+ 2021/12/14 [Spring Boot中关于%2e的Trick](http://rui0.cn/archives/1643)
+ 2021/12/18 [闲谈log4j2](闲谈log4j2.md)
+ 2021/12/19 [SPI机制](SPI机制.md)
+ 2021/12/21 [从一个被Tomcat拒绝的漏洞到特殊内存马](https://xz.aliyun.com/t/10577) **简单的说就是Tomcat启动时会加载lib下的依赖jar，如果黑客通过上传漏洞或者反序列化漏洞在这个目录添加一个jar，重启后，某些情况下这个jar会被当成正常库来加载，在一定条件下造成RCE** 这个功能是非常正常的因为中间件的类加载机制不是双亲委派机制.都是自己实现的
+ 2021/12/23 [寻找复杂对象的属性值](searchobj.md) **是在使用object-searcher的时候想到了，于是去学习了一下在java中通过递归去寻找复杂对象的属性。**
+ 2021/12/29 [jps命令](https://www.cnblogs.com/keystone/p/10789382.html) **其中可以支持远程调用并且默认情况下， jstatd开启在1099 端口上开启RMI服务器，rmi服务？想到了什么！**
+ 2021/12/30 [浅谈Log4j2不借助dnslog的检测](https://xz.aliyun.com/t/10676) **还得是大哥，思路太棒了，通过tcp数据传输的方面来验证漏洞。。学习！**
+ 2021/12/31 [构造java探测class反序列化gadget的思考](构造java探测class反序列化gadget的思考.md) **2021 新年快乐**

## 2022
+ 2022/01/07 [GadgetProbe：利用反序列化来暴力破解远程类路径](https://bishopfox.com/blog/gadgetprobe) **和构造java探测class反序列化gadget的思考思路是一样的**
+ 2022/01/08 [目录穿越上传](https://github.com/metersphere/metersphere/issues/8653) **tw上有一个后台上传jar插件进行getshell的**
+ 2022/01/08 [关于 Java 中的 RMI-IIOP](https://paper.seebug.org/1105/) **没有这么看懂。23333**
+ 2022/01/08 [你了解 SpringBoot java -jar 的启动原理吗？](https://xie.infoq.cn/article/765f324659d44a5e1eae1ee0c) 
+ 2022/01/08 [c语言能不能实现agent?](c语言能实现agent%3F!.md) **通过编写c语言代码去实现agent**  **实现了通用的代码https://gist.github.com/Firebasky/c1efd9dc7eb964a77cb788c170a8598f**
+ 2022/01/09 [瞒天过海计之Tomcat隐藏内存马](https://tttang.com/archive/1368/) 思路是[从一个被Tomcat拒绝的漏洞到特殊内存马](https://xz.aliyun.com/t/10577)中不过使用了agent去实现并且工具化了，太猛了(有空学习一下ysomap。。。。
+ 2022/01/09 [入侵JVM?Java Agent原理浅析和实践中](https://blog.csdn.net/CringKong/article/details/120840827)
+ 2022/01/09 [Java内存攻击技术漫谈](https://xz.aliyun.com/t/10075) **神仙级别的文章，还没有看完23333，慢慢消化** 
+ 2022/01/09 [解决agent中tools加载的问题](解决agent中tools加载的问题.md) **学习思路**
+ 2022/01/10 [unsafe学习](unsafe学习.md) **可以用来bypass 反射filter**
+ 2022/01/14 [CVE-2021-45456ApacheKylin命令注入分析补充](CVE-2021-45456ApacheKylin命令注入分析补充.md) **对2次函数的处理出现了问题**
+ 2022/01/15 [wJa无源码的源码级调试jar包](https://www.freebuf.com/sectool/318013.html) **这个工具好像是比较新?还没有使用过** [bilibil视频](https://www.bilibili.com/video/BV19m4y1Q75X/)
+ 2022/01/15 [使用 Yakit 打破 Java 序列化协议语言隔离](https://www.freebuf.com/sectool/318064.html) **比较好从字节码的方向出发方向，之前写gormi的时候也遇到了这个问题**
+ 2022/01/15 [CVE 2021 43287分析](CVE-2021-43287.md) 
+ 2022/01/16 [探索高版本 JDK 下 JNDI 漏洞的利用方法](https://tttang.com/archive/1405) **不愧是浅蓝师傅，非常好的文章**  [JNDI jdk高版本绕过—— Druid](https://xz.aliyun.com/u/23823) **都是根据本地的Factory类去寻找的**  **自己使用ast实现了去寻找的功能。。。**
+ 2022/01/18 [一次jsp上传绕过的思考 --yzddMr6](https://www.jianshu.com/p/c0c566de4e97) **感觉自己需要去详细的了解一下jsp** [jsp标签绕过](jsp标签绕过.md)
+ 2022/01/20 [Tomcat URL解析差异性及利用](http://www.mi1k7ea.com/2020/04/01/Tomcat-URL%E8%A7%A3%E6%9E%90%E5%B7%AE%E5%BC%82%E6%80%A7%E5%8F%8A%E5%88%A9%E7%94%A8/) **Tomcat对`/;xxx/`以及`/./`的处理是包容的、对`/../`会进行跨目录拼接处理**  [tomcat容器url解析特性研究](https://xz.aliyun.com/t/10799)
+ 2022/01/20 [微某OA从0day流量分析到武器化利用](https://mp.weixin.qq.com/s/iTP9jBypsJEsSlAIaNOnhw)  [exp](https://github.com/0730Nophone/E-cology-WorkflowServiceXml-)
+ 2022/01/21 [使用JVMTI技术解密class文件](https://landgrey.me/blog/5/) **JVMTI**去操作类似攻击思想agent加载c原因实现攻击
+ 2022/01/21 [JSP 包含文件的四种方法](https://landgrey.me/blog/4/) **利用<jsp:directive.include file="include.txt"/>**可以包含非jsp的文件，可以利用来做后门。[jsp include后门文件](https://www.javaweb.org/?p=84)
+ 2022/01/22 [Java Timer 后门](https://www.javaweb.org/?p=544) **只能说思路太厉害了** **Timer 的特性是，如果不是所有未完成的任务都已完成执行，或不调用 Timer 对象的cancel 方法，这个线程是不会停止，也不会被 GC 的，因此，这个任务会一直执行下去，直到应用关闭。**
+ 2022/01/23 [用Java 调试协议JDWP(Java DEbugger Wire Protocol) 弹shell](https://www.javaweb.org/?p=1875) **之前面试问到了内网中存在的比较多**
+ 2022/01/23 [JAVA虚拟机关闭钩子(Shutdown Hook)](https://blog.csdn.net/u013256816/article/details/50394923) **可用在jvm关闭的时候执行奇奇怪怪的存在比如：不死内存木马**
+ 2022/01/24 [浅谈加载字节码相关的Java安全问题](https://xz.aliyun.com/t/10535) **理论上还有其他的加载器。** 
+ 2022/01/25 [学习了asm的简单使用](asm.md)  [基于污点分析的JSP Webshell检测](https://xz.aliyun.com/t/10622) **其中主要是利用了asm和模拟jvm操作去判断的**  [加载恶意字节码Webshell的检测](https://xz.aliyun.com/t/10636)
+ 2022/01/31 [java执行shellcode的几种方法](https://mp.weixin.qq.com/s?__biz=MzUzNTEyMTE0Mw==&mid=2247484630&idx=1&sn=5d911558674ba5a210988df35addb3eb&chksm=fa8b194ecdfc9058194a730f280fbf0eb31deaddf1bbdbb135493d593e876b807e6cc14ecae8&mpshare=1&scene=23&srcid=01319e5soHkeMskTioS9UgSt&sharer_sharetime=1643563538758&sharer_shareid=33a823b10ae99f33a60db621d83241cb#rd)
+ 2022/02/01 [Java环境下通过时间竞争实现DNS Rebinding 绕过SSRF 防御限制](https://mp.weixin.qq.com/s?__biz=MzA4ODc0MTIwMw==&mid=2652533185&idx=1&sn=e960a15c6dd5071b22d615c6fe85ba8c&chksm=8bcb55fdbcbcdcebb1433de02cd3250c4d460e9401581817f15d887d90adcdf5c453ec9c31b1&mpshare=1&scene=23&srcid=0201Tl4H6As13axhsze8JJi9&sharer_sharetime=1643683667134&sharer_shareid=33a823b10ae99f33a60db621d83241cb#rd) **通过条件竞争绕过java默认ttl不为0**
+ 2022/02/02 [The Story of an RCE on a Java Web Application](https://infosecwriteups.com/the-story-of-a-rce-on-a-java-web-application-2e400cddcd1e)
+ 2022/02/04 [深入学习tomcat](深入学习tomcat.md) **清楚了tomcat的流程**
+ 2022/02/07 [Resin解析漏洞分析](https://mp.weixin.qq.com/s?__biz=MzIxNTIzMzM1Ng==&mid=2651103763&idx=1&sn=f3147eae969a17bd04e0a6471e2109e0&chksm=8c6b6430bb1ced267d4294a72bc991d2780b2a1521504660a010900319ae637b114c453c3057&mpshare=1&scene=23&srcid=02075OY7BLNDsUwhHiaWDFw4&sharer_sharetime=1644211604502&sharer_shareid=33a823b10ae99f33a60db621d83241cb#rd) **主要出问题的是^.*\.jsp(?=/)部分这个正则的逻辑是匹配xxxx.jsp/xxxx所以我们传入的路径会被匹配到,这也是这个漏洞的本质原因**
+ 2022/02/09 [Java加载动态链接库](http://tttang.com/archive/1436/) **模拟实现加载动态链接库webshell**
+ 2022/02/09 [Tomcat Session(CVE-2020-9484)反序列化漏洞复现](https://www.freebuf.com/vuls/245232.html)  [Apache Tomcat权限提升漏洞分析CVE-2022-23181](https://mp.weixin.qq.com/s/sQH0CbiSHdpsoJf7ABPrtA)
+ 2022/02/10 [通过代码执行修改Shiro密钥](https://mp.weixin.qq.com/s?__biz=MzkzNTI4NjU1Mw==&mid=2247483900&idx=1&sn=af727619a14b4677acb6ddad156524b9&chksm=c2b1038af5c68a9cc5185e1ed9ec0aa13963de3b2853c13fc0bf7a6184ec649cae9b28eda621&mpshare=1&scene=23&srcid=0210HYf3qYA2UnBm9sCrvUGM&sharer_sharetime=1644429131269&sharer_shareid=33a823b10ae99f33a60db621d83241cb#rd)
+ 2022/02/10 shiro利用head太长解决办法？ 1.反射修改headmax值 2.获取request，将post的数据进行classloader加载，加载的内容为动态注册filter内存马。
+ 2022/02/10 [java调用shell脚本_记一次突破反弹shell](https://blog.csdn.net/weixin_39620845/article/details/111048567)  **反弹java socket shell实现不依赖反弹shell环境**
+ 2022/02/10 [OpenRASP 两次绕过](https://mp.weixin.qq.com/s/hkL8VPHnTgFsOCCrNlRpzQ) **1.修改特征值 2.在静态代码里面开启新线程调用恶意方法**
+ 2022/02/21 **java 中存在编译时执行函数（注解的方式执行）**
+ 2022/02/23 [原创 | emoji、shiro与log4j2漏洞](https://mp.weixin.qq.com/s/mEwljigkkXk-y1ik7au_CQ) **通过fuzz报错记录log触发log4j2漏洞**
+ 2022/03/06 [Make JDBC Attacks Brilliant Again 番外篇](https://tttang.com/archive/1462/) **np!**
+ 2022/03/07 [Java Web —— 从内存中Dump JDBC数据库明文密码](https://mp.weixin.qq.com/s?__biz=Mzg5OTQ3NzA2MQ==&mid=2247485138&idx=1&sn=1229156e187fedd7b4aa4b1ac6c8f490&chksm=c053fdf8f72474eeb936fdfcefa43a74e2a7f661b9b98bff73330e5e661184440821047addf7&mpshare=1&scene=23&srcid=0307Aw2UzS1q0Fsdy5d2vqCD&sharer_sharetime=1646624025057&sharer_shareid=33a823b10ae99f33a60db621d83241cb#rd) **在connect之前hook任何写入用户名密码**
+ 2022/03/14 [关于JavaWeb后门问题](https://wooyun.js.org/drops/%E6%94%BB%E5%87%BBJavaWeb%E5%BA%94%E7%94%A8[8]-%E5%90%8E%E9%97%A8%E7%AF%87.html) **思路不错，配置文件这些。。。**
+ 2022/03/14 [weblogic下spring bean RCE的一些拓展](https://gv7.me/articles/2021/some-extensions-of-spring-bean-rce-under-weblogic/) **c0ny1师傅的文章一如既往的好**
+ 2022/03/15 [Shiro后渗透拓展面](https://tttang.com/archive/1472/) **扩展了思路agnet dump 获得key！**
+ 2022/03/16 [通过ql发现java gadgets](https://www.synacktiv.com/publications/finding-gadgets-like-its-2022.html) **可以参考文章的思路，sink和source,和中间的链。**
+ 2022/03/20 [使用 Burp 测试基于快速信息集的 Web 应用程序](https://blog.gdssecurity.com/labs/2017/10/10/pentesting-fast-infoset-based-web-applications-with-burp.html) **可能绕过xml**
+ 2022/03/23 [Linux下文件描述符回显构造](http://foreversong.cn/archives/1459) **理论上linux系统都可以通过fd文件描述符去获得回显，不仅仅是java语言，在想能不能有什么办法准确的获得fd(考虑各个因素)**
+ 2022/03/28 [内存Dump数据库密码的补充](https://mp.weixin.qq.com/s?__biz=Mzg2NDM2MTE5Mw==&mid=2247488363&idx=2&sn=cd23ae6069ce67dd1884950e59654440&chksm=ce6bdcedf91c55fb423a02276007c5c964d5ee08f56643fb643fe977bdaf2e82f7e7f130be08&mpshare=1&scene=23&srcid=0328z7pucoel3CnkzthxIP2i&sharer_sharetime=1648427946090&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd) **自己想的一个思路是获得Statement对象的全部方法然后在方法之前hook就可以了，有亿点麻烦。。。**
+ 2022/04/01 [Spring Framework CVE-2022-22965漏洞分析](https://wx.zsxq.com/dweb2/index/group/2212251881)
