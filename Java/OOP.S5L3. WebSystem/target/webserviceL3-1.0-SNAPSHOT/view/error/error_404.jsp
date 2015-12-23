<%--
  Created by IntelliJ IDEA.
  User: flystyle
  Date: 22.12.15
  Time: 08:45
  To change this template use File | Settings | File Templates.

--%>

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
    <title>Error 404</title>
</head>
<body>

    <fmt:message key="error_404" bundle="${bundle}"></fmt:message> <br/>

    <a href="/view/citizen/consumer_home.jsp" /> <fmt:message key="home_page" bundle="${bundle}"/> </a>

</body>
</html>
