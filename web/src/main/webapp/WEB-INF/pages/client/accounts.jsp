<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ include file="parts/header.jsp" %>

<div class="main container" ng-controller="MainController as mc">
    <div class="row" ng-switch on="mc.view">
        <div ng-switch-when="accounts" ng-controller="AccountsController as ctrl">
            <div layout="row" layout-wrap>
                <h3><spring:message code="account.title"/></h3>
                <span flex></span>
                <md-button ng-click="mc.changeView('payments')">Payments</md-button>
                <md-button ng-click="ctrl.create()"><spring:message code="account.create"/></md-button>
            </div>
            <div layout="row" layout-wrap>
                <md-card flex="23"  ng-repeat="account in ctrl.accounts">
                    <md-card-title>
                        <md-card-title-text>
                            <span class="md-subhead">({{account.state}})</span>
                            <span class="md-subhead"><spring:message code="account.amount"/></span>
                            <span class="md-headline">{{account.balance | currency}}</span>
                        </md-card-title-text>
                    </md-card-title>
                    <md-card-actions layout="row" layout-align="end center">
                        <md-menu>
                            <md-button aria-label="Open demo menu"
                                       ng-click="$mdOpenMenu($event)"><spring:message code="account.leftMenu"/></md-button>
                            <md-menu-content width="4">
                                <md-menu-item>
                                    <md-button ng-disabled="ctrl.isLocked(account)" ng-click="ctrl.transfer($event, account.id, $index)">
                                        <spring:message code="account.transfer"/>
                                    </md-button>
                                </md-menu-item>
                                <md-menu-item>
                                    <md-button ng-disabled="ctrl.isLocked(account)" ng-click="ctrl.pay($event, account.id)">
                                        <spring:message code="account.pay"/>
                                    </md-button>
                                </md-menu-item>
                                <md-menu-divider></md-menu-divider>
                                <md-menu-item>
                                    <md-button ng-click="ctrl.block($event, account.id)" ng-if="!ctrl.isLocked(account)">
                                        <spring:message code="account.lock"/>
                                    </md-button>
                                    <md-button ng-click="ctrl.unblock($event, account.id)" ng-if="ctrl.isLocked(account)">
                                        Разблокировать
                                    </md-button>
                                </md-menu-item>
                            </md-menu-content>
                        </md-menu>
                        <md-button ng-click="mc.showPaymentsForAccount(account.id)"><spring:message code="account.rightMenu"/></md-button>
                    </md-card-actions>
                </md-card>
            </div>
        </div>
        <div ng-switch-when="payments" ng-controller="PaymentsController as pc">
            <div layout="row" layout-wrap>
                <h3>Payments for account ({{mc.selectedAccount}})</h3>
                <span flex></span>
                <md-button ng-click="mc.changeView('accounts')">Accounts</md-button>
            </div>
            <div>
                <md-table-container>
                    <table md-table md-promise="pc.loading">
                        <thead md-head md-order="pc.query.order" md-on-reorder="pc.fetch">
                        <tr md-row>
                            <th md-column md-order-by="nameToLower"><span># of payment</span></th>
                            <th md-column md-numeric>Description</th>
                            <th md-column md-numeric>Date</th>
                            <th md-column md-numeric>State</th>
                            <th md-column md-numeric>Amount</th>
                        </tr>
                        </thead>
                        <tbody md-body>
                        <tr md-row ng-repeat="payment in pc.payments">
                            <td md-cell>{{payment.id}}</td>
                            <td md-cell>{{payment.description}}</td>
                            <td md-cell>{{payment.payDate}}</td>
                            <td md-cell>{{payment.state}}</td>
                            <td md-cell>{{payment.amountPayment}}</td>
                        </tr>
                        </tbody>
                    </table>
                </md-table-container>

                <md-table-pagination md-limit="pc.query.limit"
                                     md-limit-options="[5, 10, 15]"
                                     md-page="pc.query.page"
                                     md-total="{{pc.total}}"
                                     md-on-paginate="fetch">
                </md-table-pagination>
            </div>
            TOTAL - {{pc.total}}
        </div>
    </div>
</div>

<%@ include file="parts/footer.jsp" %>