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
        <title><fmt:message key="citizen_home_page" bundle="${bundle}"/></title>
    </head>
    <body>
        <a href="my_tasks.jsp"><fmt:message key="my_tasks" bundle="${bundle}"/></a><br/>
        <a href="add_task.jsp"><fmt:message key="add_request" bundle="${bundle}"/> </a> <br/>
        <a href="view_work_list.jsp"><fmt:message key="work_list_view" bundle="${bundle}"/> </a> <br/>

        <br >
        <form action="/Logout" method="post">
            <input type="submit" value="<fmt:message key="logout" bundle="${bundle}"/>"/>
        </form>

    </body>
</html>
