<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false"%>

<fmt:setLocale value="${locale == null ? 'en_US' : locale}" />
<fmt:setBundle basename="Lang" var="bundle" />
<fmt:requestEncoding value="UTF-8" />
<html>
<head>
	<%
		String title = (String) request.getAttribute("title");
		request.removeAttribute("title");
		if (title == null) title = "Access denied";
	%>
	<title><fmt:message key="<%=title.toString()%>" bundle="${bundle}"/></title>
</head>
<body>
<%
	String infoMessage = (String) request.getAttribute("infoMessage");
	request.removeAttribute("infoMessage");
	if (infoMessage == null) infoMessage = "Access denied";
%>
<h2><fmt:message key="<%=infoMessage.toString()%>" bundle="${bundle}"/></h2>

<%
	String prevPageURL = request.getHeader("Referer");
	if (prevPageURL == null) {
		prevPageURL = "/";
	}
%>

<form action="<%=prevPageURL%>" method="post">
	<input type="submit" value="<fmt:message key="back" bundle="${bundle}"/>"/>
</form>
</body>
</html>