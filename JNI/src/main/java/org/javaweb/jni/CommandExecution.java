package org.javaweb.jni;

import java.io.File;

public class CommandExecution {

    static {
        System.setProperty("java.library.path","D:\\library\\");
        System.loadLibrary("cmd");
//        System.loadLibrary("cmd");
    }
    public static native String exec(String cmd);

    public static void main(String[] args) {
        exec("calc");
//        System.out.println((File.separatorChar));
    }
}