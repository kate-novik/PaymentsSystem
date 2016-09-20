<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<%@ include file="include/begin-html.jsp" %>
<%@ include file="include/header-html.jsp" %>

<div class="main container" ng-controller="AccountsController as ctrl">
    <div class="row">
        <div class="pull-right">
            (<a  href="/profile" type="button" class="btn btn-link">
            <c:out value="${user.login}" /></a>)
            <a href="/createAccount" class="btn btn-success" type="button">Create Account</a>
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
            <c:forEach var="elem" items="${accounts}" varStatus="status">
            <tr>
                <td><c:out value="${elem.id}" /></td>
                <td><c:out value="${elem.balance}" /></td>
                <td><c:out value="${elem.state}" /></td>

                <td>
                    <div class="btn-group">

                        <a href="/accounts/${elem.id}/getRefill" class="btn btn-default btn-sm" data-toggle="tooltip" title="Refill" aria-label="Left Align">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                        </a>
                        <a href="/accounts/${elem.id}/getPay" class="btn btn-default btn-sm" data-toggle="tooltip" title="Pay" aria-label="Center Align">
                            <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>
                        </a>
                        <a href="/accounts/${elem.id}/getBlock" class="btn btn-default btn-sm" data-toggle="tooltip" title="Block" aria-label="Right Align">
                            <span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
                        </a>
                        <a href="/accounts/${elem.id}/payments" class="btn btn-default btn-sm" data-toggle="tooltip" title="Payments" aria-label="Justify">
                            <span class="glyphicon glyphicon-list" aria-hidden="true"></span>
                        </a>
                    </div>
                </td>
            </tr>
            </c:forEach>
            </tbody>
        </table>

        <md-table-container>
            <table md-table ng-model="ctrl.selected">
                <thead md-head md-order="query.order" md-on-reorder="getDesserts">
                <tr md-row>
                    <th md-column md-order-by="nameToLower"><span># of account</span></th>
                    <th md-column md-numeric>Amount</th>
                    <th md-column md-numeric>State</th>
                    <th md-column md-numeric>Actions</th>
                </tr>
                </thead>
                <tbody md-body>
                <tr md-row md-select="dessert" md-select-id="name" md-auto-select ng-repeat="account in ctrl.accounts">
                    <td md-cell>{{account.id}}</td>
                    <td md-cell>{{account.amount}}</td>
                    <td md-cell>{{account.state}}</td>
                    <td md-cell></td>
                </tr>
                </tbody>
            </table>
        </md-table-container>

        <md-table-pagination md-limit="ctrl.query.limit" md-limit-options="[5, 10, 15]" md-page="ctrl.query.page" md-total="{{ctrl.accounts.length}}" md-page-select></md-table-pagination>

    </div>
</div>

<%@ include file="include/end-html.jsp" %>