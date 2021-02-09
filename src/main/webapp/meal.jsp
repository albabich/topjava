<%--
  Created by IntelliJ IDEA.
  User: AB
  Date: 08.02.2021
  Time: 12:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit Meal</h2>
<form method="POST" action='listmeal' name="frmAddMeal">
    <input type="hidden" name="id" value="${meal.id}">
    DateTime : <input type="text" name="dateTime"
        <fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
                      value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}" />"/> <br/>
    Description : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />"/> <br/>
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />"/> <br/>
    <input type="submit" value="Save"/></form>
<form method="Get" action='index.jsp'>
    <input type="submit" value="Cancel"/>
</form>
</body>
</html>
