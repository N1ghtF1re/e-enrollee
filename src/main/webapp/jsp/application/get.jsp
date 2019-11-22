<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>University Application - ${entity.id}</title>
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
            <dt class="col-sm-3">Total Points</dt>
            <dd class="col-sm-9"><c:out value="${entity.getTotalPoints()}"/></dd>

            <dt class="col-sm-3">Date</dt>
            <dd class="col-sm-9"><c:out value="${entity.date}"/></dd>

            <dt class="col-sm-3">Type</dt>
            <dd class="col-sm-9"><c:out value="${entity.type}"/></dd>

            <dt class="col-sm-3">Enrollee</dt>
            <dd class="col-sm-9">
                <a href="${pageContext.request.contextPath}/enrollee/${entity.enrolleeId}">
                    <c:out value="${entity.enrolleeName}" />
                </a>
            </dd>

            <dt class="col-sm-3">Education Documents</dt>
            <dd class="col-sm-9">
                <a href="${pageContext.request.contextPath}/education-document/${entity.educationDocument.id}">
                    <c:out value="${entity.educationDocument.documentType}"/> - <c:out value="${entity.educationDocument.documentUniqueNumber}"/>
                </a>
            </dd>

            <dt class="col-sm-3">Specialities</dt>
            <dd class="col-sm-9">

                <c:forEach var="speciality" items="${entity.specialities}">
                    <c:out value="${speciality}"/></br>
                </c:forEach>

            </dd>

            <dt class="col-sm-3">Ct Certificates</dt>
            <dd class="col-sm-9">

                <c:forEach var="certificate" items="${entity.certificates}">
                    <a href="${pageContext.request.contextPath}/ct-certificate/${certificate.id}">
                        <c:out value="${certificate.subject}"/> - <c:out value="${certificate.ctPoints}"/>/100</br>
                    </a>
                </c:forEach>

            </dd>

        </dl>
    </div>
</div>
</body>
</html>