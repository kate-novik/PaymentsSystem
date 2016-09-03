<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="include/begin-html.jsp" %>
<%@ include file="include/header-html.jsp" %>

<div class="main container">
    <div class="row">
    <legend> <h3>Paying form</h3></legend>
    </div>
    <div class="row">
        <div class="col-sm-6 col-sm-offset-3">
            <form class="form-horizontal" action="do?command=Pay&id_account=${id_account}" method="POST">
                <div class="form-group">
                    <label for="dest" class="col-sm-4 control-label">Destination</label>
                    <div class="col-sm-8">
                        <input type="text" name="destination" class="form-control" id="dest" placeholder="Destination">
                         <span class="help-block">Enter account of destination</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="description" class="col-sm-4 control-label">Description</label>
                    <div class="col-sm-8">
                        <input type="text" name="description" class="form-control" id="description" placeholder="Description">
                         <span class="help-block">Enter description of payment</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="amount" class="col-sm-4 control-label">Amount</label>
                    <div class="col-sm-8">
                        <input type="text" name="amount" class="form-control" id="amount" placeholder="Amount">
                         <span class="help-block">Enter amount of payment</span>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-4 col-sm-3">
                        <button type="submit" class="btn btn-success">Pay</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="include/end-html.jsp" %>