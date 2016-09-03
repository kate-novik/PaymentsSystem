<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="include/begin-html.jsp" %>
<%@ include file="include/header-html.jsp" %>

<br><br>
<div class="main container">
<legend> <h3>Profile</h3></legend>
<p>Name: <c:out value="${user.lastName}"/>  <c:out value="${user.firstName}" />  <c:out value="${user.middleName}" /></p><br>
<p>Address: <c:out value="${user.address}" /></p><br>
<p>Phone: <c:out value="${user.phone}" /></p><br>
<p>E-mail: <c:out value="${user.email}" /></p><br>

</div>

<%@ include file="include/end-html.jsp" %>