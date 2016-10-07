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
                <md-button ng-click="ctrl.create()"><spring:message code="account.create"/></md-button>
            </div>
            <div layout="row" layout-wrap>
                <md-card flex="23"  ng-repeat="account in ctrl.accounts">
                    <md-card-title>
                        <md-card-title-text>
                            <div layout="row" layout-wrap>
                                <span class="md-subhead">{{account.title}}</span>
                                <span flex></span>
                                <md-button ng-click="ctrl.edit($event, account)">
                                    <md-icon>mode_edit</md-icon>
                                </md-button>
                            </div>
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
                                    <md-button ng-disabled="ctrl.isLocked(account)" ng-click="mc.makePayment(account)">
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
                        <md-button ng-click="mc.showPaymentsForAccount(account)"><spring:message code="account.rightMenu"/></md-button>
                    </md-card-actions>
                </md-card>
            </div>
        </div>
        <div ng-switch-when="payment" ng-controller="PayController as pc">
            <form ng-cloak name="payForm">

                <md-input-container class="md-block" flex-gt-sm>
                    <label>Source account</label>
                    <input ng-value="pc.sourceInput" disabled/>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm>
                    <label>Select a service</label>
                    <md-select ng-model="pc.destination" name="serv" required>
                        <md-option ng-repeat="service in pc.services" ng-value="service">
                            {{service.id}} - {{service.title}}
                        </md-option>
                    </md-select>
                </md-input-container>

                <md-input-container class="md-block" flex-gt-sm>
                    <label>Amount</label>
                    <input ng-model="pc.amount" name="amount" required/>
                </md-input-container>

                <md-button ng-click="mc.changeView('accounts')">
                    Cancel
                </md-button>
                <md-button ng-click="pc.ok()">
                    OK
                </md-button>
            </form>
        </div>
        <div ng-switch-when="payments" ng-controller="PaymentsController as pc">
            <div layout="row" layout-wrap>
                <h3><spring:message code="payment.title"/></h3>
                <span flex></span>
                <md-button ng-click="mc.changeView('accounts')"><spring:message code="account.title"/></md-button>
            </div>
            <md-content layout-padding>
                <div layout-gt-sm="row">
                    <md-input-container class="md-block" flex-gt-sm>
                        <label>Min</label>
                        <input name="min" ng-model="pc.filter.minAmountPayment"/>
                    </md-input-container>

                    <md-input-container class="md-block" flex-gt-sm>
                        <label>Max</label>
                        <input name="date" ng-model="pc.filter.maxAmountPayment"/>
                    </md-input-container>

                    <md-input-container class="md-block" flex-gt-sm>
                        <md-datepicker ng-model="pc.filter.payDate" md-max-date="maxDate" md-placeholder=<spring:message code="account.date"/>></md-datepicker>
                    </md-input-container>

                    <md-button type="submit" ng-click="fetch()"><spring:message code="account.filter"/></md-button>
                    <md-button type="submit" ng-click="pc.reset()"><spring:message code="account.reset"/></md-button>
                </div>
            </md-content>
            <div>
                <md-table-container>
                    <table md-table md-promise="pc.loading">
                        <thead md-head md-order="pc.query.order" md-on-reorder="fetch">
                        <tr md-row>
                            <th md-column md-order-by="nameToLower"><span># <spring:message code="payment.number"/></span></th>
                            <th md-column md-numeric><spring:message code="payment.desc"/></th>
                            <th md-column md-numeric><spring:message code="payment.date"/></th>
                            <th md-column md-numeric><spring:message code="payment.state"/></th>
                            <th md-column md-numeric><spring:message code="payment.amount"/></th>
                        </tr>
                        </thead>
                        <tbody md-body>
                        <tr md-row ng-repeat="payment in pc.payments">
                            <td md-cell>{{payment.id}}</td>
                            <td md-cell>{{payment.description}}</td>
                            <td md-cell>{{payment.payDate}}</td>
                            <td md-cell>
                                <md-icon ng-style="{color: pc.isUp(payment) ? 'green' : 'red'}">
                                    {{pc.isUp(payment) ? 'keyboard_arrow_up' : 'keyboard_arrow_down'}}
                                </md-icon>
                            </td>
                            <td md-cell>{{payment.amountPayment}}</td>
                        </tr>
                        </tbody>
                    </table>
                </md-table-container>

                <md-table-pagination md-limit="pc.query.limit"
                                     md-limit-options="[10, 15, 20]"
                                     md-page="pc.query.page"
                                     md-total="{{pc.total}}"
                                     md-on-paginate="fetch">
                </md-table-pagination>
            </div>
            <spring:message code="payment.total"/> - {{pc.total}}
        </div>
    </div>
</div>

<%@ include file="parts/footer.jsp" %>