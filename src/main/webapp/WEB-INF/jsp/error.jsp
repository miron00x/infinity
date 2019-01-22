<%@ page isErrorPage="true" %>
<%@page import="org.apache.log4j.Logger"%>
<%@ page import="org.apache.log4j.LogManager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    Logger logger = LogManager.getRootLogger();
    //String str = exception.getMessage();
    //logger.error("Error: " + str);
%>
<html>

<head>
	<title>404</title>
	<link type="text/css" rel="stylesheet" href="/resources/styles/error.css" />
</head>

<body>
    <div id="notfound">
        <div class="notfound">
            <h1>oops!</h1>
            <h2>Sorry, an error occurred.</h2>
            <a href="/index.html">go home</a>
        </div>
    </div>
</body>

</html>