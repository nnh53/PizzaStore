<%--
    Document   : NotFound.jsp
    Created on : Oct 23, 2024, 5:29:38 AM
    Author     : hoangnn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>404 NOT FOUND</h1>
        <h2>${requestScope.message}</h2>
    </body>
</html>
