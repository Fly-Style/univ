<%@ page import="univ.oop.lab3.model.entities.Request" %>
<%@ page import="univ.oop.lab3.model.entities.Citizen" %>
<%@ page import="java.util.List" %>
<%@ page import="univ.oop.lab3.model.CitizenManager" %>
<%@page import="univ.oop.lab3.model.RequestManager" %>


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
        <title>My tasks</title>
    </head>
    <body>

        <%
            Citizen user = (Citizen) session.getAttribute("citizen");

            List<Request> requests = RequestManager.GetConcreteCitizenRequests(user.getId());
            request.setAttribute("requests", requests);
        %>
        <table border="3">
            <tr>
                <th><fmt:message key="brigade" bundle="${bundle}"/></th>
                <th><fmt:message key="request" bundle="${bundle}"/></th>
                <th><fmt:message key="work_time" bundle="${bundle}"/> </th>
                <th><fmt:message key="user_id" bundle="${bundle}"/></th>
                <th><fmt:message key="work_status" bundle="${bundle}"/></th>
            </tr>
            <c:forEach items="${requestScope.requests}" var="reqs">
                <tr>
                    <td>${reqs.workObject}</td>
                    <td>${reqs.scale}</td>
                    <td>${reqs.time}</td>
                    <td>${reqs.ownerID}</td>
                    <c:choose>
                        <c:when test="${reqs.workStatus==true}">
                            <td><font color="#006400"><fmt:message key="done" bundle="${bundle}"/></font></td>
                        </c:when>
                        <c:otherwise>
                            <td><font color="red"><fmt:message key="not_done" bundle="${bundle}"/></font></td>
                        </c:otherwise>
                    </c:choose>


                </tr>
            </c:forEach>
        </table><br />

        <td>
            <form action="/checkDone/" method="post">
                <input name="action" value="mark_undone" type="hidden">
                <input type="submit" name="check_button" value="Check" />
            </form>
        </td>

        <form action="consumer_home.jsp" method="post">
            <input type="submit" value="<fmt:message key="home_page" bundle="${bundle}"/>"/>
        </form>
    </body>
</html>
