<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="parts/header.jsp" %>

<div class="main container">
    <div class="row">
        <legend>
            <h3><spring:message code="reg.title"/></h3>
        </legend>
    </div>
    <md-content layout-padding>
        <form:form class="form-horizontal" name="regForm" modelAttribute="registrationForm" action="/registration" method="post">
            <div layout-gt-sm="row">
                <md-input-container class="md-block" flex-gt-sm>
                    <label><spring:message code="reg.firstName"/></label>
                    <form:input path="firstName" md-maxlength="15" ng-model="firstName" required="true"/>
                    <div ng-messages="regForm.firstName.$error">
                        <div ng-message="required"><spring:message code="reg.required"/></div>
                        <div ng-message="md-maxlength">The name has to be less than 15 characters long.</div>
                    </div>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm>
                    <label><spring:message code="reg.middleName"/></label>
                    <form:input path="middleName" ng-model="middleName" required="true"/>
                    <div ng-messages="regForm.middleName.$error">
                        <div ng-message="required"><spring:message code="reg.required"/></div>
                    </div>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm>
                    <label><spring:message code="reg.lastName"/></label>
                    <form:input path="lastName" ng-model="lastName" required="true"/>
                    <div ng-messages="regForm.lastName.$error">
                        <div ng-message="required"><spring:message code="reg.required"/></div>
                    </div>
                </md-input-container>
            </div>

            <div layout-gt-sm="row">
                <md-input-container class="md-block" flex-gt-sm="33">
                    <label><spring:message code="reg.city"/></label>
                    <form:input path="address.city" ng-model="city" required="true"/>
                    <div ng-messages="regForm.address.city.$error">
                        <div ng-message="required"><spring:message code="reg.required"/></div>
                    </div>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm="33">
                    <label><spring:message code="reg.street"/></label>
                    <form:input path="address.street" ng-model="street" required="true"/>
                    <div ng-messages="regForm.address.street.$error">
                        <div ng-message="required"><spring:message code="reg.required"/></div>
                    </div>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm="15">
                    <label><spring:message code="reg.home"/></label>
                    <form:input path="address.home" ng-model="home" required="true"/>
                    <div ng-messages="regForm.address.home.$error">
                        <div ng-message="required"><spring:message code="reg.required"/></div>
                    </div>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm="15">
                    <label><spring:message code="reg.flat"/></label>
                    <form:input path="address.flat" ng-model="flat"/>
                    <div ng-messages="regForm.address.flat.$error">
                    </div>
                </md-input-container>
            </div>

            <div layout-gt-sm="row">
                <md-input-container class="md-block" flex-gt-sm>
                    <label><spring:message code="reg.phone"/></label>
                    <form:input path="phone" ng-model="phone" required="true" ng-pattern="/^[0-9]{12}$/" md-maxlength="12"/>
                    <div ng-messages="regForm.phone.$error">
                        <div ng-message="required"><spring:message code="reg.required"/></div>
                        <div ng-message="md-maxlength"><spring:message code="reg.validPhone"/></div>
                        <div ng-message="pattern"><spring:message code="reg.correctPhone"/></div>
                    </div>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm>
                    <label><spring:message code="reg.email"/></label>
                    <form:input path="email" ng-model="email"
                                required="true" minlength="8" maxlength="100" ng-pattern="/^.+@.+\..+$/"/>
                    <div ng-messages="regForm.email.$error">
                        <div ng-message-exp="['required', 'minlength', 'maxlength', 'pattern']">
                            <spring:message code="reg.validEmail"/>
                        </div>
                    </div>
                </md-input-container>
            </div>

            <div layout-gt-sm="row">
                <md-input-container class="md-block" flex-gt-sm>
                    <label><spring:message code="reg.numberPassport"/></label>
                    <form:input path="passport.number" ng-model="passportNumber" required="true"/>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm>
                    <form:input type="hidden" path="passport.dateOfIssue" ng-model="dateOfIssue" ng-value="dateOfIssue"/>
                    <md-datepicker ng-model="dateOfIssue" md-placeholder=<spring:message code="reg.dataIssued"/> required></md-datepicker>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm>
                    <label><spring:message code="reg.issued"/></label>
                    <form:input path="passport.issued" ng-model="issued" required="true"/>
                </md-input-container>
            </div>

            <div layout-gt-sm="row">
                <md-input-container class="md-block" flex-gt-sm>
                    <label><spring:message code="login.login"/></label>
                    <form:input path="login" ng-model="login" required="true" minlength="3" maxlength="10"/>
                    <div ng-messages="regForm.login.$error">
                        <div ng-message-exp="['required', 'minlength', 'maxlength']">
                            <spring:message code="login.validLogin"/>
                        </div>
                    </div>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm>
                    <label><spring:message code="login.pass"/></label>
                    <form:input type="password" path="password" ng-model="password" required="true" minlength="3" maxlength="15"/>
                    <div ng-messages="regForm.password.$error">
                        <div ng-message-exp="['required', 'minlength', 'maxlength']">
                            <spring:message code="login.validPass"/>
                        </div>
                    </div>
                </md-input-container>
            </div>
            <div>
                <md-button type="submit"><spring:message code="reg.submit"/></md-button>
            </div>
        </form:form>
    </md-content>
</div>

<%@ include file="parts/footer.jsp" %>
