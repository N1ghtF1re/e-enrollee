<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Enrollees List</title>
    <%@ include file="../../libs.html" %>
</head>
<body class="bg-light">
<jsp:include page="../navbar.jsp" />

<div class="container">
    <jsp:include page="panel.jsp" />

    <h2>Enrollees list</h2>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">First Name</th>
            <th scope="col">Last Name</th>
            <th scope="col">Middle Name</th>
            <th scope="col">Date of birth</th>
            <th scope="col">Actions</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach var="enrollee" items="${list}">
                <tr>
                    <td scope="row"><c:out value="${enrollee.id}" /></td>
                    <td><c:out value="${enrollee.firstName}" /></td>
                    <td><c:out value="${enrollee.lastName}" /></td>
                    <td><c:out value="${enrollee.middleName}" /></td>
                    <td><c:out value="${enrollee.birthDate}" /></td>

                    <td>
                        <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/enrollee/${enrollee.id}/edit" role="button">Edit</a>

                        <form action="${pageContext.request.contextPath}/enrollee/${enrollee.id}" method="post" style="display: inline-block;">
                            <input type="hidden" name="_method" value="DELETE">
                            <button type="submit" class="btn btn-outline-danger">Delete</button>
                        </form>

                        <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/enrollee/${enrollee.id}" role="button">View</a>

                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>