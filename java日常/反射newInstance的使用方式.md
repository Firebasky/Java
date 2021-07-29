# 反射newInstance的使用方式

通过反射创建新的类示例，有两种方式： 
Class.newInstance() 
Constructor.newInstance()

-------------------------------------------------------------------------------------------------------------------------------------

以下对两种调用方式给以比较说明： 
Class.newInstance() **只能够调用无参的构造函数，即默认的构造函数；** 
Class.newInstance() **要求被调用的构造函数是可见的，也即必须是public类型的;** 

-------------------------------------------------------------------------------------------------------------------------------------

Constructor.newInstance() **可以根据传入的参数，调用任意构造构造函数。** 

Constructor.newInstance() **在特定的情况下，可以调用私有的构造函数。** 

```java
Class clazz = Class.forName("java.lang.Runtime");
Constructor m = clazz.getDeclaredConstructor();//获得构造函数
m.setAccessible(true);//设置构造函数为可访问
```

-------------------------------------------------------------------------------------------------------------------------------------

demo

```java
package reflect.newInstance;

public class A {
    public A() {
        System.out.println("A's constructor is called.");
    }

    private A(int a, int b) {
        System.out.println("a:" + a + " b:" + b);
    }
}
```

```java
package reflect.newInstance;

import java.lang.reflect.Constructor;

public class B {
    public static void main(String[] args) {
        B b=new B();
        System.out.println("通过Class.NewInstance()调用私有构造函数:");
        b.newInstanceByClassNewInstance();
        System.out.println("通过Constructor.newInstance()调用私有构造函数:");
        b.newInstanceByConstructorNewInstance();
    }
    /*通过Class.NewInstance()创建新的类示例*/
    private void newInstanceByClassNewInstance(){
        try {/*当前包名为reflect，必须使用全路径*/
            A a=(A)Class.forName("reflect.newInstance.A").newInstance();
        } catch (Exception e) {
            System.out.println("通过Class.NewInstance()调用私有构造函数【失败】");
        }
    }

    /*通过Constructor.newInstance()创建新的类示例*/
    private void newInstanceByConstructorNewInstance(){
        try {/*可以使用相对路径，同一个包中可以不用带包路径*/
            Class c=Class.forName("reflect.newInstance.A");
            /*以下调用无参的、私有构造函数*/
            Constructor c0=c.getDeclaredConstructor();
            c0.setAccessible(true);
            A a0=(A)c0.newInstance();

            /*以下调用带参的、私有构造函数*/
            Constructor c1=c.getDeclaredConstructor(new Class[]{int.class,int.class});
            c1.setAccessible(true);
            A a1=(A)c1.newInstance(new Object[]{5, 6});

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

