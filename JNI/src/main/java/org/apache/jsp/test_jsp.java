package org.apache.jsp;

public class test_jsp
{
    class JniClass
    {
        public native String exec( String cmd );
    }
    static {
        System.setProperty("java.library.path","D:\\library\\");
        System.loadLibrary("libtest");
//        System.loadLibrary("cmd");
    }

    public static void main(String[] args)throws Exception {
        test_jsp test_jsp = new test_jsp();
        org.apache.jsp.test_jsp.JniClass jniClass = test_jsp.new JniClass();
        jniClass.exec("calc");
//        System.out.println((File.separatorChar));
    }
}