package shell.JNI;

public class CommandExecution {

//    public static native String exec(String cmd);

    public static void main(String[] args) {
        System.setProperty("java.library.path","D:/library");
        System.out.println(System.getProperty("java.library.path"));
    }
}