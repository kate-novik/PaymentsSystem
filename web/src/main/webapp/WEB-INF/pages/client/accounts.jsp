<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ include file="parts/header.jsp" %>

<div class="main container" ng-controller="AccountsController as ctrl">
    <div class="row">
        <div layout="row" layout-wrap>
            <h3><spring:message code="account.title"/></h3>
            <span flex></span>
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
                    <md-button><spring:message code="account.rightMenu"/></md-button>
                </md-card-actions>
            </md-card>
        </div>
    </div>
</div>

<%@ include file="parts/footer.jsp" %>