<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Create Education Document</title>
    <%@ include file="../../libs.html" %>
</head>
<body class="bg-light">
<jsp:include page="../navbar.jsp" />

<div class="container">
    <jsp:include page="panel.jsp" />

    <h2>Create Education Document</h2>

    <c:if test="${error != null}">
        <div class="alert alert-danger" role="alert">
                ${error}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/education-document" method="POST">
        <div class="form-group">
            <label for="averageGrade">Average Grade</label>
            <input type="text" class="form-control"
                   id="averageGrade" name="averageGrade" placeholder="Average grade"
                   value="<c:out value='${averageGrade}' />">
        </div>
        <div class="form-group">
            <label for="educationalInstitution">Education Institution</label>
            <input type="text" class="form-control"
                   id="educationalInstitution" name="educationalInstitution" placeholder="Education Institution"
                   value="<c:out value='${educationalInstitution}' />">
        </div>
        <div class="form-group">
            <label for="documentUniqueNumber">Document Number</label>
            <input type="text" class="form-control"
                   id="documentUniqueNumber" name="documentUniqueNumber"
                   placeholder="Document Number"
                   value="<c:out value='${documentUniqueNumber}' />">
        </div>

        <div class="form-group">
            <label for="documentType">Document Type</label>
            <input type="text" class="form-control"
                   id="documentType" name="documentType"
                   placeholder="Document Type"
                   value="<c:out value='${documentType}' />">
        </div>

        <div class="form-group">
            <label for="enrolleeId">Enrollee</label>
            <select class="form-control" id="enrolleeId" name="enrolleeId">
                <c:forEach var="enrollee" items="${enrollees}">
                    <option <c:if test = "${enrollee.id.toString() eq enrolleeId}">selected="selected"</c:if> value="${enrollee.id}">${enrollee.id} - ${enrollee.lastName} ${enrollee.firstName}</option>
                </c:forEach>
            </select>
        </div>


        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>

</body>
</html>