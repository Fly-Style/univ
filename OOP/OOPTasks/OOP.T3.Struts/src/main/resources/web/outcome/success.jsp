<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  Date: 4/5/2015
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration Success</title>
</head>
<body>
    First Name: <s:property value="user.name"></s:property><br/>
    Last Name:&nbsp; <s:property value="user.surname"></s:property><br/>
    Phone: <s:property value="user.phone"></s:property><br/>
    City: <s:property value="user.city"></s:property><br/>
    Address: <s:property value="user.address"></s:property><br/>
    Birthday: <s:property value="user.birthday"></s:property><br/>
    Registration date: <s:property value="user.registration"></s:property><br/>

    <a href="../index.jsp"><button>Back</button></a>
</body>
</html>
