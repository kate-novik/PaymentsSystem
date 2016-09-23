<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="include/header.jsp" %>

<div class="main container">
    <div class="row">
        <div class="pull-right">
            <a href="/profile" type="button" class="btn btn-link"><c:out value="${user.nickname}" /></a>
            <a href="do?command=Payments&id_account=${id_account}"class="btn btn-success" type="button"># of account <c:out value="${id_account}" /></a>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-offset-5">
            <h2>Blocking account</h2>
        </div>
    </div>
    <div class="row">
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <th># of account</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${id_account}</td>
                <td>
                        <a href="/accounts/${id_account}/blocking" class="btn btn-success" aria-label="Center Align" >
                            Block
                        </a>

                </td>
            </tr>
            </tbody>
                    </table>
                </div>
            </div>

<%@ include file="include/footer.jsp" %>