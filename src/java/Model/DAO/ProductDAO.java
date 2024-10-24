/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Constant.DBMessage;
import Constant.ErrorMessage;
import Model.DTO.Category;
import Model.DTO.Product;
import Model.DTO.Supplier;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 * hàm nào cũng quăng lỗi hết quăng hết ra ngoài cho Error Handler tổng nhận ***
 *
 * @author hoangnn
 */
public class ProductDAO {

    // Convert từ số thành ID dạng Uxxxx
    private static String generateID(int number) {
        String paddedNumber = String.format("%04d", number); //format 4 số
        return "P" + paddedNumber;
    }

    public String getLastProductID() throws Exception {
        //1.lấy data từ db
        Connection cn = DBConnection.getConnection();
        ResultSet rs = null;
        String sql = "SELECT COUNT(ProductID) FROM dbo.Product";
        rs = DBConnection.getResultSetFromQuery(cn, sql); //truyền đúng tham số theo sql ko là đi
        //2.parse/map result
        int count = 0;
        String lastID = null;
        if (rs != null && rs.next()) {
            count = rs.getInt(1); //theo lấy theo đúng sql
            rs.close();
        }
        lastID = generateID(count);
        cn.close();
        return lastID;
    }

    public Product getProductByProductID(String searchID) throws Exception {
        //1.lấy data từ db
        Connection cn = DBConnection.getConnection();
        ResultSet rs = null;
        String sql = "SELECT ProductID, ProductName, SupplierID, CategoryID, UnitPrice, ProductImageUrl, Status FROM dbo.Product WHERE ProductID=?";
        rs = DBConnection.getResultSetFromQuery(cn, sql, searchID); //truyền đúng tham số theo sql ko là đi
        //2.parse/map result
        Product productRS = null;
        if (rs != null && rs.next()) {
            String productID = rs.getString(1); //theo lấy 1 2 theo đúng sql
            String productName = rs.getString(2);
            String supplierID = rs.getString(3);
            String categoryID = rs.getString(4);
            String unitPrice = rs.getString(5);
            String productImageUrl = rs.getString(6);
            String status = rs.getString(7);
            productRS = new Product(productID, productName, supplierID, categoryID, unitPrice, productImageUrl, status);
            rs.close();
        }
        cn.close();
        if (productRS == null) {
            throw new Exception(ErrorMessage.PRODUCT_NOT_EXISTS.enumToString());
        }
        return productRS;
    }

    public Product getProductByProductName(String searchProductName) throws Exception {
        //1.lấy data từ db
        Connection cn = DBConnection.getConnection();
        ResultSet rs = null;
        String sql = "SELECT ProductID, ProductName, SupplierID, CategoryID, UnitPrice, ProductImageUrl, Status FROM dbo.Product WHERE ProductID=?";
        rs = DBConnection.getResultSetFromQuery(cn, sql, searchProductName); //truyền đúng tham số theo sql ko là đi
        //2.parse/map result
        Product productRS = null;
        if (rs != null && rs.next()) {
            String productID = rs.getString(1); //theo lấy 1 2 theo đúng sql
            String productName = rs.getString(2);
            String supplierID = rs.getString(3);
            String categoryID = rs.getString(4);
            String unitPrice = rs.getString(5);
            String productImageUrl = rs.getString(6);
            String status = rs.getString(7);
            productRS = new Product(productID, productName, supplierID, categoryID, unitPrice, productImageUrl, status);
            rs.close();
        }
        cn.close();
        if (productRS == null) {
            throw new Exception(ErrorMessage.PRODUCT_NOT_EXISTS.enumToString());
        }
        return productRS;
    }

    //add thành công trả lại thằng vừa add không thì null hoặc thrown lỗi
    public Product addProduct(Product productToAdd) throws Exception {
        //1.kiểm tra SupplierID có tồn tại chưa
        SupplierDAO suppDao = new SupplierDAO();
        Supplier supTmp = suppDao.getSupplierBySupplierID(productToAdd.getSupplierID());
        if (supTmp != null) {
            throw new Exception(ErrorMessage.SUPPLIER_NOT_EXISTS.enumToString());
        }
        //2.kiểm tra categoryID có tồn tại chưa
        CategoryDAO cateDao = new CategoryDAO();
        Category cateTmp = cateDao.getCategoryByCategoryID(productToAdd.getCategoryID());
        if (cateTmp != null) {
            throw new Exception(ErrorMessage.CATEGORY_NOT_EXISTS.enumToString());
        }
        //3.kiểm tra ProductName có tồn tại chưa
        Product tmpProduct = this.getProductByProductName(productToAdd.getProductName()); //thật ra có thể viết sql để tránh 2 lần connection gọi
        if (tmpProduct != null) {
            throw new Exception(ErrorMessage.PRODUCT_ALREADY_EXISTS.enumToString());
        }
        //fix cứng ID tự tăng
        productToAdd.setProductID(this.getLastProductID());
        //2.add vô db
        Connection cn = DBConnection.getConnection();
        int af = 0;
        String sql = "INSERT dbo.Product(ProductID, ProductName, UnitPrice, ProductImageUrl, Status)" + "VALUES (\n"
                + "? ," //-- ProductID - nvarchar(30)\n"
                + "? ," //-- ProductName - nvarchar(30)\n"
                + "? ," //-- UnitPrice - nvarchar(50)\n"
                + "? ," //-- ProductImageUrl - nvarchar(50)\n"
                + "? ," //-- Status - nvarchar(50)\n"
                + ");";
        af = DBConnection.getAffectedRowsFromUpdate(cn, sql, productToAdd.getProductID(), productToAdd.getProductName(), productToAdd.getUnitPrice(), productToAdd.getUnitPrice(), productToAdd.getProductImageUrl(), productToAdd.getStatus()); //truyền đúng tham số theo sql ko là đi
        cn.close();
        return (af > 0) ? productToAdd : null; // thành công trả chính nó, ko thì null
    }

