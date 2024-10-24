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
public class OrderDetail {

    private String orderID; //UNIQUE
    private String productID;
    private int quantity;
    private String unitPrice;

    public OrderDetail() {
    }

    public OrderDetail(String orderID, String productID, int quantity, String unitPrice) {
        this.orderID = orderID;
        this.productID = productID;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDetail{" + "orderID=" + orderID + ", productID=" + productID + ", unitPrice=" + unitPrice + ", quantity=" + quantity + '}';
    }

}
