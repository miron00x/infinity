<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:bundle basename = "messages">

<fmt:message key="account.index.title" var="title"/>
<u:html title="${title}">
    <h2>${title}</h2>
    <fmt:message key="user.list.table.name"/> : ${user.name}<br/>
    <fmt:message key="user.list.table.login"/>: ${user.login}<br/>
    <fmt:message key="login.form.password"/>: ${user.password}<br/>
    <c:if test="${currentUser.role != 'ADMINISTRATOR'}">
        <h><fmt:message key="account.index.balance"/>:</h>
        ${currentUser.balance}
        <c:url var="urlUpBalance" value="/account/balance.html"/>
        <a href="${urlUpBalance}"><fmt:message key="account.index.button.upBalance"/></a><br/>
        Services:<br/>
        <table>
            <tr>
                <th><fmt:message key="account.index.table.service"/></th>
                <th><fmt:message key="account.index.table.credit"/></th>
                <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
            </tr>
            <c:forEach var="bill" items="${bills}">
                <tr>
                    <td>${bill.getService().title}</td>
                    <td>${bill.bill - bill.paid}</td>
                    <c:url var="urlBillPay" value="/account/pay.html">
                        <c:param name="id" value="${bill.id}"/>
                    </c:url>
                    <c:url var="urlDisable" value="/account/index.html">
                        <c:param name="id" value="${bill.id}"/>
                    </c:url>
                    <td><a href="${urlBillPay}"><fmt:message key="account.index.button.pay"/></a></td>
                    <td><a href="${urlDisable}"><fmt:message key="account.index.button.disable"/></a></td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${not empty param.message}">
            <fmt:message key="${param.message}"/>
        </c:if>
    </c:if>
</u:html>

</fmt:bundle>