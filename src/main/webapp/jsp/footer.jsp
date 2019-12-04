<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Footer -->
<footer class="page-footer font-small blue">


    <form action="${pageContext.request.contextPath}/language" method="POST" style="
        width: 130px;
        margin: 0 auto;
        text-align: center;
    ">
        <select class="form-control" id="lang" name="lang">
            <option value="ru" <c:if test="${cookie['lang'].value.equals('ru')}">selected</c:if>>Russian</option>
            <option value="en" <c:if test="${cookie['lang'].value.equals('en')}">selected</c:if>>English</option>
        </select>
        <input type="text" name="redirect_url" value="${requestScope['javax.servlet.forward.request_uri']}" style="display: none">
        <input type="submit">
    </form>
</footer>
<!-- Footer -->