<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="include/begin-html.jsp" %>
<%@ include file="include/header-html.jsp" %>

<div class="main container">
    <div class="row">
        <legend>
            <h3>Registration form</h3>
        </legend>
    </div>
    <md-content layout-padding>
        <form:form class="form-horizontal" name="regForm" modelAttribute="registrationForm" action="/registration" method="post">
            <div layout-gt-sm="row">
                <md-input-container class="md-block" flex-gt-sm>
                    <label>First name</label>
                    <form:input path="firstName" md-maxlength="15" ng-model="firstName" required="true"/>
                    <div ng-messages="regForm.firstName.$error">
                        <div ng-message="required">First Name is required.</div>
                        <div ng-message="md-maxlength">The name has to be less than 15 characters long.</div>
                    </div>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm>
                    <label>Middle name</label>
                    <form:input path="middleName" ng-model="middleName" required="true"/>
                    <div ng-messages="regForm.middleName.$error">
                        <div ng-message="required">Middle Name is required.</div>
                    </div>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm>
                    <label>Last name</label>
                    <form:input path="lastName" ng-model="lastName" required="true"/>
                    <div ng-messages="regForm.lastName.$error">
                        <div ng-message="required">Last Name is required.</div>
                    </div>
                </md-input-container>
            </div>

            <div layout-gt-sm="row">
                <md-input-container class="md-block" flex-gt-sm="33">
                    <label>City</label>
                    <form:input path="address.city" ng-model="city" required="true"/>
                    <div ng-messages="regForm.address.city.$error">
                        <div ng-message="required">City is required.</div>
                    </div>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm="33">
                    <label>Street</label>
                    <form:input path="address.street" ng-model="street" required="true"/>
                    <div ng-messages="regForm.address.street.$error">
                        <div ng-message="required">Street is required.</div>
                    </div>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm="15">
                    <label>Home</label>
                    <form:input path="address.home" ng-model="home" required="true"/>
                    <div ng-messages="regForm.address.home.$error">
                        <div ng-message="required">Home is required.</div>
                    </div>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm="15">
                    <label>Flat</label>
                    <form:input path="address.flat" ng-model="flat"/>
                    <div ng-messages="regForm.address.flat.$error">
                    </div>
                </md-input-container>
            </div>

            <div layout-gt-sm="row">
                <md-input-container class="md-block" flex-gt-sm>
                    <label>Phone</label>
                    <form:input path="phone" ng-model="phone" required="true" ng-pattern="/^[0-9]{12}$/" md-maxlength="12"/>
                    <div ng-messages="regForm.phone.$error">
                        <div ng-message="required">Phone is required.</div>
                        <div ng-message="md-maxlength">The phone has to be less than 12 characters long.</div>
                        <div ng-message="pattern">Enter phone in correct format.</div>
                    </div>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm>
                    <label>Email</label>
                    <form:input path="email" ng-model="email"
                                required="true" minlength="8" maxlength="100" ng-pattern="/^.+@.+\..+$/"/>
                    <div ng-messages="regForm.email.$error">
                        <div ng-message-exp="['required', 'minlength', 'maxlength', 'pattern']">
                            Your email must be between 8 and 100 characters long and look like an e-mail address.
                        </div>
                    </div>
                </md-input-container>
            </div>

            <div layout-gt-sm="row">
                <md-input-container class="md-block" flex-gt-sm>
                    <label>Passport number</label>
                    <form:input path="passport.number" ng-model="passportNumber" required="true"/>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm>
                    <md-datepicker path="passport.dateOfIssue" ng-model="dateOfIssue" md-placeholder="Date of issued" required></md-datepicker>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm>
                    <label>Issued</label>
                    <form:input path="passport.issued" ng-model="issued" required="true"/>
                </md-input-container>
            </div>

            <div layout-gt-sm="row">
                <md-input-container class="md-block" flex-gt-sm>
                    <label>Login</label>
                    <form:input path="login" ng-model="login" required="true" minlength="3" maxlength="10"/>
                    <div ng-messages="regForm.login.$error">
                        <div ng-message-exp="['required', 'minlength', 'maxlength']">
                            Enter login from 3 to 10 characters.
                        </div>
                    </div>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm>
                    <label>Password</label>
                    <form:input type="password" path="password" ng-model="password" required="true" minlength="3" maxlength="15"/>
                    <div ng-messages="regForm.password.$error">
                        <div ng-message-exp="['required', 'minlength', 'maxlength']">
                            Enter password from 3 to 10 characters.
                        </div>
                    </div>
                </md-input-container>
            </div>
            <div>
                <md-button type="submit">Registration</md-button>
            </div>
        </form:form>
    </md-content>
</div>

<%@ include file="include/end-html.jsp" %>
