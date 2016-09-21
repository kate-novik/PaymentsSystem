<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<md-table-container>
    <table md-table ng-model="ctrl.selected" md-promise="ctrl.loading">
        <thead md-head md-order="ctrl.query.order" md-on-reorder="ctrl.fetch">
        <tr md-row>
            <th md-column md-order-by="nameToLower"><span># of account</span></th>
            <th md-column md-numeric>Balance</th>
            <th md-column md-numeric>State</th>
            <th md-column md-numeric>Actions</th>
        </tr>
        </thead>
        <tbody md-body>
        <tr md-row md-select="account" md-select-id="id" md-auto-select ng-repeat="account in ctrl.accounts">
            <td md-cell>{{account.id}}</td>
            <td md-cell>{{account.balance}}</td>
            <td md-cell>{{account.state}}</td>
            <td md-cell></td>
        </tr>
        </tbody>
    </table>
</md-table-container>

<md-table-pagination md-limit="ctrl.query.limit"
                     md-limit-options="[5, 10, 15]"
                     md-page="ctrl.query.page"
                     md-total="{{ctrl.accounts.length}}"
                     md-on-paginate="ctrl.fetch"
                     md-page-select>
</md-table-pagination>