<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="include/begin-html.jsp" %>
<%@ include file="include/header-html.jsp" %>

<div class="main container">
    <div class="row">
        <div class="pull-right">
            <p>(<a href="do?command=Profile" type="button" class="btn btn-link">
            <c:out value="${user.nickname}" /></a>)
            </p>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-offset-5">
            <h2>Payments</h2>
        </div>
    </div>
    <div class="row">
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <th># of payment</th>
                <th>Source of payment</th>
                <th>Destination of payment</th>
                <th>Description</th>
                <th>Amount</th>
                <th>Date</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="elem" items="${listPayments}" varStatus="status">
            <tr>
                <td><c:out value="${elem.idPayment}" /></td>
                <td><c:out value="${elem.fk_Account_Source}" /></td>
                <td><c:out value="${elem.fk_Account_Destination}" /></td>
                <td><c:out value="${elem.description}" /></td>
                <td><c:out value="${elem.amountPayment}" /></td>
                <td><c:out value="${elem.payDate}" /></td>
            </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>
</div>
<%@ include file="include/end-html.jsp" %>