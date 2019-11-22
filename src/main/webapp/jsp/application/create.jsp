<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <title>Create University Application</title>
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

    <c:choose>
        <c:when test="${param.enrolleeId == null}">
            <form action="" method="GET">
                <div class="form-group">
                    <label for="enrolleeId">Enrollee</label>
                    <select class="form-control" id="enrolleeId" name="enrolleeId">
                        <c:forEach var="enrollee" items="${enrollees}">
                            <option <c:if test = "${enrollee.id.toString() eq enrolleeId}">selected="selected"</c:if> value="${enrollee.id}">${enrollee.id} - ${enrollee.lastName} ${enrollee.firstName}</option>
                        </c:forEach>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Next</button>

            </form>
        </c:when>
        <c:otherwise>
            <form action="${pageContext.request.contextPath}/application" method="POST">
                <input style="display: none" name="enrolleeId" value="${param.enrolleeId}">

                <div class="form-group">
                    <label for="educationDocumentId">Education Document</label>
                    <select class="form-control" id="educationDocumentId" name="educationDocumentId">
                        <c:forEach var="document" items="${documents}">
                            <option <c:if test = "${document.id.toString() eq educationDocumentId}">selected="selected"</c:if> value="${document.id}">${document.educationalInstitution}</option>
                        </c:forEach>
                    </select>
                </div>


                <div class="form-group">
                    <label for="certificateIdsList">Certificates</label>
                    <select class="form-control" multiple id="certificateIdsList" name="certificateIdsList" aria-describedby="certificatesHelp" >

                        <c:set var="selectedCertificates" value="${fn:split(certificateIdsList, ',')}"/>


                        <c:forEach var="certificate" items="${certificates}">

                            <c:set var="contains" value="false" />
                            <c:forEach var="item" items="${selectedCertificates}">
                                <c:if test="${item eq certificate.id}">
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

                        <c:set var="selectedSpecialities" value="${fn:split(specialities, ',')}"/>

                        <c:forEach var="speciality" items="${specialitiesList}">

                            <c:set var="contains" value="false" />
                            <c:forEach var="item" items="${selectedSpecialities}">
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
                    <select class="form-control" id="type" name="type">
                        <c:forEach var="applicationType" items="${types}">
                            <option  <c:if test = "${type eq applicationType}">selected="selected"</c:if>  value="${applicationType}">${applicationType.description}</option>
                        </c:forEach>
                    </select>
                </div>


                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </c:otherwise>
    </c:choose>


</div>

</body>
</html>