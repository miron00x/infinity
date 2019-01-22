<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:bundle basename = "messages">

<fmt:message key="service.list.title" var="title"/>
<u:html title="${title}">
    <h2>${title}</h2>
        <table>
            <tr>
                <th><fmt:message key="service.list.table.id"/></th>
                <th><fmt:message key="service.list.table.title"/></th>
                <th><fmt:message key="service.list.table.price"/></th>
                <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
            </tr>
            <c:forEach var="service" items="${services}">
                <tr>
                    <td>${service.id}</td>
                    <td>${service.title}</td>
                    <td>${service.price}</td>
                    <c:url var="urlServiceBuy" value="/service/list.html">
                        <c:param name="id" value="${service.id}"/>
                    </c:url>
                    <c:if test="${currentUser.role != 'ADMINISTRATOR'}">
                        <td><a href="${urlServiceBuy}"><fmt:message key="service.list.button.buy"/></a></td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${not empty param.message}">
            <fmt:message key="${param.message}"/>
        </c:if>
</u:html>

</fmt:bundle>