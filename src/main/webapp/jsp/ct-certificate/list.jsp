<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ct Certificates List</title>
    <%@ include file="../../libs.html" %>
</head>
<body class="bg-light">
<jsp:include page="../navbar.jsp" />

<div class="container">
    <jsp:include page="panel.jsp" />

    <h2>Ct Certificates list</h2>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Subject</th>
            <th scope="col">Year</th>
            <th scope="col">Certificate Identifier</th>
            <th scope="col">Certificate Number</th>
            <th scope="col">Points</th>
            <th scope="col">Enrollee</th>
            <th scope="col">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="certificate" items="${list}">
            <tr>
                <td scope="row"><c:out value="${certificate.id}" /></td>
                <td><c:out value="${certificate.subject}" /></td>
                <td><c:out value="${certificate.year}" /></td>
                <td><c:out value="${certificate.certificateIdentifier}" /></td>
                <td><c:out value="${certificate.certificateNumber}" /></td>
                <td><c:out value="${certificate.ctPoints}" /></td>
                <td><a href="${pageContext.request.contextPath}/enrollee/${certificate.enrolleeId}">
                        <c:out value="${certificate.enrolleeName}" />
                    </a>
                </td>


                <td>
                    <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/ct-certificate/${certificate.id}/edit" role="button">Edit</a>

                    <form action="${pageContext.request.contextPath}/ct-certificate/${certificate.id}" method="post" style="display: inline-block;">
                        <input type="hidden" name="_method" value="DELETE">
                        <button type="submit" class="btn btn-outline-danger">Delete</button>
                    </form>

                    <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/ct-certificate/${certificate.id}" role="button">View</a>

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>