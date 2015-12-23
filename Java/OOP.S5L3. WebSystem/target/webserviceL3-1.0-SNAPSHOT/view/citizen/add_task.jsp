<?xml version="1.0" encoding="utf-8" ?>
<%@ page import="univ.oop.lab3.model.CitizenManager" %>
<%@ page import="univ.oop.lab3.model.RequestManager" %>
<%@ page import="univ.oop.lab3.model.entities.Citizen" %>
<%@ page import="univ.oop.lab3.model.entities.Request" %>
<%@ page import="java.util.List" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="../../WEB-INF/access_tag.tld" prefix="a" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false"%>

<fmt:setLocale value="${locale == null ? 'en_US' : locale}" />
<fmt:setBundle basename="Lang" var="bundle" />
<fmt:requestEncoding value="UTF-8" />

<html>
	<head>
		<meta charset="utf-8" />
		<title><fmt:message key="add_request" bundle="${bundle}"/></title>
	</head>
	<body>

		<form action="/addTask" method="post">
			<fmt:message key="work_object" bundle="${bundle}"/>:<br />
				<tr>
					<input type="text" name="work_object"/>
				</tr>
			<br/>
			<fmt:message key="scale" bundle="${bundle}"/>: <br/>
				<tr>
					<input type="text" name="scale"/>
				</tr>
			<br>
			<fmt:message key="work_time" bundle="${bundle}"/>: <br/>
			<tr>
				<input type="datetime" name="work_time" value="wrk"/>
			</tr>
			<br>
				<input type="submit" value="<fmt:message key="add_request" bundle="${bundle}"/>">
			<br/>

		</form>

		<form action="consumer_home.jsp" method="post">
			<input type="submit" value="<fmt:message key="home_page" bundle="${bundle}"/>">
		</form>

	</body>
</html>
