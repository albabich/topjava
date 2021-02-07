<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="1" width="100%" cellpadding="5" style="border-collapse:collapse">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <c:forEach var="meal" items="${meals}">
        <c:set var="textcolor" value="color:green"/>
        <c:if test="${meal.excess}">
            <c:set var="textcolor" value="color:red"/>
        </c:if>
        <tr style="${textcolor}">
            <td><fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/></td>
            <td><c:out value="${meal.description}"></c:out></td>
            <td><c:out value="${meal.calories}"></c:out></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>