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
public class Supplier {

    private String supplierID; //UNIQUE
    private String companyName;
    private String address;
    private String phone;
    private String status;

    public Supplier() {
    }

    public Supplier(String supplierID, String companyName, String address, String phone, String status) {
        this.supplierID = supplierID;
        this.companyName = companyName;
        this.address = address;
        this.phone = phone;
        this.status = status;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Supplier{" + "supplierID=" + supplierID + ", companyName=" + companyName + ", address=" + address + ", phone=" + phone + ", status=" + status + '}';
    }

}
