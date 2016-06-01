<%@ taglib prefix="c" uri="/struts-tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Duplicate user!</title>
</head>
<body>
    User <s:property value="user.name user.surname"/> already exists!<br/>
    <a href="../index.jsp"><button>Back</button></a>
</body>
</html>
