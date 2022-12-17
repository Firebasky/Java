# java 加载链接库的方法

https://tttang.com/archive/1436/

1.System.load

```java
try {
    System.load("D:\\temp\\calc_x64.dll");
}catch (UnsatisfiedLinkError e){
    e.printStackTrace();
}
```

2.Runtime.getRuntime().load

```java
Runtime.getRuntime().load("D:\\temp\\calc_x64.dll");
```

3.com.sun.glass.utils.NativeLibLoader.loadLibrary

```java
com.sun.glass.utils.NativeLibLoader.loadLibrary("\\..\\..\\..\\..\\..\\..\\..\\..\\temp\\calc_x64");
```

有限制

1. 存在于jdk\javafx-src.zip!\com\sun\glass\utils\NativeLibLoader.java，在不同的版本的jdk中javafx并不是都存在的。
2. NativeLibLoader会首先在jdk环境下找文件名，如果需要自定义路径必须使用../的方式进行目录穿越。并且如果是windows的话，只能穿越到JDK所在的盘符的根目录下。举例说明，如果JDK安装在`D:/java/JDK/`下，那么只能穿越到D盘的任意目录下面，比例说穿越到D:/temp/目录下，文件名参数就只能写成**../../../../temp/calc**，文件名还不能跟后缀，不然传入文件名会被变成**calc.dll.dll**。相对而言Linux平台是可以穿越任意目录的。

4.反射模拟底层调用

- 如果模拟ClassLoader加载就会存在两个方案
  - 模拟ClassLoader的loadLibrary和loadLibrary0两个方案。
- 如果模拟NativeLibrary就只存在load方法

**ClassLoader#loadLibrary**

```java
try {
    Class clazz = Class.forName("java.lang.ClassLoader");
    Method method = clazz.getDeclaredMethod("loadLibrary", Class.class, String.class, boolean.class);
    method.setAccessible(true);
    method.invoke(null, clazz, "D:\\temp\\calc_x64.dll", true);
}catch (Exception e){
    e.printStackTrace();
}
```

**NativeLibrary#load**

```java
String file = "D:\\temp\\calc_x64.dll";
Class a = Class.forName("java.lang.ClassLoader$NativeLibrary");
Constructor con = a.getDeclaredConstructor(new Class[]{Class.class,String.class,boolean.class});
con.setAccessible(true);
Object obj = con.newInstance(JDKClassLoaderBypass.class,file,true);
Method method = obj.getClass().getDeclaredMethod("load", String.class, boolean.class);
method.setAccessible(true);
method.invoke(obj, file, false);
```

```java
String file = "D:\\temp\\calc_x64.dll";
Class aClass = Class.forName("sun.misc.Unsafe");
Constructor<?> declaredConstructor = aClass.getDeclaredConstructor();
declaredConstructor.setAccessible(true);
Unsafe unsafe = (Unsafe)declaredConstructor.newInstance();
Object obj =  unsafe.allocateInstance(a);
Method method = obj.getClass().getDeclaredMethod("load", String.class, boolean.class);
method.setAccessible(true);
method.invoke(obj, file, false);
```

