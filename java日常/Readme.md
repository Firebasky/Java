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
+ 2021/11/02 [关于Java 中 XXE 的利用限制探究](https://www.freebuf.com/articles/web/284225.html) **使用http外带数据不能有换行，使用ftp可以解决，但是ftp在java 8u131修复了这个漏洞 CVE-2017-3533** [代码修复](https://github.com/openjdk/jdk8u-dev/commit/644ddd7722bea502f029378c22d51b6eb66f8c25)
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
+ 2022/04/02 [关于Spring framework rce（CVE-2022-22965）的一些问题思考](https://mp.weixin.qq.com/s?__biz=MzkzNjMxNDM0Mg==&mid=2247484213&idx=1&sn=f975b31111e3029fa92b098ffa5c7933&chksm=c2a1d7bcf5d65eaaf5b3ef13ec9147b77866511f07ef04b33c5d8e6897e93121b2fbe1c86efd&mpshare=1&scene=23&srcid=0402nGSU5SdMSCyU5rXBMkvD&sharer_sharetime=1648875678204&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd) **通俗易懂**
+ 2022/04/05 [JAVA RMI 反序列化流程原理分析](https://xz.aliyun.com/t/2223) **rmi攻击的回显思路，通过异常回显**
+ 2022/04/07 [（先知首发）从Jenkins RCE看Groovy代码注入](https://www.mi1k7ea.com/2020/08/26/%E4%BB%8EJenkins-RCE%E7%9C%8BGroovy%E4%BB%A3%E7%A0%81%E6%B3%A8%E5%85%A5)
+ 2022/04/09 [Spring Boot拦截器(Interceptor)详解](https://juejin.cn/post/6844904020675559432) **注入interceptor的基础**
+ 2022/04/23 [红队第4篇 | Shiro Padding Oracle无key的艰难实战利用过程](https://mp.weixin.qq.com/s?__biz=MzU4NTY4MDEzMw==&mid=2247492569&idx=1&sn=a3ff25d6fb277763785213b18885b422&chksm=fd8477b3caf3fea59b39ab27229e214e5a4038dbc6925b5ccafea9481bc8952313b404f84a11&mpshare=1&scene=23&srcid=0423xysf3wTzCs7HWGlyakZM&sharer_sharetime=1650694544259&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd) 
+ 2022/04/30 [【第2周】编写Poc小Tips之无损检测](https://mp.weixin.qq.com/s?__biz=Mzg3NjA4MTQ1NQ==&mid=2247483702&idx=1&sn=82567b235e7f3526e113ae1fa51cc30e&chksm=cf36f976f84170609633cb61e07787548271cd6da263043bb3e6b0333397045cef0ae259561d&mpshare=1&scene=23&srcid=04302wIyYWv0SSE4RbsbKHUi&sharer_sharetime=1651253127103&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd) **思路很好的**
+ 2022/04/20 [红蓝必备 你需要了解的weblogic攻击手法](https://mp.weixin.qq.com/s/tgQO9ILHudfkkOzeahICTg) **比较牛皮了**
+ 2022/04/30 [Hessian2黑名单](https://github.dev/sofastack/sofa-hessian/blob/master/src/main/resources/security/serialize.blacklist) **通过已有的黑名单快速挖掘利用的危险类**
+ 2022/05/02 [不同的类加载器加载的类不是同一个类](https://blog.csdn.net/csdnlijingran/article/details/89226943)
+ 2022/05/03 [使用 CVE-2020-2555 攻击 Shiro](https://xz.aliyun.com/t/9343) **可能之后自己会遇到。**
+ 2022/05/03 [快速探测目标防火墙出网端口的工具化实现](https://xz.aliyun.com/t/10677)  **小工具感觉有时候不错**
+ 2022/05/07 [红蓝必备 你需要了解的weblogic攻击手法](https://mp.weixin.qq.com/s/tgQO9ILHudfkkOzeahICTg) **检测路径非常不错**
+ 2022/05/14 [入侵检测挑战赛第二期-XXE注入wp](https://mp.weixin.qq.com/s?__biz=MzIwOTMzMzY0Ng==&mid=2247487049&idx=1&sn=fba13912ae3c490b588c6fb0231055c4&chksm=977432a8a003bbbec5421ba14f9fe5480972f9c8ef2ad7f9dea4df4be7d987de5552157a29f3&mpshare=1&scene=23&srcid=0514JguMX8NCJBwchxH7ZZMG&sharer_sharetime=1652501963417&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd) **分块传输**
+ 2022/05/16 [红队第9篇：给任意java程序挂Socks5代理方法](https://mp.weixin.qq.com/s?__biz=MzU0MjUxNjgyOQ==&mid=2247489836&idx=1&sn=ac9f3ea11dcae5f9a819bdad6c2b0440&chksm=fb182a1ecc6fa308837e69c8420996a1dc5b8b0ecd6dc4fec91b88facd65fc13a0b7da5022d6&mpshare=1&scene=23&srcid=0516lp7Qgg05Zcrb9rdmPY6g&sharer_sharetime=1652630865336&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd) **自己真实遇到的问题**
+ 2022/05/16 [DNS记录类型介绍(A记录、MX记录、NS记录等)](https://developer.aliyun.com/article/331012)
+ 2022/05/17 [socks5 代理和 http 代理有什么区别](https://www.wangan.com/wenda/2272)
+ 2022/05/17 [CobaltStrike二次开发](https://www.geekby.site/2020/12/cs%E4%BA%8C%E6%AC%A1%E5%BC%80%E5%8F%91) **大哥说适合基本上全部的二次开发的使用**
+ 2022/05/20 [struts2绕过waf读写文件及另类方式执行命令](https://mp.weixin.qq.com/s/outtxUANOa406ErGleWjtQ) **说不定之后会遇到。**
+ 2022/05/30 [Shiro反序列化漏洞笔记五（对抗篇）](http://changxia3.com/2022/05/09/Shiro%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E6%BC%8F%E6%B4%9E%E7%AC%94%E8%AE%B0%E4%BA%94%EF%BC%88%E5%AF%B9%E6%8A%97%E7%AF%87%EF%BC%89/#0x1-%E5%89%8D%E8%A8%80) **里面很多trick 的bypass**
+ 2022/06/05 [精简JRE,打造无依赖的Java-ShellCode-Loader](https://mp.weixin.qq.com/s?__biz=Mzg2MTc1NDAxMA==&mid=2247483848&idx=1&sn=03ea03031d7f6f19c7848f3bb60267a3&chksm=ce13063df9648f2bfdc5dd39b230ba400af7fad8f9b87b292646e862b2c41bd3db2c34341443&mpshare=1&scene=23&srcid=0605Twg54SwL9UVJVuW0U9dE&sharer_sharetime=1654430144972&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd) **感觉不错 减少了执行java的成本**
+ 2022/06/06 [CVE-2020-7961 Liferay Portal 复现分析](https://www.programminghunter.com/article/5340663689/) 
+ 2022/06/12 [Identity Security Authentication Vulnerability](http://noahblog.360.cn/an-quan-ren-zheng-xiang-guan-lou-dong-wa-jue/) **权限绕过认证非常不错**
+ 2022/06/12 [Blackhat 2021 议题详细分析—— FastJson 反序列化漏洞及在区块链应用中的渗透利用](http://noahblog.360.cn/blackhat-2021yi-ti-xiang-xi-fen-xi-fastjsonfan-xu-lie-hua-lou-dong-ji-zai-qu-kuai-lian-ying-yong-zhong-de-shen-tou-li-yong-2/) **扩大了利用**
+ 2022/06/18 [Java中的任意文件上传技巧](https://pyn3rd.github.io/2022/05/07/Arbitrary-File-Upload-Tricks-In-Java/) **bypass waf 文件上传**
+ 2022/06/22 [关于Tomcat中的三个Context的理解](https://yzddmr6.com/posts/tomcat-context/)
+ 2022/06/24 [利用tomcat自动部署机制getshell](https://novysodope.github.io/2022/06/01/82/) **tocmat 文件上传war目录穿越到webapps目录 getshell**
+ 2022/06/24 [记一次Spring Devtools反序列化利用](https://xz.aliyun.com/t/8349) **非常不错而且居然是2020年的知识**
+ 2022/06/25 [CVE-2022-22978 Spring Security RegexRequestMatcher 认证绕过及转发流程分析](https://xz.aliyun.com/t/11473) **对认证过后spring分发器的分析不错,自己之前就遇到了404的问题**
+ 2022/06/25 [【新手入门系列】 一步一步教你漏洞挖掘之如何在半黑盒模式下挖掘RCE漏洞](https://mp.weixin.qq.com/s/nusGsstudrQt2dwZxHXKgg) **客服端漏洞挖掘。。**
+ 2022/06/27 [Beanshell未授权利用简析](https://www.kitsch.live/2021/09/22/beanshell%e6%9c%aa%e6%8e%88%e6%9d%83%e5%88%a9%e7%94%a8%e7%ae%80%e6%9e%90/) **其他绕过方法**
+ 2022/06/27 [漏洞检测的那些事儿](https://paper.seebug.org/9/) **漏洞检测相关的知识**
+ 2022/07/02 [记一次无文件Webshell攻击分析](https://changxia3.com/2021/07/13/%E8%AE%B0%E4%B8%80%E6%AC%A1%E6%97%A0%E6%96%87%E4%BB%B6Webshell%E6%94%BB%E5%87%BB%E5%88%86%E6%9E%90/)
+ 2022/07/03 [第16篇：Weblogic 2019-2729反序列化漏洞绕防护拿权限的实战过程](https://mp.weixin.qq.com/s?__biz=MzkzMjI1NjI3Ng==&mid=2247484303&idx=1&sn=58cbb4d7f63b9276bb89eeac286d174c&chksm=c25fccf4f52845e241256c2f425003b73b6061b3d1964dcd4a184a2cda1b4d8761098227e6de&mpshare=1&scene=23&srcid=0703XRThsRmunAKy5fSIYQKh&sharer_sharetime=1656786411917&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd) **其中的获取weblogic路径不错**
+ 2022/07/18 [java~通过ClassLoader动态加载类,实现简单的热部署](https://icode.best/i/88333747185426)  [java利用classloader实现热部署](https://blog.csdn.net/chaofanwei2/article/details/51298818)
+ 2022/08/15 [玄武盾的几种绕过姿势](https://mp.weixin.qq.com/s/blPSDeuzQxwbjfdvZFlWQg) **里面的编码有点意思**
+ 2022/08/16 [weblogic“伪随机”目录生成算法探究](https://gv7.me/articles/2019/weblogic-pseudo-random-dir-generation-algorithm-exploration/) **比较细节**
+ 2022/08/20 [Java安全攻防之从wsProxy到AbstractTranslet](https://mp.weixin.qq.com/s/HuQV6PNBCW4qSKQVQg8ifA) **学习了反序列化代码执行不需要继承AbstractTranslet**
+ 2022/08/22 [ysoserial分析之Jython1利用链](https://mp.weixin.qq.com/s/QNrwrv5leC0FN3H4RL6oEg) **等待完善命令执行。。。**
+ 2022/09/01 [手把手带你挖掘spring-cloud-gateway新链](https://forum.butian.net/share/1410) **学到了Idea 快捷键Ctrl + Alt + H来查看调用的层次 比较清楚**
+ 2022/09/02 [代码审计之洞态IAST 0day挖掘](https://mp.weixin.qq.com/s/LDBwhQYiiZ8heOiJl83JFQ) **感觉一般**
+ 2022/09/10 [Groovy Template Engine Exploitation – Notes from a real case scenario](https://security.humanativaspa.it/groovy-template-engine-exploitation-notes-from-a-real-case-scenario/) **Groovy Template Engine Exploitation 说不定以后遇到**
+ 2022/09/10 [Xalan-J XSLT整数截断漏洞利用构造(CVE-2022-34169)](http://noahblog.360.cn/xalan-j-integer-truncation-reproduce-cve-2022-34169/) **好牛皮 但是看不懂**
+ 2022/09/11 [通过动态链接库绕过反病毒软件Hook - Break JVM](https://mp.weixin.qq.com/s?__biz=MzA4NzQwNzY3OQ==&mid=2247483882&idx=1&sn=011c3f231d38d899bcf8bf21010616a0&chksm=9038acbaa74f25acd2983131a4b309424985fde3538cd8a93409336e317a4393350f75c7e334&scene=132#wechat_redirect)
+ 2022/09/16 [研究 XSS 到 RCE 缺陷的开源应用程序](https://swarm.ptsecurity.com/researching-open-source-apps-for-xss-to-rce-flaws/) **xss->rce**
+ 2022/09/17 [JAVA反序列化中 RMI JRMP 以及JNDI多种利用方式详解](https://mp.weixin.qq.com/s/tAPCzt6Saq5q7W0P7kBdJg)
+ 2022/09/19 [冰蝎v4.0传输协议详解](https://mp.weixin.qq.com/s/EwY8if6ed_hZ3nQBiC3o7A)
+ 2022/09/20 [CVE-2022-26377: Apache HTTPd AJP Request Smuggling](http://noahblog.360.cn/apache-httpd-ajp-request-smuggling/) **好牛皮啊**
+ 2022/09/23 [cve-2010-4452 codebase 和code标签属性未检测同源策略导致任意代码执行漏洞](https://blog.csdn.net/instruder/article/details/7730905) **学习**
+ 2022/09/23 [Java运行代码的效率怎么提高](https://blog.csdn.net/qf2019/article/details/109351547)  [JAVA实现大文件多线程下载，提速30倍](https://blog.csdn.net/qq_19749625/article/details/120009749) **java效率提高**
+ 2022/09/26 [一次老版本jboss反序列化漏洞的利用分析](https://mp.weixin.qq.com/s/7oyRYlNUJ4neAdDRkxL2Rg) **低版本的jboss 重挖，不错**
+ 2022/09/26 [CS反制之批量伪装上线](https://forum.butian.net/share/708) **思路不错。**
+ 2022/09/26 [浅谈JFinal的DenyAccessJsp绕过](https://forum.butian.net/share/1899) **路径绕过url编码**
+ 2022/09/29 [TCTF 2019 线上赛 web 题 writeup](https://www.k0rz3n.com/2019/04/04/TCTF%202019%20%E7%BA%BF%E4%B8%8A%E8%B5%9B%20web%20%E9%A2%98%20writeup/) [在Java EE Servers环境下利用Jolokia Agent漏洞](https://www.freebuf.com/vuls/166695.html)
+ 2022/9/29  [从JDBC attack到detectCustomCollations利用范围扩展](https://xz.aliyun.com/t/11610) **扩展思路**
+ 2022/10/04 [为什么预编译可以防止sql注入](https://m.php.cn/faq/418626.html) **预编译可以防止sql注入的原因：允许数据库做参数化查询。在使用参数化查询的情况下，数据库不会将参数的内容视为SQL执行的一部分，而是作为一个字段的属性值来处理，这样就算参数中包含破环性语句（or ‘1=1’）也不会被执行。**
+ 2022/10/05 [JavaMelody 漏洞](https://mp.weixin.qq.com/s?__biz=MzU1OTU3ODk0OQ==&mid=2247484382&idx=1&sn=bb8b97a74d99a5c361db431898a953d9&chksm=fc1469f4cb63e0e261e53faa8728ff57c72f5694034dda028d08904fe775fa1654f82cb690aa&scene=178&cur_album_id=2327370482917965825#rd)
+ 2022/10/05 [一种新的Tomcat内存马 - Upgrade内存马](https://tttang.com/archive/1709)
+ 2022/10/06 [HSQLDB 安全测试指南](https://b1ue.cn/archives/458.html)
+ 2022/10/06 [Linux terminal/tty/pty and shell](https://kangxiaoning.github.io/post/2021/05/linux-terminal-tty-pty-and-shell/)
+ 2022/10/08 [利用ModSecurity内置实现第一代 rasp](https://mp.weixin.qq.com/s?__biz=Mzg3ODY3MzcwMQ==&mid=2247489448&idx=1&sn=3a64455cb703152d9f69b3fa3657f7f7&chksm=cf117de2f866f4f46b088ca106911db77ef7e16b3408ef5c3f3d893c99432227f38ed0969367&mpshare=1&scene=23&srcid=1008ouxJsQWdvxgKPMzYC9x0&sharer_sharetime=1665193299451&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd)
+ 2022/10/08 [WAF bypasses via 0days](https://terjanq.medium.com/waf-bypasses-via-0days-d4ef1f212ec)
+ 2022/10/11 [记一次 Tomcat 部署 WAR 包拦截绕过的深究](https://www.ch1ng.com/blog/264.html) **文件上传也可以绕过**
+ 2022/10/14 [【技术原创】Java利用技巧——AntSword-JSP-Template的优化](https://mp.weixin.qq.com/s?__biz=MzI0MDY1MDU4MQ==&mid=2247552091&idx=1&sn=061377d83ca103c5d0ddbe36e914d2e8&chksm=e915dc61de6255770aee47e7bdf1d50bc6814a99def28b64ed63164faa547c08e28f7c1864c9&mpshare=1&scene=23&srcid=10145tBlCMybIMqBL3KthNAx&sharer_sharetime=1665748971719&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd) **可能之后有用**
+ 2022/10/15 [bcel环境下打入springboot内存马](https://mp.weixin.qq.com/s?__biz=MzU5MTExMjYwMA==&mid=2247485492&idx=1&sn=82fd393c7fc33417bff5d8cfa81b1451&chksm=fe32b8c3c94531d520d3fe4b0349b982fab83da2f6273799b68aa48f7bbb16700a642034c15e&mpshare=1&scene=23&srcid=1014Db7SCSD03rrslhpasxqf&sharer_sharetime=1665743334925&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd) **解决方法太麻烦,可以直接写一个loader里面加载代码。就不需要通过bcel加载了。(因为使用bcel加载的时候会存在class not find,因为加载器是bcel.)**
+ 2022/10/15 [Padding Oracle原理深度解析&CBC字节翻转攻击原理解析](https://mp.weixin.qq.com/s/OtGw-rALwpBkERfvqdZ4kQ?utm_source=qq&utm_medium=social&utm_oi=1165421494795706368)
+ 2022/10/16 [Shiro Padding Oracle攻击分析](https://www.cnblogs.com/wh4am1/p/12761959.html) **重新学习**
+ 2022/10/16 [JSP文件无依赖加载shellcode分析](https://cangqingzhe.github.io/2021/10/21/JSP%E6%96%87%E4%BB%B6%E6%97%A0%E4%BE%9D%E8%B5%96%E5%8A%A0%E8%BD%BDshellcode%E5%88%86%E6%9E%90/) **由于这种方式是通过Tomcat服务的进程上线的,exit的话比较困难**
+ 2022/10/17 [负载均衡踩坑记](https://cangqingzhe.github.io/2021/09/24/%E8%B4%9F%E8%BD%BD%E5%9D%87%E8%A1%A1%E8%B8%A9%E5%9D%91%E8%AE%B0/)
+ 2022/10/17 [最新CS RCE（CVE-2022-39197）复现心得分享](https://mp.weixin.qq.com/s/89wXyPaSn3TYn4pmVdr-Mw)
+ 2022/10/17 [RMI攻击Registry的两种方式](https://mp.weixin.qq.com/s?__biz=MjM5NjA0NjgyMA==&mid=2651199558&idx=2&sn=f92be210fda6dcda351912e5819191e5&chksm=bd1d8acd8a6a03db3b62ba72b2a3b931ab99cf74dbacde501c0d615a8eb894c50d96405b3b43&mpshare=1&scene=23&srcid=10175X0cCc5JMI6fbq1VPYi6&sharer_sharetime=1666017207856&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd)
+ 2022/10/19 [Apache Spark UI 命令注入漏洞 CVE-2022-33891](https://mp.weixin.qq.com/mp/appmsgalbum?__biz=MjM5MTYxNjQxOA==&action=getalbum&album_id=2619537533131227139&scene=173&from_msgid=2652892336&from_itemidx=1&count=3&nolastread=1#wechat_redirect)
+ 2022/10/20 [如何更加精确的检测Tomcat AJP文件包含漏洞(CVE-2020-1938)](https://gv7.me/articles/2020/how-to-detect-tomcat-ajp-lfi-more-accurately/) **ajp的利用**
+ 2022/10/25 [Python PIP自解压的命令执行](https://mp.weixin.qq.com/s/xFY6VYzrA4RryH1agC8zUw)  **包管理工具的命令执行**  [node npm 中的preinstall 命令执行](https://bytedance.feishu.cn/docx/doxcnWmtkIItrGokckfo1puBtCh)
+ 2022/10/26 [这是我见过最复杂的URL了](https://cn-sec.com/archives/1372213.html)
+ 2022/10/27 [【技术干货】CVE-2022-34916 Apache Flume 远程代码执行漏洞分析](https://mp.weixin.qq.com/s/zS2TBfBsK1gzkLxs5u3GmQ)
+ 2022/10/30 [Beware the Nashorn: ClassFilter gotchas](https://mbechler.github.io/2019/03/02/Beware-the-Nashorn/)
+ 2022/11/01 [红队第10篇：coldfusion反序列化过waf改exp拿靶标的艰难过程](https://www.moonsec.com/5362.html)
+ 2022/11/03 [hw打点之某创中间件](https://mp.weixin.qq.com/s/D-LuR33WKlzRjo0s75TFSQ)
+ 2022/11/06 [看我如何再一次駭進 Facebook，一個在 MobileIron MDM 上的遠端程式碼執行漏洞!](https://devco.re/blog/2020/09/12/how-I-hacked-Facebook-again-unauthenticated-RCE-on-MobileIron-MDM/) 好np啊
+ 2022/11/06 [How I Chained 4 Bugs(Features?) into RCE on Amazon Collaboration System](https://blog.orange.tw/2018/08/how-i-chained-4-bugs-features-into-rce-on-amazon.html) **真的np**
+ 2022/11/08 [常见安全工具的扫描流量特征分析与检测](https://mp.weixin.qq.com/s/JyFXNtIwludyDBNQc0-oKw)
+ 2022/11/09 [Bypass Authentication BurpSuit 插件](https://mp.weixin.qq.com/s?__biz=Mzg5OTQ3NzA2MQ==&mid=2247485029&idx=1&sn=c1a45885d1037f902f172da08d84341d&chksm=c053fd4ff72474590add9334e497b5c08895e564d3a913cf7b20c9a707d204cca47ed160cca9&mpshare=1&scene=23&srcid=1109NLqGHLO9SdPBfzlUhLUT&sharer_sharetime=1667932033444&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd) **bp 插件**
+ 2022/11/09 [Kcon议题分析《高级攻防下的WebShell》分析 —— Java Agent 通用内存马](https://mp.weixin.qq.com/s?__biz=Mzg5OTQ3NzA2MQ==&mid=2247484929&idx=1&sn=39ed4ec26af5a3d40ccefbf340bd295d&chksm=c053fd2bf724743d0a4cf2e5f995c631a33cba1262dfa7cd8bd09966fd71b5f867e6212233c9&mpshare=1&scene=23&srcid=1109ne3bmFyb2NFKi1ISzS1y&sharer_sharetime=1667931921863&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd)
+ 2022/11/10 [Druid远程代码执行漏洞分析（CVE-2021-25646）](https://xz.aliyun.com/t/9229) **简单的说就是使用@JacksonInject注解的时候，可以通过""去匹配参数从而控制值。该漏洞是控制了config 为 true.最后漏洞的利用点就是利用config为true之后绕过了对于config的检查**
+ 2022/11/11 [从SPI机制到JDBC后门实现](https://mp.weixin.qq.com/s/vhKWEz9hwhdinm4TEtLUqw) 
+ 2022/11/11 [一起通过Navicat进行供应链攻击的样本分析](https://mp.weixin.qq.com/s?__biz=MzU0MDg1NjMyNQ==&mid=2247485330&idx=1&sn=ad68b1301c9289bc9ebc39640e03315e&chksm=fb339ef8cc4417ee9a047850e999f7db51ebe601b5c6a37cf247f4f17eac1481ec5147f9b5b2&mpshare=1&scene=1&srcid=11117c2tOWqevk7sw3mH7cHO&sharer_sharetime=1668165218192&sharer_shareid=33fdea7abe6be586e131951d667ccd06&key=13199a1408fc416798bb4b4f4fb6a44ff1bd702c2e1d10d0b2b72bfe4b80d53346ab688dc13c8f6da2eb8afdc49c2508f520a4234972ec3cce0a612e7c7d25aad3b5c647e77a6040bc0181802fd86df19f36bc5a21dd8a4702aab2ed6d4a6d59fcdc1c4e6d83b07ffcbcf26f78f9f2122887dee5a5f5d5c39d03a1e27b9eca2c&ascene=1&uin=ODYyODE3NzI1&devicetype=Windows+10+x64&version=6308001f&lang=zh_CN&exportkey=n_ChQIAhIQZGY7rBoHsLsIbkHsdPBgBBLvAQIE97dBBAEAAAAAAPWyNDbyQpAAAAAOpnltbLcz9gKNyK89dVj0qe2fqlflmmc8D1eybpB9UjyEVXZxzTjhUQnmaod69dFsw2ig6d2B53zT%2FWgGY2yFadFDdL%2BDBq5jySJDOnOj4H4s5cVqKESUbZ7IUfIsfvyrM4JN6HLsUL1qF1%2BSYWIe8bD1T%2FG9Eye5Qendcd%2FZpmWeJcq7ua%2BvKZrSqWy5TnM6qGrZ9reOvJeBaQo3ZcSk%2BtxapkLHCSRkAejizHNRMYFVlCSSpBP4A6IflbjQ1kX8xDv5oLFHaz3PbQLish3WWGvAqV4ONDWG&acctmode=0&pass_ticket=uGXE0Z4fPCmC9suZxdId189%2FNtwCT5VyAktjMGr70tXhWj2mXEslo4cG4WozS3Vz&wx_header=0&fontgear=2) **好np**
+ 2022/11/19 [命令注入执行](https://0xn3va.gitbook.io/cheat-sheets/web-application/command-injection)
+ 2022/11/19 [Hessian 序列化、反序列化](https://mp.weixin.qq.com/s/icYs7VjPRytt6zgXja9V-w) **学习**
+ 2022/11/20 [Remote Command Execution in a Bank Server](https://medium.com/@win3zz/remote-command-execution-in-a-bank-server-b213f9f42afe)
+ 2022/11/23 [ZK框架权限绕过导致R1Soft Server Backup Manager RCE并接管Agent](http://tttang.com/archive/1833) **forward转发 bypass 权限操作**
+ 2022/11/27 [burp指纹修改](https://mp.weixin.qq.com/s?__biz=MzU1NTQ5MDEwNw==&mid=2247484690&idx=1&sn=5b2251069f9bcc98c340278207825c66&chksm=fbd2cb46cca542505b3f49c8ba7f609fab9d5ca6a43b6ebdc61cf67a3f725406b998b56fdbdc&mpshare=1&scene=23&srcid=1126mmkxPLOblhlehRFdhOY7&sharer_sharetime=1669485801645&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd)
+ 2022/11/30 [关于HackerOne上Grafana、jolokia、Flink攻击手法的学习](https://mp.weixin.qq.com/s/iQlLvF8LHzJvL8ofE2YvKA) **flink 寻找main 有意思**
+ 2022/11/30 [内存马的攻防博弈之旅之gRPC内存马](https://mp.weixin.qq.com/s/osuoinwCpOwNM4WoI6SOnQ) **可能之后可以用**
+ 2022/12/02 [一次失败的定点漏洞挖掘之代码审计宜信Davinci](https://www.cnblogs.com/r00tuser/p/13265435.html) **遇到了 但是不出网**
+ 2022/12/05 [宝塔后渗透-添加用户|反弹shell](https://mp.weixin.qq.com/s/2o_H66BMqy3Ft3-5ERlKpQ) **后渗透比较重要**
+ 2022/12/05 [Nacos Client Yaml反序列化漏洞分析](https://xz.aliyun.com/t/10355) [Nacos 未授权远程代码执行漏洞通告](https://mp.weixin.qq.com/s/Zpa3af43XZECglYMbNRk8g) **add user有用**
+ 2022/12/08 [CVE-2022-44262](https://github.com/ff4j/ff4j/issues/624) **需要找到构造方法并且是string类型的利用**
+ 2022/12/08 [RCE on Apache Struts 2.5.30](https://mc0wn.blogspot.com/2022/11/rce-on-apache-struts-2530.html) **np s2的利用**
+ 2022/12/09 [那些年一起打过的CTF - Laravel 任意用户登陆Tricks分析](https://www.yulegeyu.com/2021/09/22/%E9%82%A3%E4%BA%9B%E5%B9%B4%E4%B8%80%E8%B5%B7%E6%89%93%E8%BF%87%E7%9A%84CTF-Laravel-%E4%BB%BB%E6%84%8F%E7%94%A8%E6%88%B7%E7%99%BB%E9%99%86Tricks%E5%88%86%E6%9E%90/)  **不愧是是雨神，yyds**
+ 2022/12/09 [老版本Fastjson 的一些不出网利用](https://www.yulegeyu.com/2022/11/12/Java%E5%AE%89%E5%85%A8%E6%94%BB%E9%98%B2%E4%B9%8B%E8%80%81%E7%89%88%E6%9C%ACFastjson-%E7%9A%84%E4%B8%80%E4%BA%9B%E4%B8%8D%E5%87%BA%E7%BD%91%E5%88%A9%E7%94%A8/) ***yyds*
+ 2022/12/09 [浅谈XXE防御(Java)](https://mp.weixin.qq.com/s/BSq77W0u0-O2elKZTJQNOQ)
+ 2022/12/14 [js-on-security-off-abusing-json-based-sql-to-bypass-waf](https://claroty.com/team82/research/js-on-security-off-abusing-json-based-sql-to-bypass-waf) 
+ 2022/12/17 [java.exe和javaw.exe区别](https://blog.csdn.net/xtho62/article/details/114085591) 在bp启动的时候看到了
+ 2022/12/17 [Weakness in Java TLS Host Verification](https://blog.h3xstream.com/2020/10/weakness-in-java-tls-host-verification.html) **字符编码绕过**
+ 2022/12/18 [Java使用 try catch会影响性能？](https://mp.weixin.qq.com/s/kkEGvMwaG6J1WrD_DWRRzg) **不会**
+ 2022/12/22 [How I was able to steal users credentials via Swagger UI DOM-XSS](https://medium.com/@M0X0101/how-i-was-able-to-steal-users-credentials-via-swagger-ui-dom-xss-e84255eb8c96)
+ 2022/12/22 [浅析自动绑定漏洞](https://xz.aliyun.com/t/128) [浅析自动绑定漏洞之Spring MVC](https://www.mi1k7ea.com/2020/02/12/%E6%B5%85%E6%9E%90%E8%87%AA%E5%8A%A8%E7%BB%91%E5%AE%9A%E6%BC%8F%E6%B4%9E%E4%B9%8BSpring-MVC/) [Spring MVC Autobinding漏洞实例初窥](https://xz.aliyun.com/t/1089) [Autobinding](https://github.com/Cryin/JavaID/blob/master/JAVA%E5%AE%89%E5%85%A8%E7%BC%96%E7%A0%81%E4%B8%8E%E4%BB%A3%E7%A0%81%E5%AE%A1%E8%AE%A1.md) **Autobinding漏洞，代码审计的时候可以关注@SessionAttributes,@ModelAttribute注解**
+ 2022/12/22 [渗透必备！文件读取漏洞的后利用姿势](https://mp.weixin.qq.com/s?__biz=MzUyMTA0MjQ4NA==&mid=2247539336&idx=1&sn=81cd9e896db0dc9febd9f44bfbb1c69c&chksm=f9e335d3ce94bcc5894e9a6309ec200b8761d8eaef611b07c21fffe01459c71b1f4b686486a0&mpshare=1&scene=23&srcid=1222fVGVLCHXZOEVl7ECdKpe&sharer_sharetime=1671640052561&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd) **/var/lib/mlocate/mlocate.db 文件比较有趣 centos默认有 ubu默认没有.**
+ 2022/12/23 [红队实录系列(三)-WiFi 近源攻击实战](https://mp.weixin.qq.com/s?__biz=MzkzNjM5MDYwNw==&mid=2247483774&idx=1&sn=8808bfa1445f6b516077a1af244b761f&chksm=c29e3bdef5e9b2c89e0b607a08f098fca261228079259472bef46c645d8a83d2e1ed955f9ffe&mpshare=1&scene=23&srcid=1223e1e52DqpkBFnt02jHE7R&sharer_sharetime=1671794034434&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd)
+ 2022/12/23 [漫谈 JEP 290](https://xz.aliyun.com/t/10170) **总结的非常好，在weblogic中启动了全局的过滤器那么如果存在一个cve是jndi,能不能通过ldap打本地反序列化的方法去rce?不能！！！因为ldap打本地反序列化需要有一个gadget虽然weblogic中的gadget非常多但是都被黑名单过滤了又因为是全局过滤器所以在ldap这条路也不能用。除非用jndi......就又一直重复了。**
+ [网络安全14：Struts2框架下Log4j2漏洞检测方法分析与总结](https://mp.weixin.qq.com/s?__biz=MzkzMjI1NjI3Ng==&mid=2247484207&idx=1&sn=285b54a79e48db9a05816cab2e6afc27&chksm=c25fcc54f5284542c1b9abe870e0caa9f958f4da90723bd83292deed215c63c705b7b0bbfaff&mpshare=1&scene=23&srcid=1225r9kGcJN5evUgMo6ecUCC&sharer_sharetime=1671942359949&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd) **自己也find 一些**
+ 2022/12/26 [第27篇：CSRF跨站请求伪造漏洞挖掘及绕过校验方法](https://mp.weixin.qq.com/s?__biz=MzkzMjI1NjI3Ng==&mid=2247484515&idx=1&sn=eacea9e2e1636d27a4d122a8c28ca98d&chksm=c25fcb18f528420ee30ed8d48d76add6423c736408ce50f4723b7b4aa8213e7ad7d400c268ea&cur_album_id=2660130833605132289&scene=190#rd) **了解了解**
