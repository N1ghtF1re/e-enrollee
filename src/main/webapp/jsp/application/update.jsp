<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <title>Update University Application - ${entity.id}</title>
    <%@ include file="../../libs.html" %>
</head>
<body class="bg-light">
<jsp:include page="../navbar.jsp" />

<div class="container">
    <jsp:include page="panel.jsp" />

    <h2>Create University Application</h2>

    <c:if test="${error != null}">
        <div class="alert alert-danger" role="alert">
                ${error}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/application/<c:out value='${entity.id}' />" method="POST">
        <input type="hidden" name="_method" value="PUT">

        <div class="form-group">
            <label for="educationDocumentId">Education Document</label>
            <select class="form-control" id="educationDocumentId" name="educationDocumentId">
                <c:forEach var="document" items="${documents}">
                    <option <c:if test = "${document.id.toString() eq entity.educationDocument.id}">selected="selected"</c:if> value="${document.id}">${document.educationalInstitution}</option>
                </c:forEach>
            </select>
        </div>


        <div class="form-group">
            <label for="certificateIdsList">Certificates</label>
            <select class="form-control" multiple id="certificateIdsList" name="certificateIdsList" aria-describedby="certificatesHelp" >


                <c:forEach var="certificate" items="${certificates}">

                    <c:set var="contains" value="false" />
                    <c:forEach var="item" items="${entity.certificates}">
                        <c:if test="${item.id eq certificate.id}">
                            <c:set var="contains" value="true" />
                        </c:if>
                    </c:forEach>

                    <option  <c:if test = "${contains eq true}">selected="selected"</c:if> value="${certificate.id}">${certificate.subject} - ${certificate.ctPoints} (${certificate.year} year)</option>
                </c:forEach>
            </select>

            <small id="certificatesHelp" class="form-text text-muted">Please, select 3 ct certificate.</small>

        </div>

        <div class="form-group">
            <label for="specialities">Specialities</label>
            <select class="form-control" multiple id="specialities" name="specialities">


                <c:forEach var="speciality" items="${specialitiesList}">

                    <c:set var="contains" value="false" />
                    <c:forEach var="item" items="${entity.specialities}">
                        <c:if test="${item eq speciality.toString()}">
                            <c:set var="contains" value="true" />
                        </c:if>
                    </c:forEach>

                    <option <c:if test = "${contains eq true}">selected="selected"</c:if> value="${speciality}">${speciality.faculty} - ${speciality}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="type">Type</label>

            <input type="text" class="form-control" disabled="disabled" id="type"
                   value="<c:out value='${entity.type}' />">
        </div>


        <button type="submit" class="btn btn-primary">Submit</button>
    </form>

</div>

</body>
</html>