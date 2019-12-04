<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}" />
<fmt:setBundle basename="messages" />


<!DOCTYPE html>
<html lang="${cookie['lang'].value}">
<head>
    <title>Login</title>
    <%@ include file="../libs.html" %>
</head>
<body class="bg-light">
<jsp:include page="navbar.jsp" />

<div class="container">
    <div class="py-5 text-center">
        <h1><fmt:message key="label.login" /></h1>
    </div>

    <div class="container register-form">
        <form class="form" action="" method="post">
            <c:if test="${error != null}">
                <div class="alert alert-danger" role="alert">
                        ${error}
                </div>
            </c:if>

            <div class="form-content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <input type="text" name="username" class="form-control" placeholder="<fmt:message key="placeholder.username" />" value=""/>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="form-group">
                            <input type="password" name="password" class="form-control" placeholder="<fmt:message key="placeholder.password" />" value=""/>
                        </div>
                    </div>
                </div>
                <input type="submit" value="<fmt:message key="button.login" />">
            </div>
        </form>
    </div>

</div>

<jsp:include page="footer.jsp" />

</body>
</html>