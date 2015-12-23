<%@ page import="univ.oop.lab3.model.RequestManager" %>
<%@ page import="univ.oop.lab3.model.entities.WorkList" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="a" uri="../../WEB-INF/access_tag.tld" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false"%>

<fmt:setLocale value="${locale == null ? 'en_US' : locale}" />
<fmt:setBundle basename="Lang" var="bundle" />
<fmt:requestEncoding value="UTF-8" />

<html>
	<head>
		<title><fmt:message key="work_list_view" bundle="${bundle}"/></title>
	</head>
	<body>

		<%
			List<WorkList> lst = WorkList.GetWorkList();
			request.setAttribute("worklist", lst);
		%>

		<table border="3">
			<tr>
				<th><fmt:message key="brigade" bundle="${bundle}"/></th>
				<th><fmt:message key="request" bundle="${bundle}"/></th>
			</tr>
			<c:forEach items="${requestScope.worklist}" var="list">
				<tr>
					<td>${list.requestID}</td>
					<td>${list.brigadeID}</td>
				</tr>
			</c:forEach>
		</table><br />

		<td>
			<form action="/checkList" method="post">
				<input name="action" value="mark_undone" type="hidden">
				<input type="submit" name="checks_button" value="Check" />
			</form>
		</td>

		<form action="consumer_home.jsp" method="post">
			<input type="submit" value="<fmt:message key="home_page" bundle="${bundle}"/>">
		</form>
	</body>
</html>
