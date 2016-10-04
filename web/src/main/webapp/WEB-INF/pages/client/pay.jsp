<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ include file="parts/header.jsp" %>

<div class="main container">
    <div class="row">
    <legend> <h3><spring:message code="pay.title"/></h3></legend>
    </div>
    <div class="row">
        <div class="col-sm-6 col-sm-offset-3">
            <form class="form-horizontal" action="${pageContext.request.contextPath}/accounts/${id_account}/pay" method="POST">
                <div class="form-group">
                    <label for="dest" class="col-sm-4 control-label"><spring:message code="pay.dest"/></label>
                    <div class="col-sm-8">
                        <input type="text" name="destination" class="form-control" id="dest" placeholder="Destination">
                         <span class="help-block"><spring:message code="pay.destMessage"/></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="description" class="col-sm-4 control-label"><spring:message code="payment.desc"/></label>
                    <div class="col-sm-8">
                        <input type="text" name="description" class="form-control" id="description" placeholder="Description">
                         <span class="help-block"><spring:message code="pay.descMessage"/></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="amount" class="col-sm-4 control-label"><spring:message code="payment.amount"/></label>
                    <div class="col-sm-8">
                        <input type="text" name="amount" class="form-control" id="amount" placeholder="Amount">
                         <span class="help-block"><spring:message code="pay.amountMessage"/></span>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-4 col-sm-3">
                        <button type="submit" class="btn btn-success"><spring:message code="pay.submit"/></button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="parts/footer.jsp" %>