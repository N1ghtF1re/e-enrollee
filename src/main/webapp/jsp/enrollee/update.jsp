<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Update Enrollee - <c:out value="${entity.lastName}"/> <c:out value="${entity.firstName}"/></title>
    <%@ include file="../../libs.html" %>

    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://unpkg.com/gijgo@1.9.13/js/gijgo.min.js" type="text/javascript"></script>
    <link href="https://unpkg.com/gijgo@1.9.13/css/gijgo.min.css" rel="stylesheet" type="text/css" />
</head>
<body class="bg-light">
<jsp:include page="../navbar.jsp" />

<div class="container">
    <jsp:include page="panel.jsp" />

    <h2>Edit enrollee <c:out value='${entity.id}' /></h2>

    <c:if test="${error != null}">
        <div class="alert alert-danger" role="alert">
                ${error}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/enrollee/<c:out value='${entity.id}' />" method="POST">
        <input type="hidden" name="_method" value="PUT">

        <div class="form-group">
            <label for="firstName">First name</label>
            <input type="text" class="form-control"
                   id="firstName" name="firstName" placeholder="First name"
                   value="<c:out value='${entity.firstName}' />">
        </div>
        <div class="form-group">
            <label for="lastName">First name</label>
            <input type="text" class="form-control"
                   id="lastName" name="lastName" placeholder="Last name"
                   value="<c:out value='${entity.lastName}' />">
        </div>
        <div class="form-group">
            <label for="middleName">Middle name</label>
            <input type="text" class="form-control"
                   id="middleName" name="middleName"
                   placeholder="Middle name"
                   value="<c:out value='${entity.middleName}' />">
        </div>

        <div class="form-group">
            <label for="birthDate">Date of Birth</label>
            <input type="text" class="form-control"
                   id="birthDate" name="birthDate" placeholder="Date Of Birth"
                   value="<c:out value='${entity.birthDate}' />">
        </div>

        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>
<script>
    $('#birthDate').datepicker({
        uiLibrary: 'bootstrap4',
        format: 'dd.mm.yyyy'
    });
</script>
</body>
</html>