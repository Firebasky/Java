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
