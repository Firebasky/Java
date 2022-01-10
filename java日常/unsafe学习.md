# unsafe学习

## 获取偏移量方法

```java
public native long objectFieldOffset(Field var1);//获取非静态变量var1的偏移量。
public native long staticFieldOffset(Field var1);//获取静态变量var1的偏移量。
public native int arrayBaseOffset(Class<?> var1);//获取数组var1中的第一个元素的偏移量，即数组的基础地址。
public native Object staticFieldBase(Field var1);//获取静态变量var1的实际地址，配合staticFieldOffset方法使用，可求出变量所在的段地址
public native int arrayIndexScale(Class<?> var1);//获取数组var1的偏移量增量。结合arrayBaseOffset(Class<?> var1)方法就可以求出数组中各个元素的地址。
```



## 操作属性方法

```java
public native Object getObject(Object var1, long var2);//获取var1对象中偏移量为var2的Object对象，该方法可以无视修饰符限制。相同方法有getInt、getLong、getBoolean等。
public native void putObject(Object var1, long var2, Object var4);//将var1对象中偏移量为var2的Object对象的值设为var4，该方法可以无视修饰符限制。相同的方法有putInt、putLong、putBoolean等。
public native Object getObjectVolatile(Object var1, long var2);//功能与getObject(Object var1, long var2)一样，但该方法可以保证读写的可见性和有序性，可以无视修饰符限制。相同的方法有getIntVolatile、getLongVolatile、getBooleanVolatile等。
public native void putObjectVolatile(Object var1, long var2, Object var4);//功能与putObject(Object var1, long var2, Object var4)一样，但该方法可以保证读写的可见性和有序性，可以无视修饰符限制。相同的方法有putIntVolatile、putLongVolatile、putBooleanVolatile等。
public native void putOrderedObject(Object var1, long var2, Object var4);//功能与putObject(Object var1, long var2, Object var4)一样，但该方法可以保证读写的有序性(不保证可见性)，可以无视修饰符限制。相同的方法有putOrderedInt、putOrderedLong等。
```

## 操作内存方法

```java
public native int addressSize();//获取本地指针大小，单位为byte，通常值为4或8。
public native int pageSize();//获取本地内存的页数，该返回值会是2的幂次方。
public native long allocateMemory(long var1);//开辟一块新的内存块，大小为var1(单位为byte)，返回新开辟的内存块地址。
public native long reallocateMemory(long var1, long var3);//将内存地址为var3的内存块大小调整为var1(单位为byte)，返回调整后新的内存块地址。
public native void setMemory(long var2, long var4, byte var6);//从实际地址var2开始将后面的字节都修改为var6，修改大小为var4(通常为0)。
public native void copyMemory(Object var1, long var2, Object var4, long var5, long var7);//从对象var1中偏移量为var2的地址开始复制，复制到var4中偏移量为var5的地址，复制大小为var7。当var1为空时，var2就不是偏移量而是实际地址，当var4为空时，var5就不是偏移量而是实际地址。
public native void freeMemory(long var1);//释放实际地址为var1的内存。
```

## 线程挂起和恢复方法

```java
public native void unpark(Object var1);//将被挂起的线程var1恢复，由于其不安全性，需保证线程var1是存活的。
public native void park(boolean var1, long var2);//当var2等于0时，线程会一直挂起，知道调用unpark方法才能恢复。当var2大于0时，如果var1为false，这时var2为增量时间，即线程在被挂起var2秒后会自动恢复，如果var1为true，这时var2为绝对时间，即线程被挂起后，得到具体的时间var2后才自动恢复。
```

## CAS方法

```java
public final native boolean compareAndSwapObject(Object var1, long var2, Object var4, Object var5);//CAS机制相关操作，对对象var1里偏移量为var2的变量进行CAS修改，var4为期待值，var5为修改值，返回修改结果。相同方法有compareAndSwapInt、compareAndSwapLong。
```

## 类加载方法

```java
public native boolean shouldBeInitialized(Class<?> var1);//判断var1类是否被初始。
public native void ensureClassInitialized(Class<?> var1);//确保var1类已经被初始化。
public native Class<?> defineClass(String var1, byte[] var2, int var3, int var4, ClassLoader var5, ProtectionDomain var6);//定义一个类，用于动态的创建类。var1为类名，var2为类的文件数据字节数组，var3为var2的输入起点，var4为输入长度，var5为加载该类的加载器，var6为保护领域。返回创建后的类。
public native Class<?> defineAnonymousClass(Class<?> var1, byte[] var2, Object[] var3);//用于动态的创建匿名内部类。var1为需创建匿名内部类的类，var2为匿名内部类的文件数据字节数组，var3为修补对象。返回创建后的匿名内部类。
public native Object allocateInstance(Class<?> var1) throws InstantiationException;//创建var1类的实例，但是不会调用var1类的构造方法，如果var1类还没有初始化，则进行初始化。返回创建实例对象。
```

## 内存屏障方法

```java
public native void loadFence();//所有读操作必须在loadFence方法执行前执行完毕。
public native void storeFence();//所有写操作必须在storeFence方法执行前执行完毕。
public native void fullFence();//所有读写操作必须在fullFence方法执行前执行完毕。
```

>https://www.cnblogs.com/gaofei200/p/13951764.html
>
>https://tech.meituan.com/2019/02/14/talk-about-java-magic-class-unsafe.html







