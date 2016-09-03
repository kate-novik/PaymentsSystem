<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="include/begin-html.jsp" %>
<%@ include file="include/header-html-reg.jsp" %>

<div class="main container">
    <div class="row">
    <legend> <h3>Registration form</h3></legend>
    </div>
    <div class="row">
        <div class="col-sm-6 col-sm-offset-3">
            <form class="form-horizontal" action="do?command=Registration" method="POST">
                <div class="form-group">
                    <label for="first_name" class="col-sm-4 control-label">First name</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" name="first_name" id="first_name" placeholder="First name">
                        <span class="help-block">Enter first name</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="middle_name" class="col-sm-4 control-label">Middle name</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" name="middle_name" id="middle_name" placeholder="Middle name">
                        <span class="help-block">Enter middle name</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="last_name" class="col-sm-4 control-label">Last name</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" name="last_name" id="last_name" placeholder="Last name">
                        <span class="help-block">Enter last name</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="phone" class="col-sm-4 control-label">Phone</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" name="phone" id="phone" placeholder="Phone">
                        <span class="help-block">Enter phone in format: xxxxxxxxxxxx</span>
                    </div>
                </div>
                <!--<label class="col-sm-4 control-label">Address</label>-->
                <div class="form-group">

                    <label for="city" class="col-sm-4 control-label">City</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" name="city" id="city" placeholder="City">
                    </div>
                    <label for="street" class="col-sm-4 control-label">Street</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" name="street" id="street" placeholder="Street">
                    </div>
                    <label for="home" class="col-sm-4 control-label">Home</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" name="home" id="home" placeholder="Home">
                    </div>
                    <label for="flat" class="col-sm-4 control-label">Flat</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" name="flat" id="flat" placeholder="Flat">
                    </div>
                </div>
                <div class="form-group">
                    <label for="email" class="col-sm-4 control-label">Email</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" name="email" id="email" placeholder="E-mail">
                        <span class="help-block">Enter email</span>
                    </div>
                </div>
                <!--<label class="col-sm-4 control-label">Passport</label>-->
                <div class="form-group">

                    <label for="numberOfPassport" class="col-sm-4 control-label">Number of passport</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" name="numberOfPassport" id="numberOfPassport" placeholder="Number of passport">
                    </div>
                    <label for="date" class="col-sm-4 control-label">Date of issue</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" name="date" id="date" placeholder="Date of issue">
                    </div>
                    <label for="issued" class="col-sm-4 control-label">Issued</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" name="issued" id="issued" placeholder="Issued">
                    </div>
                </div>
                <div class="form-group">
                    <label for="login" class="col-sm-4 control-label">Login</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" name="login" id="login" placeholder="Login">
                        <span class="help-block">Enter login from 3 to 10 characters</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-sm-4 control-label">Password</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" name="password" id="password" placeholder="Password">
                        <span class="help-block">Enter password from 3 to 15 characters</span>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-4 col-sm-10">
                        <button type="submit" class="btn btn-success">Registration</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="include/end-html.jsp" %>