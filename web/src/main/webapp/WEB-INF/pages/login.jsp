<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="client/parts/header.jsp" %>

<br><br>
<div class="container">
    <div class="row">
        <legend><h3><spring:message code="login.auth"/></h3></legend>
    </div>
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty msg}">
        <div class="msg">${msg}</div>
    </c:if>
    <c:url value="/login" var="loginUrl"/>
    <form class="form-horizontal col-sm-6 col-sm-offset-3" name="loginForm" action="${loginUrl}" method="POST">
        <div layout-gt-sm="row">
            <md-input-container class="md-block" flex-gt-sm>
                <label><spring:message code="login.login"/></label>
                <input name="username" ng-model="username" required minlength="3" maxlength="10"/>
                <div ng-messages="regForm.email.$error">
                    <div ng-message-exp="['required', 'minlength', 'maxlength']">
                        <spring:message code="login.validLogin"/>
                    </div>
                </div>
            </md-input-container>
        </div>

        <div layout-gt-sm="row">
            <md-input-container class="md-block" flex-gt-sm>
                <label><spring:message code="login.pass"/></label>
                <input type="password" name="password" ng-model="password" required minlength="3" maxlength="15"/>
                <div ng-messages="regForm.email.$error">
                    <div ng-message-exp="['required', 'minlength', 'maxlength']">
                        <spring:message code="login.validPass"/>
                    </div>
                </div>
            </md-input-container>
        </div>
        <div flex-gt-sm="50">
            <md-checkbox ng-model="rememberMe" aria-label="remember-me">
                <spring:message code="login.remember"/>
            </md-checkbox>
        </div>

        <input type="hidden"
               name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div>
            <md-button type="submit"><spring:message code="login.submit"/></md-button>
        </div>
    </form>

</div>