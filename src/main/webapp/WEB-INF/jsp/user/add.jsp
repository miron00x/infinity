<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:bundle basename = "messages">

<fmt:message key="user.list.button.new" var="title"/>
<u:html title="${title}">
    <form action="/user/add.html" method="post">
        <label for="login"><fmt:message key="login.form.login"/>:</label>
        <input id="login" name="login" type="login" placeholder="admin" autofocus required>
        <label for="password"><fmt:message key="login.form.password"/>:</label>
        <input id="password" name="password" type="password" placeholder="root" required>
        <label for="name"><fmt:message key="user.list.table.name"/>:</label>
        <input id="name" name="name" placeholder="root" required>
        <label for="balance"><fmt:message key="user.list.table.balance"/>:</label>
        <input id="balance" name="balance" placeholder="root" required>
        <div id="lower">
            <input type="submit" value="<fmt:message key="user.list.button.new"/>">
        </div>
    </form>
    <c:if test="${not empty param.message}">
        <fmt:message key="${param.message}"/>
    </c:if>
</u:html>

</fmt:bundle>