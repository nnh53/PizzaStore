/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Constant;

/**
 *
 * @author hoangnn
 */
public enum RouteController {
    USER_CONTROLLER_SERVLET("UserController"),
    LOGIN_CONTROLLER("LoginController"),
    LOGOUT_CONTROLLER("LogoutController"),
    CREATE_USER_CONTROLLER("CreateController"),
    DELETE_USER_CONTROLLER("DeleteController"),
    UPDATE_USER_CONTROLLER("UpdateController"),
    SEARCH_USER_CONTROLLER("SearchController"),
    USER_DETAIL_CONTROLLER("UserDetailController");

    private final String message;

    RouteController(String message) {
        this.message = message;
    }

    public String enumToString() {
        return message;
    }
}
