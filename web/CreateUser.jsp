<%--
    Document   : CreateUser
    Created on : Oct 21, 2024, 10:48:18 AM
    Author     : hoangnn
--%>

<%@page import="Constant.RouteController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String userController = RouteController.USER_CONTROLLER_SERVLET.enumToString();
    String message = (String) request.getAttribute("message");
    String notiColor = "";
    if (message != null) {
        notiColor = message.contains("successfully") ? "green" : "red";
    }

%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Create User</h1>

        <c:set var="message" value="${requestScope.message}"/>
        <c:if test="${message != null}">
            <p style="color: <%= notiColor%>">${message}<p/>
        </c:if>

        <form action="<%= userController%>" method="POST">
            <c:set var="error" value="${requestScope.ErrorDetail}"/>
            <!--UserName-->
            UserName <input type="text" name="txtUserName" value="Uxxx" /> <br/>
            <c:if test="${not empty error.userNameError}">
                <p style="color: red">${error.userNameError}<p/>
            </c:if>
            <!--Password-->
            Password <input type="text" name="txtPassword" value="Enter Password" /> <br/>
            <c:if test="${not empty error.passwordError}">
                <p style="color: red">${error.passwordError}<p/>
            </c:if>
            <!--LastName-->
            LastName <input type="text" name="txtLastName" value="Enter LastName" /> <br/>
            <c:if test="${not empty error.lastNameError}">
                <p style="color: red">${error.lastNameError}<p/>
            </c:if>
            <input type="checkbox" name="chkIsAdmin" disabled="true"/>isAdmim<br/>

            <input type="submit" name="action" value="Create"/>
        </form>

        <a href="Login.jsp">Back to Login</a>
    </body>
</html>
