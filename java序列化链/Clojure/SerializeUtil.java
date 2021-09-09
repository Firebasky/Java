package Tools;

import java.io.*;

public class SerializeUtil {

   public static void writeObjectToFile(Object obj,String fileName) throws Exception {
       ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
       outputStream.writeObject(obj);
       outputStream.close();
   }
    public static void readFileObject(String fileName) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
        Object o = ois.readObject();
    }
}
