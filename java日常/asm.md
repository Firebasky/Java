# asm

## 分析类

分析类的代码

```java
public class Test {
    public boolean aBoolean = true;

    public void render(){
        System.out.println("hello asm");
    }
}
```

MyClassVisitors类

```java
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class MyClassVisitors extends ClassVisitor {
    public MyClassVisitors(){
        super(ASM5);
    }

    /**
     * 继承关系
     * @param version
     * @param access
     * @param name
     * @param signature
     * @param superName
     * @param interfaces
     */
    public void visit(int version,int access,String name,String signature,String superName,String[] interfaces){
        System.out.println(name + "extends " + superName + "{");
        super.visit(version,access,name,signature,superName,interfaces);
    }

    /**
     * 属性变量
     * @param access
     * @param name
     * @param desc
     * @param signature
     * @param value
     * @return
     */
    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        System.out.println(desc + " " + name);
        return super.visitField(access, name, desc, signature, value);
    }

    /**
     * 方法
     * @param access
     * @param name
     * @param desc
     * @param signature
     * @param exceptions
     * @return
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println(name + " " + desc);
        return super.visitMethod(access, name, desc, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        System.out.println("}");
        super.visitEnd();
    }
}
```

主程序

```java
import jdk.internal.org.objectweb.asm.ClassReader;

/**
 * 通过asm框架分析类
 */
public class AnalysisClass {
    public static void main(String[] args)throws Exception {
        MyClassVisitors myClassVisitors = new MyClassVisitors();
        ClassReader classReader = new ClassReader(Test.class.getName());
        classReader.accept(myClassVisitors,0);
    }
}
```

