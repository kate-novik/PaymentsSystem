<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<%@ include file="include/header.jsp" %>

<div class="main container">
    <div class="row">
    <legend> <h3>Ending session</h3></legend>
    </div>
    <div class="row">
        <div class="col-sm-6 col-sm-offset-3">

                <div class="form-group">
                    <div class="col-sm-offset-9 col-sm-3">
                        <a href="/logout" type="submit" class="btn btn-success">Log Out</a>
                    </div>
                </div>

        </div>
    </div>
</div>

<%@ include file="include/footer.jsp" %>