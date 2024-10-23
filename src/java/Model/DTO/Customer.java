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
public class Customer {

    private String customerID; //UNIQUE
    private String password;
    private String contactName;
    private String address;
    private String phone;

    public Customer() {
    }

    public Customer(String customerID, String password, String contactName, String address, String phone) {
        this.customerID = customerID;
        this.password = password;
        this.contactName = contactName;
        this.address = address;
        this.phone = phone;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Customer{" + "customerID=" + customerID + ", password=" + password + ", contactName=" + contactName + ", address=" + address + ", phone=" + phone + '}';
    }

}
