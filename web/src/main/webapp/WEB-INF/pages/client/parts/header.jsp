<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv=Content-Type content="text/html;charset=UTF-8">
    <title>Payments system</title>
    <link rel="stylesheet" href="resources/styles/bootstrap.css">
    <link rel="stylesheet" href="resources/styles/style.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="resources/js/payments.js"> </script>

</head>
<body ng-app="payments">

<md-toolbar md-scroll-shrink ng-controller="HeaderController">
    <div class="md-toolbar-tools">
        <h3>
            <a ng-href="/">{{title}}</a>
        </h3>
        <span flex></span>
        <sec:authorize access="isAuthenticated()">
            <md-button aria-label="Profile" ng-href="${pageContext.request.contextPath}/profile">
                <md-icon md-svg-src="account box"></md-icon>
                <c:out value="${user.login}" />
            </md-button>
            <md-button aria-label="Logout" ng-href="${pageContext.request.contextPath}/getLogout">
                <spring:message code="logout.logout"/>
            </md-button>
        </sec:authorize>
        <sec:authorize access="isAnonymous()">
            <md-button aria-label="Registration" ng-href="${pageContext.request.contextPath}/registration">
                <spring:message code="reg.name"/>
            </md-button>
        </sec:authorize>
        <a href="?myLocale=en">En</a>|<a href="?myLocale=ru">Ru</a>
    </div>
</md-toolbar>