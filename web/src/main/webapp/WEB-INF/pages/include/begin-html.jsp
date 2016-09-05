<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv=Content-Type content="text/html;charset=UTF-8">
    <title>System of payments</title>
    <link rel="stylesheet" href="resources/styles/bootstrap.css">
    <link rel="stylesheet" href="resources/styles/style.css">
</head>
<body ng-app="payments">
<md-toolbar md-scroll-shrink ng-if="true" ng-controller="Controller">
    <div class="md-toolbar-tools">
        <h3>
            <span>{{title}}</span>
        </h3>
    </div>
</md-toolbar>