![image-20220125122358358](https://user-images.githubusercontent.com/63966847/150936112-ab83838c-4f67-4102-9816-ce2afa734559.png)


![image-20220125122420194](https://user-images.githubusercontent.com/63966847/150936121-914e2c3a-6019-4f01-91ed-38c5453cab39.png)


>该部分也是GI的核心。https://xz.aliyun.com/t/10363

## 生成类

>感觉用于webshell中。

```java
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import java.io.FileOutputStream;
import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class WriteClass {
    public static void main(String[] args) throws Exception {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        // java1.8 public修饰 路径 签名 父类 接口
        classWriter.visit(V1_8, ACC_PUBLIC, "com/firebasky/utils/asm/learn/Learn2Test", null, "java/lang/Object", null);
        //public和static修饰 方法名 描述符 签名 异常
        MethodVisitor render = classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC, "render", "()V", null, null);
        //插入一个字段是方法里面插入 操作码 路径 名字 描述符
        render.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        //插入一个ldc
        render.visitLdcInsn("Hello asm!");
        //插入一个方法 操作码 路径 方法名 参数 是否为接口的方法
        render.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        //插入返回值
        render.visitInsn(RETURN);
        //设置栈和局部变量大小
        render.visitMaxs(2, 1);
        //结束
        render.visitEnd();
        classWriter.visitEnd();
        //生成文件
        byte[] bytes = classWriter.toByteArray();
        FileOutputStream outputStream = new FileOutputStream("d://1.class");
        outputStream.write(bytes);
        outputStream.close();
    }
}
```

生成的类

```java
package com.firebasky.utils.asm.learn;

public class Learn2Test {
     public static void render() {
          System.out.println("Hello asm!");
     }
}

```

## 加载或移除类成员

主程序

```java
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;

import java.io.FileOutputStream;

public class Learn3 {
    public static void main(String[] args)throws Exception {
        ClassReader classReader = new ClassReader(Test.class.getName());
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        MyClassVisitor myClassVisitor = new MyClassVisitor(classWriter);
        classReader.accept(myClassVisitor,0);
        byte[] bytes = classWriter.toByteArray();
        FileOutputStream outputStream = new FileOutputStream("d://1.class");
        outputStream.write(bytes);
        outputStream.close();
    }
}
```

MyClassVisitor类

```java
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class MyClassVisitor extends ClassVisitor {

    public MyClassVisitor(ClassWriter classWriter) {
        super(ASM5, classWriter);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        if(name.equals("aBoolean")){
            return null;
        }
        return super.visitField(access, name, desc, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,String[] exceptions) {
        if(name.equals("render")){
            return null;
        }
        return super.visitMethod(access, name, desc, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        super.visitField(ACC_PRIVATE,"name","Ljava/lang/String;",null,null).visitEnd();
        super.visitMethod(ACC_PRIVATE,"name","(Ljava/lang/String;)V",null,null).visitEnd();
        super.visitEnd();
    }
}
```

修改之后的代码

```java
package com.firebasky.utils.asm.learn;

public class Test {
     private String name;

     public Test() {
          this.aBoolean = true;
     }
     private void name(String var1) {
     }
}

```

## 创建对象和数组

```java
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import java.io.FileOutputStream;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class Learn4 {
    public static void main(String[] args)throws Exception {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        methodVisitor.visitTypeInsn(NEW,"java/lang/String");
        methodVisitor.visitLdcInsn("xxx");//常量
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL,"java/lang/String","<init>","(Ljava/lang/String;)V",false);
        //生成文件
        byte[] bytes = classWriter.toByteArray();
        FileOutputStream outputStream = new FileOutputStream("d://1.class");
        outputStream.write(bytes);
        outputStream.close();
    }
}
```

数组

```java
methodVisitor.visitIntInsn(SIPUSH,2);//数组长度
methodVisitor.visitIntInsn(NEWARRAY,T_BYTE);//类型是byte
methodVisitor.visitInsn(DUP);//压
methodVisitor.visitIntInsn(SIPUSH,0);//插入数组0位置
methodVisitor.visitIntInsn(SIPUSH,1);
methodVisitor.visitInsn(AASTORE);//保存
```

## 字符串混淆

就是在写入的时候做一个转换。

```java
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class Learn5 {
    public static void main(String[] args) throws IOException {
        ClassReader classReader = new ClassReader(Test.class.getName());
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        MyClassVisitor1 myClassVisitor = new MyClassVisitor1(classWriter);
        classReader.accept(myClassVisitor,0);
        byte[] bytes = classWriter.toByteArray();
        FileOutputStream outputStream = new FileOutputStream("d://1.class");
        outputStream.write(bytes);
        outputStream.close();
    }
}
```

MyClassVisitor1

```java
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import java.nio.charset.StandardCharsets;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

/**
 * 拦截字符串修改
 */
public class MyClassVisitor1 extends ClassVisitor {
    public MyClassVisitor1(ClassWriter classWriter) {
        super(ASM5, classWriter);
    }
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        return new MethodVisitor(api,super.visitMethod(access,name,desc,signature,exceptions)) {
            @Override
            public void visitLdcInsn(Object cst) {
                if(cst instanceof String){
                    byte[] bytes = ((String) cst).getBytes(StandardCharsets.UTF_8);//转换bytes
                    mv.visitTypeInsn(NEW,"java/lang/String");
                    mv.visitInsn(DUP);
                    mv.visitIntInsn(SIPUSH,bytes.length);
                    mv.visitIntInsn(NEWARRAY,T_BYTE);
                    for(int i = 0;i<bytes.length;i++){
                        mv.visitInsn(DUP);
                        mv.visitIntInsn(SIPUSH,i);
                        mv.visitIntInsn(SIPUSH,bytes[i]);
                        mv.visitInsn(AASTORE);
                    }
                    mv.visitLdcInsn("UTF-8");
                    mv.visitMethodInsn(INVOKEVIRTUAL,"java/lang/String","<init>","([BLjava/lang/String;)V",false);
                }else {
                    super.visitLdcInsn(cst);
                }
            }
        };
    }
}
```

将字符串转换成bytes
