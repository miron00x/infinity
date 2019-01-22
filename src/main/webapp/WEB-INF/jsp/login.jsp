<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:bundle basename = "messages">
<html>
    <head>
        <meta charset="utf-8">
        <title>Telephone exchange</title>
        <link href="/resources/styles/style.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        <div id="container">
            <form action="/login.html" method="post">
                <label for="name"><fmt:message key="login.form.login"/>:</label>
                <input id="login" name="login" type="name" placeholder="admin" autofocus required>
                <label for="username"><fmt:message key="login.form.password"/>:</label>
                <input id="password" name="password" type="password" placeholder="root" required>
                <div id="lower">
                    <input type="submit" value="<fmt:message key="login.button.login"/>">
                </div>
            </form>
            <c:if test="${not empty param.message}">
                <fmt:message key="${param.message}"/>
            </c:if>
        </div>
    </body>
</html>
</fmt:bundle>