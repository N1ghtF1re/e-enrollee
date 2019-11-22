<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Interim List</title>
    <%@ include file="../../libs.html" %>
</head>
<body class="bg-light">
<jsp:include page="../navbar.jsp" />

<div class="container">

    <div class="py-5 text-center">
        <h1>Interim List</h1>
    </div>

    <c:forEach var="interimList" items="${list.specialityInterimLists}">

        <c:choose>
            <c:when test="${interimList.passingScore == 0}">
                <c:set var = "score" scope = "session" value = "No competition"/>
            </c:when>
            <c:otherwise>
                <c:set var = "score" scope = "session" value = "${interimList.passingScore}"/>
            </c:otherwise>
        </c:choose>

        <h4 class="m-y-2"><c:out value="${interimList.speciality}" /> | <c:out value="${interimList.applicationType.description}" /> <span class="badge badge-secondary"><c:out value="${score}" /></span></h4>

        <ul class="list-group">
            <c:forEach var="enrollee" items="${interimList.enrollees}">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    <a href="${pageContext.request.contextPath}/enrollee/${enrollee.id}">
                        <c:out value="${enrollee.lastName}" /> <c:out value="${enrollee.firstName}" />
                    </a>
                </li>
            </c:forEach>

        </ul>
    </c:forEach>
</div>
</body>
</html>