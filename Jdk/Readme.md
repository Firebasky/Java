# JDK

jdk>12不能反射修改下面class的成员。
![image](https://user-images.githubusercontent.com/63966847/194300821-dd1bf0bc-b5bd-4680-aa35-49a5d4c8adb4.png)
思路是通过unsafe api去修改Reflection类的成员，赋值为null.
```java

import sun.misc.Unsafe;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;

public class bypass {
    private static Unsafe getUnsafe() {
        Unsafe unsafe = null;
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
        return unsafe;
    }
    public static byte[] readInputStream(InputStream inputStream) {
        byte[] temp = new byte[4096];
        int readOneNum = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            while ((readOneNum = inputStream.read(temp)) != -1) {
                bos.write(temp, 0, readOneNum);
            }
            inputStream.close();
        }catch (Exception e){
        }
        return bos.toByteArray();
    }

    public void bypassReflectionFilter()throws Exception{
        Unsafe unsafe = getUnsafe();
        Class reflectionClass=Class.forName("jdk.internal.reflect.Reflection");
        byte[] classBuffer = readInputStream(reflectionClass.getResourceAsStream("Reflection.class"));
        //定义一个类，但不让类加载器知道它。
        Class reflectionAnonymousClass = unsafe.defineAnonymousClass(reflectionClass,classBuffer,null);

        Field fieldFilterMapField=reflectionAnonymousClass.getDeclaredField("fieldFilterMap");
        //不需要
        //Field methodFilterMapField=reflectionAnonymousClass.getDeclaredField("methodFilterMap");

        if(fieldFilterMapField.getType().isAssignableFrom(HashMap.class)){
            unsafe.putObject(reflectionClass,unsafe.staticFieldOffset(fieldFilterMapField),new HashMap());
        }
        //if(methodFilterMapField.getType().isAssignableFrom(HashMap.class)){
        //  unsafe.putObject(reflectionClass,unsafe.staticFieldOffset(methodFilterMapField),new HashMap());
        //}
    }
    public static void main(String[] args) throws Exception{
        //绕过Java 反射过滤获取ClassLoader私有字段
        //ClassLoader.class.getDeclaredField("parent");//在之前反射会报错
        new bypass().bypassReflectionFilter();
        ClassLoader.class.getDeclaredField("parent");//在之后反射可以bypass
    }
}
```
参考:https://github.com/BeichenDream/Kcon2021Code/blob/master/bypassJdk/JdkSecurityBypass.java

jdk>16

jdk17 bypass module

https://www.bennyhuo.com/2021/10/02/Java17-Updates-06-internals/

https://github.com/BeichenDream/Kcon2021Code/blob/master/bypassJdk/JdkSecurityBypass.java

在jdk17使用反序列化的时候发现要报错

```
InvokerTransformer: The method 'newTransformer' on 'class com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl' cannot be accessed
```


![image](https://user-images.githubusercontent.com/63966847/208854101-cfe0eee9-5882-4450-9d82-7092d353e30c.png)

限制了

![image](https://user-images.githubusercontent.com/63966847/208854137-7c56007c-ac54-4490-8f30-2753cc0e52e3.png)


限制了的类https://cr.openjdk.java.net/~mr/jigsaw/jdk8-packages-strongly-encapsulated

## 需要bypass

```
按照提案的说明，被严格限制的这些内部 API 包括：
        
java.* 包下面的部分非 public 类、方法、属性，例如 Classloader 当中的 defineClass 等等。
sun.* 下的所有类及其成员都是内部 API。
绝大多数 com.sun.* 、 jdk.* 、org.* 包下面的类及其成员也是内部 API。
```

**code**

```java

import sun.misc.Unsafe;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * https://cr.openjdk.java.net/~mr/jigsaw/jdk8-packages-strongly-encapsulated
 */
public class BypassModule {
    public static void main(String[] args) throws Exception {
        final ArrayList<Class> classes = new ArrayList<>();
        classes.add(Class.forName("java.lang.reflect.Field"));
        classes.add(Class.forName("java.lang.reflect.Method"));
        Class aClass = Class.forName("com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl");
        classes.add(aClass);
        new BypassModule().bypassModule(classes);
        aClass.newInstance();
    }

    public void bypassModule(ArrayList<Class> classes){
        try {
            Unsafe unsafe = getUnsafe();
            Class currentClass = this.getClass();
            try {
                Method getModuleMethod = getMethod(Class.class, "getModule", new Class[0]);
                if (getModuleMethod != null) {
                    for (Class aClass : classes) {
                        Object targetModule = getModuleMethod.invoke(aClass, new Object[]{});
                        unsafe.getAndSetObject(currentClass, unsafe.objectFieldOffset(Class.class.getDeclaredField("module")), targetModule);
                    }
                }
            }catch (Exception e) {
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Method getMethod(Class clazz,String methodName,Class[] params) {
        Method method = null;
        while (clazz!=null){
            try {
                method = clazz.getDeclaredMethod(methodName,params);
                break;
            }catch (NoSuchMethodException e){
                clazz = clazz.getSuperclass();
            }
        }
        return method;
    }

    private static Unsafe getUnsafe() {
        Unsafe unsafe = null;
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
        return unsafe;
    }
}
```

