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
public class Account {

    private String accountID; //UNIQUE
    private String userName; //UNIQUE
    private String password;
    private String fullName;
    private String type;
    private String status;

    public Account() {
    }

    public Account(String accountID, String userName, String password, String fullName, String type, String status) {
        this.accountID = accountID;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.type = type;
        this.status = status;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account{" + "accountID=" + accountID + ", userName=" + userName + ", password=" + password + ", fullName=" + fullName + ", type=" + type + ", status=" + status + '}';
    }

}
