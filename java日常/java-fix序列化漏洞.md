# java fix序列化漏洞

重点看了看java序列化的fix的操作，于是就简单的记录一下。

[github上有一篇文章写的比较全](https://github.com/Cryin/Paper/blob/master/%E6%B5%85%E8%B0%88Java%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E6%BC%8F%E6%B4%9E%E4%BF%AE%E5%A4%8D%E6%96%B9%E6%A1%88.md)这里自己只是实现其中的一个方法，而该方法也是[SerialKiller](https://github.com/ikkisoft/SerialKiller)项目的底层原理。



#### hook ObjectInputStream类的resolveClass方法

>需要继承Java.io.ObjectInputStream实现一个子类，在子类中重写resolveClass方法，以实现在其中通过判断类名来过滤危险类。然后在JavaSerializer类中使用这个子类来读取序列化数据，从而修复漏洞。

demo

```java
package xlh.fix.HookResolveClass;

import java.io.*;

public class HookObjectInputStream extends ObjectInputStream {
    public HookObjectInputStream(InputStream inputStream)
            throws IOException {
        super(inputStream);
    }
    /**
     * 只允许反序列化xlh.serialVersionUID.Address.class
     */
    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException,
            ClassNotFoundException {
        if (!desc.getName().equals(xlh.serialVersionUID.Address.class.getName())) {
            throw new InvalidClassException(
                    "Unauthorized deserialization attempt",
                    desc.getName());
        }
        return super.resolveClass(desc);
    }
}
```

```java
package xlh.fix.HookResolveClass;

import xlh.serialVersionUID.Address;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class test  {
    public static void main (String args[]) {
        try{
            FileInputStream fin = new FileInputStream("d:\\address.ser");//这样的话cc6.ser就不能使用了。
            ObjectInputStream ois = new HookObjectInputStream(fin);
            Address address = (Address) ois.readObject();
            ois.close();
            System.out.println(address);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
```

其他利用fix思路不过多介绍。



突然想到了国赛java题的修复方案，是通过修改关键类中的serialVersionUID，[serialVersionUID](https://www.cnblogs.com/xuxinstyle/p/11394358.html)可以理解为java序列化的标识。只有满足序列化后的serialVersionUID值和序列化前的值一样才可以成功反序列化。不然会报错**InvalidClassException**

[一篇好文章](http://www.code2sec.com/ji-yi-ci-javafan-xu-lie-hua-lou-dong-de-fa-xian-he-xiu-fu.html)



#### 利用思路

介绍了fix，然后在介绍一下漏洞挖掘的思路，只是自己的思路。。。

```java
一个类被加载到jvm中，是还没有进行初始化的，通常情况下可以通过new、newInstance、Class.forName等方法来初始化。同时在初始化的过程中会调用类的静态方法/属性或者构造函数。所以经常有见到类写成如下形式：
public class Test{
    static{
        System.out.println("Hello Test");
    }
}
这种时候通过Class.forName再初始化类的时候，jvm会自动调用其中的静态代码块，并输出。
```

所以如果我们能控制**Class.forName**的值，并且我们重写一个恶意的静态方法就能够成功利用（这里和p师傅文章中的java安全慢谈一样）。

对于new、newInstance进行初始化类的时候会调用其无参的构造函数。至于利用思路，师傅们可以自己去思考。

```java
public class instance {
    static {
        System.out.println("static");
    }
    instance(){
        System.out.println("构造函数");
    }
    public static void main(String[] args) throws Exception{
        instance instance = new instance();
//        instance.class.newInstance();
//        Class.forName("instance");
    }
}
```
