<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv=Content-Type content="text/html;charset=UTF-8">
    <title>System of payments</title>
    <link rel="stylesheet" href="resources/styles/bootstrap.css">
    <link rel="stylesheet" href="resources/styles/style.css">
    <script src="resources/js/payments.js"> </script>

</head>
<body ng-app="payments">

<md-toolbar md-scroll-shrink ng-controller="Controller">
    <div class="md-toolbar-tools">
        <h3>
            <a ng-href="/">{{title}}</a>
        </h3>
        <span flex></span>
        <sec:authorize access="hasRole('USER')">
            <md-button aria-label="Profile" ng-href="/profile">
                <md-icon md-svg-src="account box"></md-icon>
                <c:out value="${user.login}" />
            </md-button>
            <md-button aria-label="Logout" ng-href="/getLogout">
                Logout
            </md-button>
        </sec:authorize>
        <sec:authorize access="isAnonymous()">
            <md-button aria-label="Registration" ng-href="/registration">
                Registration
            </md-button>
        </sec:authorize>
    </div>
</md-toolbar>