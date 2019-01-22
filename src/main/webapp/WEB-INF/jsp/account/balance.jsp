<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:bundle basename = "messages">

    <fmt:message key="account.balance.title" var="title"/>
    <u:html title="${title}">
        <h2>${title}</h2>
        <fmt:message key="account.index.balance"/>: ${user.balance}
        <form action="/account/balance.html" method="post">
            <label for="balanceUp"><fmt:message key="account.balance.balanceUp"/>:</label>
            <input id="balanceUp" name="balanceUp" pattern="[0-9]{,10}" autofocus required>
            <div id="lower">
                <input type="submit" value="<fmt:message key="account.balance.balanceUpButton"/>">
            </div>
        </form>
    </u:html>

</fmt:bundle>