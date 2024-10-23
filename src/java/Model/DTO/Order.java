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
public class Order {

    private String orderID; //UNIQUE
    private String customerID;
    private String orderDate;
    private String shipAddress;
    private String status;

    public Order() {
    }

    public Order(String orderID, String customerID, String orderDate, String shipAddress, String status) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.orderDate = orderDate;
        this.shipAddress = shipAddress;
        this.status = status;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" + "orderID=" + orderID + ", customerID=" + customerID + ", orderDate=" + orderDate + ", shipAddress=" + shipAddress + ", status=" + status + '}';
    }

}
