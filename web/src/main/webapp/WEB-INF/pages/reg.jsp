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
                    <form:input path="address.city" ng-model="city"/>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm="33">
                    <label>Street</label>
                    <form:input path="address.street" ng-model="street"/>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm="15">
                    <label>Home</label>
                    <form:input path="address.home" ng-model="home"/>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm="15">
                    <label>Flat</label>
                    <form:input path="address.flat" ng-model="flat"/>
                </md-input-container>
            </div>

            <div layout-gt-sm="row">
                <md-input-container class="md-block" flex-gt-sm>
                    <label>Phone</label>
                    <form:input path="phone" ng-model="city"/>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm>
                    <label>Email</label>
                    <form:input path="email" ng-model="street"/>
                </md-input-container>
            </div>

            <div layout-gt-sm="row">
                <md-input-container class="md-block" flex-gt-sm>
                    <label>Passport number</label>
                    <form:input path="passport.number" ng-model="passportNumber"/>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm>
                    <md-datepicker path="passport.dateOfIssue" ng-model="dateOfIssue" md-placeholder="Date of issued"></md-datepicker>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm>
                    <label>Issued</label>
                    <form:input path="passport.issued" ng-model="issued"/>
                </md-input-container>
            </div>

            <div layout-gt-sm="row">
                <md-input-container class="md-block" flex-gt-sm>
                    <label>Login</label>
                    <form:input path="login" ng-model="city"/>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm>
                    <label>Password</label>
                    <form:input path="password" ng-model="street"/>
                </md-input-container>
            </div>
            <div>
                <md-button type="submit">Registration</md-button>
            </div>
        </form:form>
</md-content>
    <div class="row">
    <legend> <h3>Registration form</h3></legend>
    </div>
    <%--<div class="row">--%>
        <%--<div class="col-sm-6 col-sm-offset-3">--%>
            <%--<form:form class="form-horizontal" modelAttribute="registrationForm" action="/registration" method="post">--%>
                <%--<div class="form-group">--%>
                    <%--<form:label path="firstName" class="col-sm-4 control-label">First name</form:label>--%>
                    <%--<div class="col-sm-8">--%>
                        <%--<form:input type="text" class="form-control" path="firstName" placeholder="First name"/>--%>
                        <%--<span class="help-block">Enter first name</span>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<form:label path="middleName" class="col-sm-4 control-label">Middle name</form:label>--%>
                    <%--<div class="col-sm-8">--%>
                        <%--<form:input type="text" class="form-control" path="middleName" placeholder="Middle name"/>--%>
                        <%--<span class="help-block">Enter middle name</span>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<form:label path="lastName" class="col-sm-4 control-label">Last name</form:label>--%>
                    <%--<div class="col-sm-8">--%>
                        <%--<form:input type="text" class="form-control" path="lastName" placeholder="Last name"/>--%>
                        <%--<span class="help-block">Enter last name</span>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<form:label path="phone" class="col-sm-4 control-label">Phone</form:label>--%>
                    <%--<div class="col-sm-8">--%>
                        <%--<form:input type="text" class="form-control" path="phone" placeholder="Phone"/>--%>
                        <%--<span class="help-block">Enter phone in format: xxxxxxxxxxxx</span>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<!--<label class="col-sm-4 control-label">Address</label>-->--%>
                <%--<div class="form-group">--%>
                    <%--<form:label path="address.city" class="col-sm-4 control-label">City</form:label>--%>
                    <%--<div class="col-sm-8">--%>
                        <%--<form:input type="text" class="form-control" path="address.city" placeholder="City"/>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<form:label path="address.street" class="col-sm-4 control-label">Street</form:label>--%>
                    <%--<div class="col-sm-8">--%>
                        <%--<form:input type="text" class="form-control" path="address.street" placeholder="Street"/>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<form:label path="address.home" class="col-sm-4 control-label">Home</form:label>--%>
                    <%--<div class="col-sm-8">--%>
                        <%--<form:input type="text" class="form-control" path="address.home" placeholder="Home"/>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<form:label path="address.flat" class="col-sm-4 control-label">Flat</form:label>--%>
                    <%--<div class="col-sm-8">--%>
                        <%--<form:input type="text" class="form-control" path="address.flat" placeholder="Flat"/>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<form:label path="email" class="col-sm-4 control-label">Email</form:label>--%>
                    <%--<div class="col-sm-8">--%>
                        <%--<form:input type="text" class="form-control" path="email" placeholder="E-mail"/>--%>
                        <%--<span class="help-block">Enter email</span>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<!--<label class="col-sm-4 control-label">Passport</label>-->--%>
                <%--<div class="form-group">--%>
                    <%--<form:label path="passport.number" class="col-sm-4 control-label">Number of passport</form:label>--%>
                    <%--<div class="col-sm-8">--%>
                        <%--<form:input type="text" class="form-control" path="passport.number" placeholder="Number of passport"/>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--&lt;%&ndash;<div class="form-group">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<form:label path="passport.dateOfIssue" class="col-sm-4 control-label">Date of issue</form:label>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<div class="col-sm-8">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<form:input type="text" class="form-control" path="passport.dateOfIssue" placeholder="Date of issue"/>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                <%--<div class="form-group">--%>
                    <%--<form:label path="passport.issued" class="col-sm-4 control-label">Issued</form:label>--%>
                    <%--<div class="col-sm-8">--%>
                        <%--<form:input type="text" class="form-control" path="passport.issued" placeholder="Issued"/>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<form:label path="login" class="col-sm-4 control-label">Login</form:label>--%>
                    <%--<div class="col-sm-8">--%>
                        <%--<form:input type="text" class="form-control" path="login" placeholder="Login"/>--%>
                        <%--<span class="help-block">Enter login from 3 to 10 characters</span>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<form:label path="password" class="col-sm-4 control-label">Password</form:label>--%>
                    <%--<div class="col-sm-8">--%>
                        <%--<form:input type="text" class="form-control" path="password" placeholder="Password"/>--%>
                        <%--<span class="help-block">Enter password from 3 to 15 characters</span>--%>
                    <%--</div>--%>
                <%--</div>--%>

                <%--<div class="form-group">--%>
                    <%--<div class="col-sm-offset-4 col-sm-10">--%>
                        <%--<button type="submit" class="btn btn-success">Registration</button>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</form:form>--%>
        <%--</div>--%>
    <%--</div>--%>
</div>

<%@ include file="include/end-html.jsp" %>
