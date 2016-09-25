<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<%@ include file="include/header.jsp" %>

<div class="main container" ng-controller="AccountsController as ctrl">
    <div class="row">
        <div layout="row" layout-wrap>
            <md-card flex="23"  ng-repeat="account in ctrl.accounts">
                <md-card-title>
                    <md-card-title-text>
                        <span class="md-subhead">({{account.state}})</span>
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
                                <md-button ng-click="ctrl.transfer($event, account.id, $index)">
                                    Перевод между счетами
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

<%@ include file="include/footer.jsp" %>