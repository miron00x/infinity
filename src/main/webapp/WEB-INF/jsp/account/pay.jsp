<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:bundle basename = "messages">

    <fmt:message key="account.pay.title" var="title"/>
    <u:html title="${title}">
        <h2>${title}</h2>
        Service to be paid: ${bill.getService().title}
        Credit: ${bill.bill - bill.paid}
        <form action="/account/index.html" method="post">
            <input type="hidden" name="bill_id" value="${bill.id}">
            <label for="toPay"><fmt:message key="account.pay.toPay"/>:</label>
            <input id="toPay" name="toPay" pattern="[0-9]{,10}" autofocus required>
            <div id="lower">
                <input type="submit" value="<fmt:message key="account.pay.pay"/>">
            </div>
        </form>
        <c:if test="${not empty param.message}">
            <fmt:message key="${param.message}"/>
        </c:if>
    </u:html>

</fmt:bundle>