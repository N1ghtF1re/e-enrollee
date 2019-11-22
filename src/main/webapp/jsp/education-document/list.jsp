<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Education Documents List</title>
    <%@ include file="../../libs.html" %>
</head>
<body class="bg-light">
<jsp:include page="../navbar.jsp" />

<div class="container">
    <jsp:include page="panel.jsp" />

    <h2>Education Documents list</h2>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Enrollee</th>
            <th scope="col">Average Grade</th>
            <th scope="col">Education Institution</th>
            <th scope="col">Document Number</th>
            <th scope="col">Type</th>
            <th scope="col">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="document" items="${list}">
            <tr>
                <td scope="row"><c:out value="${document.id}" /></td>
                <td><a href="${pageContext.request.contextPath}/enrollee/${document.enrolleeId}">
                    <c:out value="${document.enrolleeName}" />
                </a>
                <td><c:out value="${document.averageGrade}" /></td>
                <td><c:out value="${document.educationalInstitution}" /></td>
                <td><c:out value="${document.documentUniqueNumber}" /></td>
                <td><c:out value="${document.documentType}" /></td>

                </td>


                <td>
                    <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/education-document/${document.id}/edit" role="button">Edit</a>

                    <form action="${pageContext.request.contextPath}/education-document/${document.id}" method="post" style="display: inline-block;">
                        <input type="hidden" name="_method" value="DELETE">
                        <button type="submit" class="btn btn-outline-danger">Delete</button>
                    </form>

                    <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/education-document/${document.id}" role="button">View</a>

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>