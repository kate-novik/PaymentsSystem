<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="include/begin-html.jsp" %>
<%@ include file="include/header-html.jsp" %>

<br><br>
<div class="main container profile">
    <h3 class="md-display-1">Profile</h3>
    <md-divider></md-divider>
    <ul>
        <li layout="row">
            <h5 class="md-title" flex="25">Name:</h5>
            <p class="md-body-1">
                <c:out value="${user.lastName}"/>  <c:out value="${user.firstName}" />  <c:out value="${user.middleName}"/>
            </p>
        </li>
        <li layout="row">
            <h5 class="md-title" flex="25">Address:</h5>
            <p class="md-body-1">
                <c:out value="${user.address.city}"/>, <c:out value="${user.address.street}"/> <c:out value="${user.address.home}"/> - <c:out value="${user.address.flat}"/>
            </p>
        </li>
        <li layout="row">
            <h5 class="md-title" flex="25">Phone:</h5>
            <p class="md-body-1">
                <c:out value="${user.phone}" />
            </p>
        </li>
        <li layout="row">
            <h5 class="md-title" flex="25">E-mail:</h5>
            <p class="md-body-1">
                <c:out value="${user.email}" />
            </p>
        </li>
    </ul>
</div>

<%@ include file="include/end-html.jsp" %>