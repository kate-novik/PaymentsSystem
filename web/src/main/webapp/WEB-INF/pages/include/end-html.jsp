<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<div class="row notification" >
<c:if test="${not empty message}" >
                <div class="alert alert-${type} alert-dismissible fade in" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">×</span>
                </button>
                   ${message}
                 </div>
                 </c:if>
                 </div>
<footer>
    <nav class="navbar navbar-default navbar-fixed-bottom">
        <div class="container">

<p class="text-center navbar-text">© 2016 Kate Novik — All rights reserved.</p>
</div>
</nav>
</footer>
</body>
</html>