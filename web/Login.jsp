<%-- Document : Login Created on : Oct 21, 2024, 10:30:08 AM Author : hoangnn--%>
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link
            href="https://cdn.jsdelivr.net/npm/bootswatch@5.3.3/dist/cerulean/bootstrap.min.css"
            rel="stylesheet"
            />
        <title>Login</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg bg-primary" data-bs-theme="dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">PizzaStore</a>
                <button
                    class="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarColor01"
                    aria-controls="navbarColor01"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
                    >
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarColor01">
                    <ul class="navbar-nav me-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="#">Pizza </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Categories</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Reviews</a>
                        </li>
                    </ul>
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="#">Register</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Login</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <form
            style="
            margin: 6rem auto;
            width: 40rem;
            background-color: beige;
            padding: 2rem 4rem;
            "
            action="<%= loginController%>" method="POST"
            >
            <fieldset>
                <legend>Please log in here</legend>
                <p>Enter your details below</p>
                <div>
                    <label for="exampleInputEmail1" class="form-label mt-4"
                           >UserName</label
                    >
                    <input
                        type="text  "
                        class="form-control"
                        name="txtUserName"
                        id="exampleInputEmail1"
                        aria-describedby="emailHelp"
                        placeholder="Enter email"
                        />
                </div>
                <div>
                    <label for="exampleInputPassword1" class="form-label mt-4"
                           >Password</label
                    >
                    <input
                        type="password"
                        class="form-control"
                        name="txtPassword"
                        id="exampleInputPassword1"
                        placeholder="Password"
                        autocomplete="off"
                        />
                </div>
                <button
                    style="
                    margin: 1.5rem 0;
                    padding: 0.5rem 2rem;
                    border: none;
                    background-color: blue;
                    color: white;
                    "
                    type="submit" name="action"
                    >
                    Login
                </button>
            </fieldset>
        </form>
    </body>
</html>
