package org.javaweb.jni;

import java.io.File;
import java.lang.reflect.Method;

public class CommandExecutionTest {
    public static void main(String[] args) {
        cmd("calc");
    }
    public static void cmd(String cmd){
        try {
            ClassLoader loader = new ClassLoader(CommandExecutionTest.class.getClassLoader()){};
            // 测试时候换成自己编译好的dll路径
            File libPath = new File("E:\\java安全\\jni\\jni\\cmd.dll");
            // 加载命令执行类
            Class commandClass = loader.loadClass("org.javaweb.jni.CommandExecution");
            // 可以用System.load也加载lib也可以用反射ClassLoader加载,如果loadLibrary0
            Method loadLibrary0Method = ClassLoader.class.getDeclaredMethod("loadLibrary0", Class.class, File.class);
            loadLibrary0Method.setAccessible(true);
            loadLibrary0Method.invoke(loader, commandClass, libPath);
            String content = (String) commandClass.getMethod("exec", String.class).invoke(null, cmd);
            System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
