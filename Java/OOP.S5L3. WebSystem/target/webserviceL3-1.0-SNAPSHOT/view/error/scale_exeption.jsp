<%--
  Created by IntelliJ IDEA.
  User: flystyle
  Date: 22.12.15
  Time: 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="a" uri="../../WEB-INF/access_tag.tld" %>
<%@ taglib prefix="a" uri="../../WEB-INF/access_tag.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false"%>

<fmt:setLocale value="${locale == null ? 'en_US' : locale}" />
<fmt:setBundle basename="Lang" var="bundle" />
<fmt:requestEncoding value="UTF-8" />

<html>
<head>
    <title><fmt:message key="scale_exeption" bundle="${bundle}"></fmt:message></title>
</head>
<body>

<a href="/view/citizen/consumer_home.jsp"/> <fmt:message key="home_page" bundle="${bundle}"/> </a>
</body>
</html>
