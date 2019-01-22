<%@tag language="java" pageEncoding="UTF-8"%>
<%@tag isELIgnored="false" %>
<%@attribute name="title" required="true" rtexprvalue="true" type="java.lang.String"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:bundle basename = "messages">

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>${title}</title>
        <link href="/resources/styles/main.css" rel="stylesheet">
    </head>
    <body>
        <c:url var="urlLogout" value="/logout.html"/>
        <div class="top">
        	<div class="header">
        		<div class="left">
        			<fmt:message key="application.title"/>
        		</div>
        		<div class="right">
        			<h2><fmt:message key="application.welcome"/> ${currentUser.login}</h2>
					<b><a href="${urlLogout}">
						<fmt:message key="application.button.logout"/>
					</a></b>
        		</div>
       		</div>
       	</div>
       	<div class="container">
       		<div class="navigation">
       			<a href="/user/list.html">Users</a>
       			<a href="/service/list.html">Services</a>
       			<a href="/account/index.html">Account</a>
       			<div class="clearer"><span></span></div>
       		</div>
       		<div class="main">
       			<div class="content">
       				<jsp:doBody/>
       			</div>
        		<div class="clearer"><span></span></div>
        	</div>
       	</div>
    </body>
</html>

</fmt:bundle>