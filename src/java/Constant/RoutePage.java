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
public enum RoutePage {
    SEARCH_PAGE("Search.jsp"),
    LOGIN_PAGE("Login.jsp"),
    MESSAGE_PAGE("DisplayMessage.jsp"),
    NOT_FOUND_PAGE("NotFound.jsp"),
    CREATE_USER_PAGE("CreateUser.jsp"),
    USER_DETAIL_PAGE("UserDetails.jsp");

    private final String message;

    RoutePage(String message) {
        this.message = message;
    }

    public String enumToString() {
        return message;
    }
}
