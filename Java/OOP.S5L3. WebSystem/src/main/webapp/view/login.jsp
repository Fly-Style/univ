<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false"%>

<fmt:setLocale value="${locale == null ? 'en_US' : locale}" />
<fmt:setBundle basename="Lang" var="bundle" />
<fmt:requestEncoding value="UTF-8" />

<html>
	<head>
		<title><fmt:message key="title_login" bundle="${bundle}"/></title>
	</head>
	<body>
		<%
			String senderInfo = (String) request.getAttribute("senderInfo");
			if (senderInfo == null) {
				senderInfo = "";
			} else {
				senderInfo += "<br/>";
			}
		%>
		<%=senderInfo.toString()%>

		<form action="/Login" method="post">
			<fmt:message key="login" bundle="${bundle}"/>:<br />
			<input type="text" name="login"/><br/>

			<fmt:message key="password" bundle="${bundle}"/>:<br />
			<input type="password" name="password"/><br/>

			<input type="submit" value="<fmt:message key="sign_in" bundle="${bundle}"/>"/>
		</form><br/>

	</body>
</html>
