<%--
    Document   : UserDetails.jsp
    Created on : Oct 23, 2024, 5:43:59 AM
    Author     : hoangnn
--%>

<%@page import="Model.DTO.User"%>
<%@page import="Constant.RouteController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String userController = RouteController.USER_CONTROLLER_SERVLET.enumToString();
    User currentUserDetail = (User) request.getAttribute("UserDetail");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>User Details!</h1>
        <!--logout-->
        <form method="POST">
            <input type="submit" formaction="LogoutController" value="Logout"/>
        </form>

        <!--NẾU LÀ ADMIN THÌ ĐC SEARCH show info-->
        <c:set var="userLoggedIn" value="${userLoggedIn}"/>
        <c:if test="${userLoggedIn !=null}">
            <c:set var="lastName" value="${userLoggedIn.lastName}"/>
            <c:set var="txtSearchValue" value="${param.txtSearchValue}"/>
        </c:if> 


        <!--tồn tại userDetail-->
        <c:if test="${userDetails != null}">
            <h1>User Detail</h1>
            <c:set var="message" value="${requestScope.message}"/>
            <c:if test="${message != null}">
                <p style="">${message}<p/>
            </c:if>
        </c:if>

        <!--UPDATEEEE-->
        <form action="<%= userController%>" method="POST">
            <c:set var="error" value="${requestScope.ErrorDetail}"/>

            <!--UserName-->
            UserName <input type="text" name="txtUserName" readonly="true" value="<%= currentUserDetail.getUserName()%>" /> <br/>
            <c:if test="${not empty error.userNameError}">
                <p style="color: red">${error.userNameError}<p/>
            </c:if>
            <!--Password-->
            Password <input type="text" name="txtPassword" value="<%= currentUserDetail.getPassword()%>" /> <br/>
            <c:if test="${not empty error.passwordError}">
                <p style="color: red">${error.passwordError}<p/>
            </c:if>
            <!--LastName-->
            LastName <input type="text" name="txtLastName" value="<%= currentUserDetail.getLastName()%>" /> <br/>
            <c:if test="${not empty error.lastNameError}">
                <p style="color: red">${error.lastNameError}<p/>
            </c:if>
            <input type="checkbox" name="chkIsAdmin" disabled="true"/>isAdmim<br/>

            <input type="submit" name="action" value="Create"/>
        </form>
    </body>
</html>
