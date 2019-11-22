<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>University Applications List</title>
    <%@ include file="../../libs.html" %>
</head>
<body class="bg-light">
<jsp:include page="../navbar.jsp" />

<div class="container">
    <jsp:include page="panel.jsp" />

    <h2>University Applications list</h2>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Enrollee</th>
            <th scope="col">Date</th>
            <th scope="col">Type</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="application" items="${list}">
            <tr>
                <td scope="row"><c:out value="${application.id}" /></td>
                <td><a href="${pageContext.request.contextPath}/enrollee/${application.enrolleeId}">
                    <c:out value="${application.enrolleeName}" />
                    </a>
                </td>
                <td><c:out value="${application.date}" /></td>
                <td><c:out value="${application.type}" /></td>



                <td>
                    <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/application/${application.id}/edit" role="button">Edit</a>

                    <form action="${pageContext.request.contextPath}/application/${application.id}" method="post" style="display: inline-block;">
                        <input type="hidden" name="_method" value="DELETE">
                        <button type="submit" class="btn btn-outline-danger">Delete</button>
                    </form>

                    <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/application/${application.id}" role="button">View</a>

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>