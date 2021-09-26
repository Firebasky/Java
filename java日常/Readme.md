# java日常

>有一些不知道这么分类就放里面吧

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
