<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="include/begin-html.jsp" %>
<%@ include file="include/header-html.jsp" %>

<div class="main container">
    <div class="row">
        <div class="pull-right">
            (<a  href="do?command=Profile" type="button" class="btn btn-link">
            <c:out value="${user.nickname}" /></a>)
            <a href="do?command=New_Account" class="btn btn-success" type="button">Create Account</a>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-offset-5">
            <h2>Accounts</h2>
        </div>
    </div>
    <div class="row">
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <th># of account</th>
                <th>Amount</th>
                <th>State</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="elem" items="${listAccounts}" varStatus="status">
            <tr>
                <td><c:out value="${elem.idAccount}" /></td>
                <td><c:out value="${elem.balans}" /></td>
                <td><c:out value="${elem.state}" /></td>

                <td>
                    <div class="btn-group">

                        <a href="do?command=Refill&id_account=${elem.idAccount}" class="btn btn-default btn-sm" data-toggle="tooltip" title="Refill" aria-label="Left Align">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                        </a>
                        <a href="do?command=Pay&id_account=${elem.idAccount}" class="btn btn-default btn-sm" data-toggle="tooltip" title="Pay" aria-label="Center Align">
                            <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>
                        </a>
                        <a href="do?command=Block&id_account=${elem.idAccount}&reference=1" class="btn btn-default btn-sm" data-toggle="tooltip" title="Block" aria-label="Right Align">
                            <span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
                        </a>
                        <a href="do?command=Payments&id_account=${elem.idAccount}" class="btn btn-default btn-sm" data-toggle="tooltip" title="Payments" aria-label="Justify">
                            <span class="glyphicon glyphicon-list" aria-hidden="true"></span>
                        </a>
                    </div>
                </td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="include/end-html.jsp" %>