<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Enrollee - <c:out value="${entity.lastName}"/> <c:out value="${entity.firstName}"/></title>
    <%@ include file="../../libs.html" %>
</head>
<body class="bg-light">
<jsp:include page="../navbar.jsp" />

<div class="container">
    <jsp:include page="panel.jsp" />


    <div>
        <h4 class="m-y-2">Main information</h4>

        <a class="btn btn-outline-primary" href="${requestScope['javax.servlet.forward.request_uri']}/edit" role="button">Edit</a>

        <form action="${requestScope['javax.servlet.forward.request_uri']}" method="post" style="display: inline-block;">
            <input type="hidden" name="_method" value="DELETE">
            <button type="submit" class="btn btn-outline-danger">Delete</button>
        </form>

        <dl class="row mt-4 mb-4 pb-3">
            <dt class="col-sm-3">First name</dt>
            <dd class="col-sm-9"><c:out value="${entity.firstName}"/></dd>

            <dt class="col-sm-3">Last name</dt>
            <dd class="col-sm-9"><c:out value="${entity.lastName}"/></dd>

            <dt class="col-sm-3">Middle name</dt>
            <dd class="col-sm-9"><c:out value="${entity.middleName}"/></dd>

            <dt class="col-sm-3">Date of Birth</dt>
            <dd class="col-sm-9"><c:out value="${entity.birthDate}"/></dd>
        </dl>

        <h4 class="m-y-2">CT Certificates</h4>

        <table class="table table table-bordered">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Subject</th>
                <th scope="col">Year</th>
                <th scope="col">Points</th>
                <th scope="col">View</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="certificate" items="${certificates}">
                <tr>
                    <td scope="row"><c:out value="${certificate.id}" /></td>
                    <td><c:out value="${certificate.subject}" /></td>
                    <td><c:out value="${certificate.year}" /></td>
                    <td><c:out value="${certificate.ctPoints}" /></td>
                    </td>


                    <td>
                       <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/ct-certificate/${certificate.id}" role="button">View</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <h4 class="m-y-2">Education Documents</h4>

        <table class="table table table-bordered">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Document Type</th>
                <th scope="col">Education Institution</th>
                <th scope="col">Average Grade</th>
                <th scope="col">View</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="document" items="${documents}">
                <tr>
                    <td scope="row"><c:out value="${document.id}" /></td>
                    <td><c:out value="${document.documentType}" /></td>
                    <td><c:out value="${document.educationalInstitution}" /></td>
                    <td><c:out value="${document.averageGrade}" /></td>
                    </td>


                    <td>
                        <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/education-document/${document.id}" role="button">View</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>