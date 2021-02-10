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
<form method="POST" action='meals' name="frmAddMeal">
    <input type="hidden" name="id" value="${meal.id}">
    DateTime : <input type="datetime-local" name="dateTime" required
                      value="${meal.dateTime}"/> <br/>
    Description : <input
        type="text" name="description" required
        value="<c:out value="${meal.description}" />"/> <br/>
    Calories : <input
        type="number" name="calories" min="0" required
        value="<c:out value="${meal.calories}" />"/> <br/>
    <input type="submit" value="Save"/></form>
<form method="Get" action='meals'>
    <input type="submit" value="Cancel"/>
</form>
</body>
</html>
