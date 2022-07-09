# jboss介绍：

JBoss 是一个基于J2EE的[开放源代码](https://baike.baidu.com/item/开放源代码/114160)的[应用服务器](https://baike.baidu.com/item/应用服务器/4971773)。 JBoss代码遵循LGPL许可，可以在任何商业应用中免费使用。JBoss是一个管理EJB的容器和服务器，支持EJB 1.1、EJB  2.0和EJB3的规范。但JBoss核心服务不包括支持servlet/JSP的WEB容器，一般与Tomcat或Jetty绑定使用。

自己测试了网上很多工具发现不是特别好用 而且不集中。。。。
所以自己想写一个综合利用的工具。。。

+ [JBOSS CVE-2017-12149 WAF绕过之旅](https://www.yulegeyu.com/2021/03/05/JBOSS-CVE-2017-12149-WAF%E7%BB%95%E8%BF%87%E4%B9%8B%E6%97%85/)

## 反序列化漏洞
bypass  请求方式是HEAD

**endpoint**
```
/invoker/readonly   是一个filter 请求方法随便并且url后面可以加其他的
/invoker/EJBInvokerServlet
/invoker/JMXInvokerServlet
/invoker/readonly/JMXInvokerServlet
/invoker/restricted/JMXInvokerServlet
```
http-invoker.sar 组件的问题

web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE web-app PUBLIC
   "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
   "http://java.sun.com/dtd/web-app_2_3.dtd">

<!-- The http-invoker.sar/invoker.war web.xml descriptor
$Id: web.xml 96504 2009-11-18 19:01:09Z scott.stark@jboss.org $
-->
<web-app>
    <filter>
      <filter-name>ReadOnlyAccessFilter</filter-name>
      <filter-class>org.jboss.invocation.http.servlet.ReadOnlyAccessFilter</filter-class>
      <init-param>
         <param-name>readOnlyContext</param-name>
         <param-value>readonly</param-value>
         <description>The top level JNDI context the filter will enforce
         read-only access on. If specified only Context.lookup operations
         will be allowed on this context. Another other operations or lookups
         on any other context will fail. Do not associate this filter with the
         JMXInvokerServlets if you want unrestricted access.
         </description>
      </init-param>
      <init-param>
         <param-name>invokerName</param-name>
          <param-value>jboss:service=NamingBeanImpl</param-value>
         <description>The JMX ObjectName of the naming service mbean
         </description>
      </init-param>
    </filter>

    <filter-mapping>
      <filter-name>ReadOnlyAccessFilter</filter-name>
      <url-pattern>/readonly/*</url-pattern>
    </filter-mapping>

<!-- ### Servlets -->
    <servlet>
        <servlet-name>EJBInvokerServlet</servlet-name>
        <description>The EJBInvokerServlet receives posts containing serlized
        MarshalledInvocation objects that are routed to the EJB invoker given by
        the invokerName init-param. The return content is a serialized
        MarshalledValue containg the return value of the inovocation, or any
        exception that may have been thrown.
        </description>
        <servlet-class>org.jboss.invocation.http.servlet.InvokerServlet</servlet-class>
        <init-param>
            <param-name>invokerName</param-name>
            <param-value>jboss:service=invoker,type=http</param-value>
            <description>The RMI/HTTP EJB compatible invoker</description>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
   <servlet>
       <servlet-name>JMXInvokerServlet</servlet-name>
       <description>The JMXInvokerServlet receives posts containing serlized
       MarshalledInvocation objects that are routed to the invoker given by
       the the MBean whose object name hash is specified by the
       invocation.getObjectName() value. The return content is a serialized
       MarshalledValue containg the return value of the inovocation, or any
       exception that may have been thrown.
       </description>
       <servlet-class>org.jboss.invocation.http.servlet.InvokerServlet</servlet-class>
       <load-on-startup>1</load-on-startup>
   </servlet>

    <servlet>
        <servlet-name>JNDIFactory</servlet-name>
        <description>A servlet that exposes the JBoss JNDI Naming service stub
        through http. The return content is a serialized
        MarshalledValue containg the org.jnp.interfaces.Naming stub. This
        configuration handles requests for the standard JNDI naming service.
        </description>
        <servlet-class>org.jboss.invocation.http.servlet.NamingFactoryServlet</servlet-class>
        <init-param>
            <param-name>namingProxyMBean</param-name>
            <param-value>jboss:service=invoker,type=http,target=Naming</param-value>
        </init-param>
      <init-param>
         <param-name>proxyAttribute</param-name>
         <param-value>Proxy</param-value>
      </init-param>
      <load-on-startup>2</load-on-startup>
    </servlet>

   <servlet>
       <servlet-name>ReadOnlyJNDIFactory</servlet-name>
       <description>A servlet that exposes the JBoss JNDI Naming service stub
       through http, but only for a single read-only context. The return content
       is a serialized MarshalledValue containg the org.jnp.interfaces.Naming
       stub.
       </description>
       <servlet-class>org.jboss.invocation.http.servlet.NamingFactoryServlet</servlet-class>
      <init-param>
          <param-name>namingProxyMBean</param-name>
          <param-value>jboss:service=invoker,type=http,target=Naming,readonly=true</param-value>
      </init-param>
      <init-param>
         <param-name>proxyAttribute</param-name>
         <param-value>Proxy</param-value>
      </init-param>
       <load-on-startup>2</load-on-startup>
   </servlet>

<!-- ### Servlet Mappings -->
    <servlet-mapping>
        <servlet-name>JNDIFactory</servlet-name>
        <url-pattern>/JNDIFactory/*</url-pattern>
    </servlet-mapping>
    <!-- A mapping for the NamingFactoryServlet that only allows invocations
    of lookups under a read-only context. This is enforced by the
    ReadOnlyAccessFilter
    -->
    <servlet-mapping>
        <servlet-name>ReadOnlyJNDIFactory</servlet-name>
        <url-pattern>/ReadOnlyJNDIFactory/*</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>EJBInvokerServlet</servlet-name>
        <url-pattern>/EJBInvokerServlet/*</url-pattern>
    </servlet-mapping>
   <servlet-mapping>
       <servlet-name>JMXInvokerServlet</servlet-name>
       <url-pattern>/JMXInvokerServlet/*</url-pattern>
   </servlet-mapping>
    <!-- A mapping for the JMXInvokerServlet that only allows invocations
    of lookups under a read-only context. This is enforced by the
    ReadOnlyAccessFilter
    -->
    <servlet-mapping>
        <servlet-name>JMXInvokerServlet</servlet-name>
        <url-pattern>/readonly/JMXInvokerServlet/*</url-pattern>
    </servlet-mapping>

    <!-- Alternate mappings that place the servlets under the restricted
    path to required authentication for access. Remove the unsecure mappings
    if only authenticated users should be allowed.
    -->
    <servlet-mapping>
        <servlet-name>JNDIFactory</servlet-name>
        <url-pattern>/restricted/JNDIFactory/*</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>JMXInvokerServlet</servlet-name>
        <url-pattern>/restricted/JMXInvokerServlet/*</url-pattern>
    </servlet-mapping>

   <!-- An example security constraint that restricts access to the HTTP invoker
   to users with the role HttpInvoker Edit the roles to what you want and
   configure the WEB-INF/jboss-web.xml/security-domain element to reference
   the security domain you want.
   -->
   <security-constraint>
      <web-resource-collection>
         <web-resource-name>HttpInvokers</web-resource-name>
         <description>An example security config that only allows users with the
            role HttpInvoker to access the HTTP invoker servlets
         </description>
         <url-pattern>/restricted/*</url-pattern>
         <http-method>GET</http-method>
         <http-method>POST</http-method>
      </web-resource-collection>
      <auth-constraint>
         <role-name>HttpInvoker</role-name>
      </auth-constraint>
   </security-constraint>
   <login-config>
      <auth-method>BASIC</auth-method>
      <realm-name>JBoss HTTP Invoker</realm-name>
   </login-config>

   <security-role>
      <role-name>HttpInvoker</role-name>
   </security-role>
</web-app>
```
org.jboss.invocation.http.servlet.ReadOnlyAccessFilter
```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jboss.invocation.http.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Map;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.jboss.invocation.MarshalledInvocation;
import org.jboss.logging.Logger;
import org.jboss.mx.util.MBeanServerLocator;

public class ReadOnlyAccessFilter implements Filter {
    private static Logger log = Logger.getLogger(ReadOnlyAccessFilter.class);
    private FilterConfig filterConfig = null;
    private String readOnlyContext;
    private Map namingMethodMap;

    public ReadOnlyAccessFilter() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            this.readOnlyContext = filterConfig.getInitParameter("readOnlyContext");
            String invokerName = filterConfig.getInitParameter("invokerName");

            try {
                MBeanServer mbeanServer = MBeanServerLocator.locateJBoss();
                ObjectName mbean = new ObjectName(invokerName);
                this.namingMethodMap = (Map)mbeanServer.getAttribute(mbean, "MethodMap");
            } catch (Exception var5) {
                log.error("Failed to init ReadOnlyAccessFilter", var5);
                throw new ServletException("Failed to init ReadOnlyAccessFilter", var5);
            }
        }

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        Principal user = httpRequest.getUserPrincipal();
        if (user == null && this.readOnlyContext != null) {
            ServletInputStream sis = request.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(sis);
            MarshalledInvocation mi = null;

            try {
                mi = (MarshalledInvocation)ois.readObject();
            } catch (ClassNotFoundException var10) {
                throw new ServletException("Failed to read MarshalledInvocation", var10);
            }

            request.setAttribute("MarshalledInvocation", mi);
            mi.setMethodMap(this.namingMethodMap);
            Method m = mi.getMethod();
            if (m != null) {
                this.validateAccess(m, mi);
            }
        }

        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public String toString() {
        if (this.filterConfig == null) {
            return "NamingAccessFilter()";
        } else {
            StringBuffer sb = new StringBuffer("NamingAccessFilter(");
            sb.append(this.filterConfig);
            sb.append(")");
            return sb.toString();
        }
    }

    private void validateAccess(Method m, MarshalledInvocation mi) throws ServletException {
        boolean trace = log.isTraceEnabled();
        if (trace) {
            log.trace("Checking against readOnlyContext: " + this.readOnlyContext);
        }

        String methodName = m.getName();
        if (!methodName.equals("lookup")) {
            throw new ServletException("Only lookups against " + this.readOnlyContext + " are allowed");
        } else {
            Object[] args = mi.getArguments();
            Object arg = args.length > 0 ? args[0] : "";
            String name;
            if (arg instanceof String) {
                name = (String)arg;
            } else {
                name = arg.toString();
            }

            if (trace) {
                log.trace("Checking lookup(" + name + ") against: " + this.readOnlyContext);
            }

            if (!name.startsWith(this.readOnlyContext)) {
                throw new ServletException("Lookup(" + name + ") is not under: " + this.readOnlyContext);
            }
        }
    }
}
```
org.jboss.invocation.http.servlet.InvokerServlet
```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jboss.invocation.http.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.invocation.InvocationException;
import org.jboss.invocation.MarshalledInvocation;
import org.jboss.invocation.MarshalledValue;
import org.jboss.logging.Logger;
import org.jboss.mx.util.JMXExceptionDecoder;
import org.jboss.mx.util.MBeanServerLocator;
import org.jboss.security.SecurityAssociation;
import org.jboss.system.Registry;

public class InvokerServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(InvokerServlet.class);
    private static String REQUEST_CONTENT_TYPE = "application/x-java-serialized-object; class=org.jboss.invocation.MarshalledInvocation";
    private static String RESPONSE_CONTENT_TYPE = "application/x-java-serialized-object; class=org.jboss.invocation.MarshalledValue";
    private MBeanServer mbeanServer;
    private ObjectName localInvokerName;

    public InvokerServlet() {
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            String name = config.getInitParameter("invokerName");
            if (name != null) {
                this.localInvokerName = new ObjectName(name);
                log.debug("localInvokerName=" + this.localInvokerName);
            }
        } catch (MalformedObjectNameException var3) {
            throw new ServletException("Failed to build invokerName", var3);
        }

        this.mbeanServer = MBeanServerLocator.locateJBoss();
        if (this.mbeanServer == null) {
            throw new ServletException("Failed to locate the MBeanServer");
        }
    }

    public void destroy() {
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean trace = log.isTraceEnabled();
        if (trace) {
            log.trace("processRequest, ContentLength: " + request.getContentLength());
            log.trace("processRequest, ContentType: " + request.getContentType());
        }

        Boolean returnValueAsAttribute = (Boolean)request.getAttribute("returnValueAsAttribute");

        try {
            response.setContentType(RESPONSE_CONTENT_TYPE);
            MarshalledInvocation mi = (MarshalledInvocation)request.getAttribute("MarshalledInvocation");
            if (mi == null) {
                ServletInputStream sis = request.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(sis);
                mi = (MarshalledInvocation)ois.readObject();
                ois.close();
            }

            if (mi.getPrincipal() == null && mi.getCredential() == null) {
                mi.setPrincipal(InvokerServlet.GetPrincipalAction.getPrincipal());
                mi.setCredential(InvokerServlet.GetCredentialAction.getCredential());
            }

            Object[] params = new Object[]{mi};
            String[] sig = new String[]{"org.jboss.invocation.Invocation"};
            ObjectName invokerName = this.localInvokerName;
            if (invokerName == null) {
                Integer nameHash = (Integer)mi.getObjectName();
                invokerName = (ObjectName)Registry.lookup(nameHash);
                if (invokerName == null) {
                    throw new ServletException("Failed to find invoker name for hash(" + nameHash + ")");
                }
            }

            Object value = this.mbeanServer.invoke(invokerName, "invoke", params, sig);
            if (returnValueAsAttribute != null && returnValueAsAttribute) {
                request.setAttribute("returnValue", value);
            } else {
                MarshalledValue mv = new MarshalledValue(value);
                ServletOutputStream sos = response.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(sos);
                oos.writeObject(mv);
                oos.close();
            }
        } catch (Throwable var13) {
            Throwable t = JMXExceptionDecoder.decode(var13);
            if (t instanceof InvocationTargetException) {
                InvocationTargetException ite = (InvocationTargetException)t;
                t = ite.getTargetException();
            }

            InvocationException appException = new InvocationException(t);
            if (returnValueAsAttribute != null && returnValueAsAttribute) {
                log.debug("Invoke threw exception", t);
                request.setAttribute("returnValue", appException);
            } else if (response.isCommitted()) {
                log.error("Invoke threw exception, and response is already committed", t);
            } else {
                response.resetBuffer();
                MarshalledValue mv = new MarshalledValue(appException);
                ServletOutputStream sos = response.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(sos);
                oos.writeObject(mv);
                oos.close();
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    public String getServletInfo() {
        return "An HTTP to JMX invocation servlet";
    }

    private static class GetCredentialAction implements PrivilegedAction {
        static PrivilegedAction ACTION = new InvokerServlet.GetCredentialAction();

        private GetCredentialAction() {
        }

        public Object run() {
            Object credential = SecurityAssociation.getCredential();
            return credential;
        }

        static Object getCredential() {
            Object credential = AccessController.doPrivileged(ACTION);
            return credential;
        }
    }

    private static class GetPrincipalAction implements PrivilegedAction {
        static PrivilegedAction ACTION = new InvokerServlet.GetPrincipalAction();

        private GetPrincipalAction() {
        }

        public Object run() {
            Principal principal = SecurityAssociation.getPrincipal();
            return principal;
        }

        static Principal getPrincipal() {
            Principal principal = (Principal)AccessController.doPrivileged(ACTION);
            return principal;
        }
    }
}
```