    //update thành công trả lại thằng vừa update không thì null hoặc thrown lỗi
    public Product updateProduct(Product productToUpdate) throws Exception {
        Connection cn = DBConnection.getConnection();
        //1.kiểm tra có tồn tại chưa
        Product tmpProduct = this.getProductByProductID(productToUpdate.getProductID()); //thật ra có thể viết sql để tránh 2 lần connection gọi
        if (tmpProduct != null) {
            throw new Exception(ErrorMessage.PRODUCT_NOT_EXISTS.enumToString());
        }
        //GIỮ LẠI THUỘC TÍNH GÌ CŨ THÌ LẤY LẠI TMP USER XÀI

        //2.update vô db
        int af = 0;
        String sql = "UPDATE dbo.Category SET [ProductID]=?,[ProductName]=?,[UnitPrice]=?,[ProductImageUrl]=?,[Status]=?  WHERE ProductID=?"; //CHÚ Ý CÁI WHERE LÀ CÁI CUỐI
        af = DBConnection.getAffectedRowsFromUpdate(cn, sql, productToUpdate.getProductID(), productToUpdate.getProductName(), productToUpdate.getUnitPrice(), productToUpdate.getProductImageUrl(), productToUpdate.getStatus(), productToUpdate.getProductID()); //truyền đúng tham số theo sql ko là đi
        cn.close();
        return (af > 0) ? productToUpdate : null; // thành công trả chính nó, ko thì null
    }

    //delete thành công trả lại thằng vừa delete không thì null hoặc thrown lỗi
    public Product deleteProductByProductId(String idToDelete) throws Exception {
        Connection cn = DBConnection.getConnection();
        //1.kiểm tra có tồn tại chưa
        Product tmpProduct = this.getProductByProductID(idToDelete); //thật ra có thể viết sql để tránh 2 lần connection gọi
        if (tmpProduct != null) {
            throw new Exception(ErrorMessage.PRODUCT_NOT_EXISTS.enumToString());
        }
        //GIỮ LẠI THUỘC TÍNH GÌ CŨ THÌ LẤY LẠI TMP USER XÀI

        String statusMessageDisable = DBMessage.DISSABLED.enumToString();
        //2.update vô db
        int af = 0;
        String sql = "UPDATE dbo.Category SET [ProductID]=?,[ProductName]=?,[UnitPrice]=?,[ProductImageUrl]=?,[Status]=?  WHERE ProductID=?"; //CHÚ Ý CÁI WHERE LÀ CÁI CUỐI
        af = DBConnection.getAffectedRowsFromUpdate(cn, sql, tmpProduct.getProductID(), tmpProduct.getProductName(), tmpProduct.getUnitPrice(), tmpProduct.getProductImageUrl(), statusMessageDisable, tmpProduct.getProductID()); //truyền đúng tham số theo sql ko là đi
        cn.close();
        return (af > 0) ? tmpProduct : null; // thành công trả chính nó, ko thì null
    }
}
//    public ArrayList<Product> searchProductByProductName(String searchValue) throws Exception {
//        //data
//        ArrayList<Product> productListHasFound = new ArrayList<>();
//
//        //1.lấy data từ db
//        Connection cn = DBConnection.getConnection();
//        ResultSet rs = null;
//        String sql = "SELECT ProductID,ProductName,Description,Status FROM dbo.Category WHERE ProductName=? LIKE ?";
//        rs = DBConnection.getResultSetFromQuery(cn, sql, searchValue); //truyền đúng tham số theo sql ko là đi
//
//        //2.parse/map result
//        Product productRS = null;
//        if (rs != null && rs.next()) {
//            String productID = rs.getString(1); //theo lấy 1 2 theo đúng sql
//            String productName = rs.getString(2);
//            String description = rs.getString(3);
//            String status = rs.getString(4);
//            productRS = new Product(productID, productName, productID, productID, status, productName, status)
//            rs.close();
//        }
//        cn.close();
//        productListHasFound.add(productRS);
//        return (productListHasFound.isEmpty() == true) ? null : productListHasFound;
