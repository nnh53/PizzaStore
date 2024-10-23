<%--
    Document   : Login
    Created on : Oct 21, 2024, 10:30:08 AM
    Author     : hoangnn
--%>

<%@page import="Constant.RoutePage"%>
<%@page import="Constant.RouteController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String loginController = RouteController.LOGIN_CONTROLLER.enumToString();
    String createUserPage = RoutePage.CREATE_USER_PAGE.enumToString();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login1</h1>

        <c:set var="message" value="${requestScope.message}"/>
        <c:if test="${message != null}">
            <p style="color: red">${message}<p/>
        </c:if>

        <form action="<%= loginController%>" method="POST">
            UserName <input type="text" name="txtUserName"/> <br/>
            Password <input type="text" name="txtPassword"/> <br/>
            <input type="submit" name="action" value="Login"/>
            <input type="reset" value="Reset"/>

            <a href="<%= createUserPage%>">Click here to sign up</a><br/>
        </form>

    </body>
</html>

