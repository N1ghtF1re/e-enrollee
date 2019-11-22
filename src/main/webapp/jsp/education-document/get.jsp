<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Education Document - ${entity.id}</title>
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
            <dt class="col-sm-3">Document Number</dt>
            <dd class="col-sm-9"><c:out value="${entity.documentUniqueNumber}"/></dd>

            <dt class="col-sm-3">Education Institution</dt>
            <dd class="col-sm-9"><c:out value="${entity.educationalInstitution}"/></dd>

            <dt class="col-sm-3">Enrollee</dt>
            <dd class="col-sm-9">
                <a href="${pageContext.request.contextPath}/enrollee/${entity.enrolleeId}">
                    <c:out value="${entity.enrolleeName}" />
                </a>
            </dd>

            <dt class="col-sm-3">Average Grade</dt>
            <dd class="col-sm-9"><c:out value="${entity.averageGrade}"/>/10</dd>

            <dt class="col-sm-3">Document Type</dt>
            <dd class="col-sm-9"><c:out value="${entity.documentType}"/></dd>
        </dl>
    </div>
</div>
</body>
</html>