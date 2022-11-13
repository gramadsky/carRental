<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
    <title>Cars Page</title>

    <style type="text/css">
        .tg{
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #ccc;
        }

        .tg td {
            font-family: Arial, sans-serif;
            font-size: 14px;
            padding: 10px 5px;
            border-style: solid;
            border-color: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #fff;
        }

        .tg th {
            font-family: Arial, sans-serif;
            font-size: 14px;
            font-weight: normal;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #f0f0f0;
        }

        .tg .tg-4eph {
            background_color: #f9f9f9
        }
    </style>
</head>
<body>

<a href = "/index.jsp">Back to main menu</a>

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

<h1>Add Car</h1

<c:url var="addAction" value="/cars/add"/>

<form:form action="${addAction}" commandName="car">
    <table>
        <c:if test="${!empty car.carModel}">
            <tr>
                <td>
                    <form:label path="id">
                        <spring:message text="ID"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="id" readonly="true" size="8" disabled="true"/>
                    <form:hidden path="id"/>
                </td>
            </tr>
        </c:if>

         <tr>
            <td>
                <form:label path="carModel">
                    <spring:message text="Model"/>
                </form:label>
            </td>
            <td>
                <form:input path="carModel"/>
            </td>
        </tr>

        <tr>
            <td>
                <form:label path="carClass">
                    <spring:message text="Class"/>
                </form:label>
            </td>
            <td>
                <form:input path="carClass"/>
            </td>
        </tr>

        <tr>
            <td>
                <form:label path="cost">
                    <spring:message text="Cost"/>
                </form:label>
            </td>
            <td>
                <form:input path="cost"/>
            </td>
        </tr>

        <tr>
            <td>
                <form:label path="availability">
                    <spring:message text="Availability"/>
                </form:label>
            </td>
            <td>
                <form:input path="availability"/>
            </td>
        </tr>

        <tr>
            <td colspan="2">
                <c:if test="${!empty car.carModel}">
                    <input type="submit"
                        value="<spring:message text="Edit Car"/>"/>
                </c:if>
                <c:if test="${empty car.carModel}">
                    <input type="submit"
                        value="<spring:message text="Add Car"/>"/>
                </c:if>
            </td>
        </tr>
    </table>
 </form:form>


</body>
</html>