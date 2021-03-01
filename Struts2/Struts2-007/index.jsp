<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>S2-007</title>
</head>
<body>
<h2>S2-007 Demo</h2>
<p>link: <a href="https://struts.apache.org/docs/s2-007.html">https://struts.apache.org/docs/s2-007.html</a></p>

<s:form action="user">
    <s:textfield name="name" label="name" />
    <s:textfield name="email" label="email" />
    <s:textfield name="age" label="age" />
    <s:submit></s:submit>
</s:form>
</body>
</html>
