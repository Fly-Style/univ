<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<html>
  <head>
    <title>User registration</title>
  </head>
  <sx:head parseContent="true"></sx:head>
  <body>
    <div style="float:left">
      <s:form action="register">
        <s:textfield name="user.name" label="Name"></s:textfield>
        <s:textfield name="user.surname" label="Surname"></s:textfield>
        <s:textfield name="user.phone" label="Phone"></s:textfield>
        <s:textfield name="user.city" label="City"></s:textfield>
        <s:textfield name="user.address" label="Address"></s:textfield>
        <sx:datetimepicker name="user.birthday" label="Birthday" displayFormat="dd/MM/yyyy"
                value="01/01/1970"></sx:datetimepicker>

        <s:submit value="Register"/>
      </s:form>
    </div>
  </body>
</html>
