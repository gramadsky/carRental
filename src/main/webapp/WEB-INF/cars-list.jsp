<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Cars</title>
</head>
<body>
<h1> CARS LIST</h1>

<table class="tg">
    <tr>
        <th width="20"> ID </th>
        <th width="140"> Model</th>
        <th width="140"> Class</th>
        <th width="140"> Cost</th>
        <th width="140"> Availability</th>
        <th width="60"> Edit</th>
        <th width="60"> Delete</th>
    </tr>
    <c:forEach items="${cars}" var="car">
        <tr>
            <td>${car.id}</td>
            <td>${car.carModel}</td>
            <td>${car.carClass}</td>
            <td>${car.cost}</td>
            <td>${car.availability}</td>
            <td><a href="<c:url value='/edit/${car.id}'/>">Edit</a></td>
            <td><a href="<c:url value='/remove/${car.id}'/>">Delete</a></td>
        <tr>
    </c:forEach>

</table>
</body>
</html>