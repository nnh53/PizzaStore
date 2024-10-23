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
public class UserError {

    //userNameError, lastNameError, passwordError, dupplicateUserNameError
    private String userNameError;
    private String lastNameError;
    private String passwordError;
    private String dupplicateUserNameError;

    public UserError() {
    }

    public UserError(String userNameError, String lastNameError, String passwordError, String dupplicateUserNameError) {
        this.userNameError = userNameError;
        this.lastNameError = lastNameError;
        this.passwordError = passwordError;
        this.dupplicateUserNameError = dupplicateUserNameError;
    }

    public String getUserNameError() {
        return userNameError;
    }

    public void setUserNameError(String userNameError) {
        this.userNameError = userNameError;
    }

    public String getLastNameError() {
        return lastNameError;
    }

    public void setLastNameError(String lastNameError) {
        this.lastNameError = lastNameError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getDupplicateUserNameError() {
        return dupplicateUserNameError;
    }

    public void setDupplicateUserNameError(String dupplicateUserNameError) {
        this.dupplicateUserNameError = dupplicateUserNameError;
    }

}
