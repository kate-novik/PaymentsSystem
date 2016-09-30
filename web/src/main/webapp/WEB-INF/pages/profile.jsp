<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file="include/header.jsp" %>

<br><br>
<div class="main container profile">
    <h3 class="md-display-1"><spring:message code="profile.title"/></h3>
    <md-divider></md-divider>
    <ul>
        <li layout="row">
            <h5 class="md-title" flex="25"><spring:message code="profile.name"/>:</h5>
            <p class="md-body-1">
                <c:out value="${user.lastName}"/>  <c:out value="${user.firstName}" />  <c:out value="${user.middleName}"/>
            </p>
        </li>
        <li layout="row">
            <h5 class="md-title" flex="25"><spring:message code="profile.address"/>:</h5>
            <p class="md-body-1">
                <c:out value="${user.address.city}"/>, <c:out value="${user.address.street}"/> <c:out value="${user.address.home}"/> - <c:out value="${user.address.flat}"/>
            </p>
        </li>
        <li layout="row">
            <h5 class="md-title" flex="25"><spring:message code="reg.phone"/>:</h5>
            <p class="md-body-1">
                <c:out value="${user.phone}" />
            </p>
        </li>
        <li layout="row">
            <h5 class="md-title" flex="25"><spring:message code="reg.email"/>:</h5>
            <p class="md-body-1">
                <c:out value="${user.email}" />
            </p>
        </li>
    </ul>
</div>

<%@ include file="include/footer.jsp" %>