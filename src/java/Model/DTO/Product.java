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
public class Product {

    private String productID; //UNIQUE
    private String productName; //UNIQUE
    private String supplierID;
    private String categoryID;
    private String unitPrice;
    private String productImageUrl;
    private String status;

    public Product() {
    }

    public Product(String productID, String productName, String supplierID, String categoryID, String unitPrice, String productImageUrl, String status) {
        this.productID = productID;
        this.productName = productName;
        this.supplierID = supplierID;
        this.categoryID = categoryID;
        this.unitPrice = unitPrice;
        this.productImageUrl = productImageUrl;
        this.status = status;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product{" + "productID=" + productID + ", productName=" + productName + ", supplierID=" + supplierID + ", categoryID=" + categoryID + ", unitPrice=" + unitPrice + ", productImageUrl=" + productImageUrl + ", status=" + status + '}';
    }

}
