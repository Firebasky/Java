# wsdl 相关

## 恢复成java代码

```
wsimport -keep "test.wsdl" -p com.test -extension

常用参数为:
-d<目录>  - 将生成.class文件。默认参数。
-s<目录> - 将生成.java文件。
-p<生成的新包名> -将生成的类，放于指定的包下，自定义包结构。
(wsdlurl) - http://server:port/service?wsdl，必须的参数。
示例：
C:/> wsimport –s .
C:/> wsimport –s . –p com.sitech.web


注意：-s不能分开，-s后面有个小点，用于指定源代码生成的目录。点即当前目录。
如果使用了-s参数则会在目录下生成两份代码，一份为.class代码。一份为.java代码。
.class代码，可以经过打包以后使用。.java代码可以直接Copy到我们的项目中运行。
```



## 客户端调用

```xml
<dependency>
    <groupId>org.apache.cxf</groupId>
    <artifactId>cxf-rt-frontend-jaxws</artifactId>
    <version>3.4.5</version>
</dependency>

<dependency>
    <groupId>org.apache.cxf</groupId>
    <artifactId>cxf-rt-transports-http</artifactId>
    <version>3.4.5</version>
</dependency>
```

java代码

```java
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.*;

import java.util.Arrays;

/**
 *https://www.programcreek.com/java-api-examples/?api=org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory
 */
public class Main {
    public static void main(String[] args) {
        JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
        Client client = factory.createClient("http://1.116.136.120:58081/admin/service/UserService?wsdl");
        try {
            Object[] objects = client.invoke("sayHello","admin");
            Arrays.stream(objects).forEach(System.out::println);
            client.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## 参考

>https://blog.51cto.com/u_15127638/2751110
>https://blog.csdn.net/qq_32447301/article/details/79204311
>http://www.360doc.com/content/17/0105/20/835902_620335609.shtml
