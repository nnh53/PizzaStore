/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DTO;

/**
 *
 * @author hoangnn
 */
public class AccountError {

    //userNameError, lastNameError, passwordError, dupplicateUserNameError
    private String userNameError;
    private String passwordError;
    private String fullNameError;
    private String dupplicateUserNameError;

    public AccountError() {
    }

    public AccountError(String userNameError, String passwordError, String fullNameError, String dupplicateUserNameError) {
        this.userNameError = userNameError;
        this.passwordError = passwordError;
        this.fullNameError = fullNameError;
        this.dupplicateUserNameError = dupplicateUserNameError;
    }

    public String getUserNameError() {
        return userNameError;
    }

    public void setUserNameError(String userNameError) {
        this.userNameError = userNameError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getFullNameError() {
        return fullNameError;
    }

    public void setFullNameError(String fullNameError) {
        this.fullNameError = fullNameError;
    }

    public String getDupplicateUserNameError() {
        return dupplicateUserNameError;
    }

    public void setDupplicateUserNameError(String dupplicateUserNameError) {
        this.dupplicateUserNameError = dupplicateUserNameError;
    }

}
