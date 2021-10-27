# Dubbo 

>Apache Dubbo 是伪装的、轻量级的Java RPC 服务框架。[RPC服务](https://www.zhihu.com/question/25536695) 
>[默认反序列化利用之hessian2](https://www.anquanke.com/post/id/197658)

### CVE-2019-17564
>spring (spring-web（5.1.9.RELEASE）) 的httpinvoker 可能存在反序列化漏洞 [docs](https://docs.spring.io/spring-framework/docs/5.1.0.RELEASE/spring-framework-reference/integration.html#remoting-httpinvoker)

http://www.lmxspace.com/2020/02/16/Apache-Dubbo%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E6%BC%8F%E6%B4%9E%EF%BC%88CVE-2019-17564%EF%BC%89/

https://www.mi1k7ea.com/2021/07/03/%E6%B5%85%E6%9E%90Dubbo-HttpInvokerServiceExporter%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E6%BC%8F%E6%B4%9E%EF%BC%88CVE-2019-17564%EF%BC%89/

### CVE-2021-25641
>Dubbo Provider即服务提供方默认使用dubbo协议来进行RPC通信，而dubbo协议默认是使用Hessian2序列化格式进行对象传输的,不过可以通过更改dubbo协议的第三个flag位字节来更改为使用Kryo或FST序列化格式来进行Dubbo Provider反序列化攻击从而绕过针对Hessian2反序列化相关的限制来达到RCE。

https://www.mi1k7ea.com/2021/06/30/%E6%B5%85%E6%9E%90Dubbo-KryoFST%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E6%BC%8F%E6%B4%9E%EF%BC%88CVE-2021-25641%EF%BC%89/

~~可以整理一个fastjson利用gadget~~

### CVE-2021-30179

https://mp.weixin.qq.com/s/vHJpE2fZ8Lne-xFggoQiAg

**个人认为CVE-2021-30179的主要思路就是Apache Dubbo在处理泛类引用时，提供了多种通过反序列化方式得到对象再生成pojo对象的选择。** 三梦师傅说跟这个思路扩大了反序列化挖掘思路

### Dubbo反序列化RCE利用之新拓展面 - Dubbo Rouge攻击客户端

https://xz.aliyun.com/t/7354

**文章中有一点非常强**
![image](https://user-images.githubusercontent.com/63966847/139078049-28694796-bb21-40fe-9e8d-38d96da29ab8.png)
