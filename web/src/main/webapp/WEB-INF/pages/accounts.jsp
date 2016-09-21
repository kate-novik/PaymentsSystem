<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<%@ include file="include/begin-html.jsp" %>
<%@ include file="include/header-html.jsp" %>

<div class="main container" ng-controller="AccountsController as ctrl">
    <div class="row" ng-init="ctrl._csrfName=${_csrf.parameterName}; ctrl._csrfToken=${_csrf.token}">
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
        <div flex-xs layout="row">
            <md-card flex-gt-xs="33" md-theme="dark-blue" md-theme-watch ng-repeat="account in ctrl.accounts">
                <md-card-title>
                    <md-card-title-text>
                        <span class="md-subhead">Остаток на счете</span>
                        <span class="md-headline">{{account.balance | currency}}</span>
                    </md-card-title-text>
                </md-card-title>
                <md-card-actions layout="row" layout-align="end center">
                    <md-menu>
                        <md-button aria-label="Open demo menu"
                                   ng-click="$mdOpenMenu($event)">Действия</md-button>
                        <md-menu-content width="4">
                            <md-menu-item>
                                <md-button ng-click="ctrl.refill($event, account.id, $index)">
                                    Пополнение счета
                                </md-button>
                            </md-menu-item>
                            <md-menu-item>
                                <md-button ng-click="ctrl.pay($event, account.id)">
                                    Оплата услуг
                                </md-button>
                            </md-menu-item>
                            <md-menu-divider></md-menu-divider>
                            <md-menu-item>
                                <md-button ng-click="ctrl.block($event, account.id)">
                                    Заблокировать
                                </md-button>
                            </md-menu-item>
                        </md-menu-content>
                    </md-menu>
                    <md-button>Выписка</md-button>
                </md-card-actions>
            </md-card>
        </div>
    </div>
</div>

<%@ include file="include/end-html.jsp" %>