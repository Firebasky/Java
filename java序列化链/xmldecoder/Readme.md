# XMLDecoder反序列化漏洞底层

参考的文章已经分析的非常详细了，这里我主要是就是一下最后的执行是怎么样的。

```java
import java.beans.Expression;
public class test {
    public static void main(String[] args)throws Exception {
        String[] strings = new String[]{"cmd.exe","/c","calc"};
        Object var3 = new ProcessBuilder(strings);
        String var4 = "start";
        Object[] var2 = new Object[]{};
        Expression var5 = new Expression(var3, var4, var2);
        var5.getValue();
    }
}
```

并且通过测试可以发现Expression的使用，给出下面的例子。

```java
public class cmd {
    public void Noparameter(){
        System.out.println("无参数调用....");
    }
    public void Parameter(Object[] obj){
        System.out.println("有参数调用....");
    }
}
```

```java
import java.beans.Expression;

public class test1 {
    public static void main(String[] args)throws Exception {
        Object var3 = new cmd();
        String var4 = "Parameter";//Noparameter
        Object[] var2 = new Object[]{"233333"};
        var2 = new Object[]{var2};
        var2 = new Object[]{};
        Expression var5 = new Expression(var3, var4, var2);
        var5.getValue();
    }
}
```

并且给出了一些exp.

[exp1](./exp1.xml)

[exp2](./exp2.xml) **通过实体编码绕过**

[exp3](./exp3.xml)

>参考
>
>https://www.freebuf.com/articles/network/247331.html
