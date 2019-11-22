<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Update Education Document - ${entity.id}</title>
    <%@ include file="../../libs.html" %>
</head>
<body class="bg-light">
<jsp:include page="../navbar.jsp" />

<div class="container">
    <jsp:include page="panel.jsp" />

    <h2>Edit certificate <c:out value='${entity.id}' /></h2>

    <c:if test="${error != null}">
        <div class="alert alert-danger" role="alert">
                ${error}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/education-document/<c:out value='${entity.id}' />" method="POST">
        <input type="hidden" name="_method" value="PUT">

        <div class="form-group">
            <label for="averageGrade">Average Grade</label>
            <input type="text" class="form-control"
                   id="averageGrade" name="averageGrade" placeholder="Average grade"
                   value="<c:out value='${entity.averageGrade}' />">
        </div>
        <div class="form-group">
            <label for="educationalInstitution">Education Institution</label>
            <input type="text" class="form-control"
                   id="educationalInstitution" name="educationalInstitution" placeholder="Education Institution"
                   value="<c:out value='${entity.educationalInstitution}' />">
        </div>
        <div class="form-group">
            <label for="documentUniqueNumber">Document Number</label>
            <input type="text" class="form-control"
                   id="documentUniqueNumber" name="documentUniqueNumber"
                   placeholder="Document Number"
                   value="<c:out value='${entity.documentUniqueNumber}' />">
        </div>

        <div class="form-group">
            <label for="documentType">Document Type</label>
            <input type="text" class="form-control"
                   id="documentType" name="documentType"
                   placeholder="Document Type"
                   value="<c:out value='${entity.documentType}' />">
        </div>

        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>

</body>
</html>