<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:bundle basename = "messages">

<fmt:message key="user.list.title" var="title"/>
<u:html title="${title}">
    <c:if test="${currentUser.role == 'ADMINISTRATOR'}">
        <h2><a href="/user/add.html"><fmt:message key="user.list.button.new"/></a></h2>
    </c:if>
    <h2>${title}</h2>
        <table>
            <tr>
                <th><fmt:message key="user.list.table.login"/></th>
                <th><fmt:message key="user.list.table.name"/></th>
                <c:if test="${currentUser.role == 'ADMINISTRATOR'}">
                    <th><fmt:message key="user.list.table.bill"/></th>
                    <th><fmt:message key="user.list.table.status"/></th>
                    <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                </c:if>
            </tr>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.login}</td>
                    <td>${user.name}</td>
                    <c:if test="${currentUser.role == 'ADMINISTRATOR'}">
                        <td>${user.credit}</td>
                        <c:if test="${user.status}">
                            <td><fmt:message key="user.list.table.unblocked"/></td>
                        </c:if>
                        <c:if test="${not user.status}">
                            <td><fmt:message key="user.list.table.blocked"/></td>
                        </c:if>
                        <c:url var="urlUserBlock" value="/user/block.html">
                            <c:param name="id" value="${user.id}"/>
                        </c:url>
                        <c:if test="${user.status}">
                            <td><a href="${urlUserBlock}"><fmt:message key="user.list.button.block"/></a></td>
                        </c:if>
                        <c:if test="${not user.status}">
                            <td><a href="${urlUserBlock}"><fmt:message key="user.list.button.unblock"/></a></td>
                        </c:if>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
</u:html>

</fmt:bundle>