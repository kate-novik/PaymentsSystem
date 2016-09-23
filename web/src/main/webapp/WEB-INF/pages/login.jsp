<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<%@ include file="include/header.jsp" %>

<br><br>
<div class="container">
    <div class="row">
        <legend><h3>Authorization</h3></legend>
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
                <label>Login</label>
                <input name="username" ng-model="username" required minlength="3" maxlength="10"/>
                <div ng-messages="regForm.email.$error">
                    <div ng-message-exp="['required', 'minlength', 'maxlength']">
                        Enter login from 3 to 10 characters.
                    </div>
                </div>
            </md-input-container>
        </div>

        <div layout-gt-sm="row">
            <md-input-container class="md-block" flex-gt-sm>
                <label>Password</label>
                <input type="password" name="password" ng-model="password" required minlength="3" maxlength="15"/>
                <div ng-messages="regForm.email.$error">
                    <div ng-message-exp="['required', 'minlength', 'maxlength']">
                        Enter password from 3 to 10 characters.
                    </div>
                </div>
            </md-input-container>
        </div>
        <div flex-gt-sm="50">
            <md-checkbox ng-model="rememberMe" aria-label="remember-me">
                Remember me
            </md-checkbox>
        </div>

        <input type="hidden"
               name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div>
            <md-button type="submit">Sign In</md-button>
        </div>
    </form>

</div>