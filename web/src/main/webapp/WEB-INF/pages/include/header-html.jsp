<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<md-toolbar md-scroll-shrink ng-if="true" ng-controller="Controller">
    <div class="md-toolbar-tools">
        <h3>
            <span>{{title}}</span>
        </h3>
        <sec:authorize access="hasRole('ROLE_USER')">
        <md-button aria-label="Profile" ng-href="/profile">
            Profile
        </md-button>
        <md-button aria-label="Accounts" ng-href="/accounts">
            Accounts
        </md-button>
        <md-button aria-label="Payments" ng-href="/payments">
            Payments
        </md-button>
        </sec:authorize>
        <span flex></span>
        <sec:authorize access="hasRole('ADMIN') or hasRole('USER')">
        <md-button aria-label="Logout" ng-href="/logout">
            Logout
        </md-button>
        </sec:authorize>
        <%--<sec:authorize access="isAnonymous()">--%>
            <%--<md-button aria-label="Registration" ng-href="/registration">--%>
                <%--Registration--%>
            <%--</md-button>--%>
        <%--</sec:authorize>--%>
    </div>
</md-toolbar>