<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Update Ct Certificate - ${entity.certificateIdentifier}</title>
    <%@ include file="../../libs.html" %>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>
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

    <form action="${pageContext.request.contextPath}/ct-certificate/<c:out value='${entity.id}' />" method="POST">
        <input type="hidden" name="_method" value="PUT">

        <div class="form-group">
            <label for="ctPoints">Points</label>
            <input type="text" class="form-control"
                   id="ctPoints" name="ctPoints" placeholder="Points"
                   value="<c:out value='${entity.ctPoints}' />">
        </div>
        <div class="form-group">
            <label for="certificateIdentifier">Certificate Identifier</label>
            <input type="text" class="form-control"
                   id="certificateIdentifier" name="certificateIdentifier" placeholder="XX-XXX-X"
                   value="<c:out value='${entity.certificateIdentifier}' />">
        </div>
        <div class="form-group">
            <label for="certificateNumber">Middle name</label>
            <input type="text" class="form-control"
                   id="certificateNumber" name="certificateNumber"
                   placeholder="XXXXXXX"
                   value="<c:out value='${entity.certificateNumber}' />">
        </div>

        <div class="form-group">
            <label for="subject">Subject</label>
            <select class="form-control" id="subject" name="subject">
                <c:forEach var="subject" items="${subjects}">

                    <option <c:if test = "${subject.subjectName eq entity.subject}">selected="selected"</c:if> value="${subject}">${subject.subjectName}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="year">Year</label>
            <input type="text" class="form-control"
                   id="year" name="year" placeholder="Year"
                   value="<c:out value='${entity.year}' />">
        </div>

        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>

<script>
    $(document).ready(function(){
        $('#certificateIdentifier').mask('00-000-0');
        $('#certificateNumber').mask('0000000');
    });
</script>

</body>
</html>