/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Constant.DBMessage;
import Constant.ErrorMessage;
import Model.DTO.Account;
import Model.DTO.Category;
import Model.DTO.Order;
import Model.DTO.Supplier;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * hàm nào cũng quăng lỗi hết quăng hết ra ngoài cho Error Handler tổng nhận ***
 *
 * @author hoangnn
 */
public class OrderDAO {

    // Convert từ số thành ID dạng Uxxxx
    private static String generateID(int number) {
        String paddedNumber = String.format("%04d", number); //format 4 số
        return "O" + paddedNumber;
    }

    public String getLastOrderID() throws Exception {
        //1.lấy data từ db
        Connection cn = DBConnection.getConnection();
        ResultSet rs = null;
        String sql = "SELECT COUNT(OrderID) FROM dbo.Order";
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

    public Order getOrderByOrderID(String searchID) throws Exception {
        //1.lấy data từ db
        Connection cn = DBConnection.getConnection();
        ResultSet rs = null;
        String sql = "SELECT OrderID,CustomerID,ShipAdress,OrderDate,Status FROM dbo.[Order] WHERE OrderID=?";
        rs = DBConnection.getResultSetFromQuery(cn, sql, searchID); //truyền đúng tham số theo sql ko là đi
        //2.parse/map result
        Order orderRS = null;
        if (rs != null && rs.next()) {
            String orderID = rs.getString(1); //theo lấy 1 2 theo đúng sql
            String customerID = rs.getString(2);
            String shipAdress = rs.getString(3);
            String orderDate = rs.getString(4);
            String status = rs.getString(5);
            orderRS = new Order(orderID, customerID, orderDate, shipAdress, status);
            rs.close();
        }
        cn.close();
        if (orderRS == null) {
            throw new Exception(ErrorMessage.ORDER_NOT_EXISTS.enumToString());
        }
        return orderRS;
    }

    //add thành công trả lại thằng vừa add không thì null hoặc thrown lỗi
    public Order addOrder(Order orderToAdd) throws Exception {
        //fix cứng ID tự tăng
        orderToAdd.setOrderID(this.getLastOrderID());
        //2.add vô db
        Connection cn = DBConnection.getConnection();
        int af = 0;
        String sql = "INSERT dbo.[Order](OrderID, CustomerID, OrderDate, ShipAdress, Status)" + "VALUES (\n"
                + "? ," //-- OrderID - nvarchar(30)\n"
                + "? ," //-- CustomerID - nvarchar(30)\n"
                + "? ," //-- OrderDate - nvarchar(50)\n"
                + "? ," //-- ShipAdress - nvarchar(50)\n"
                + "? ," //-- Status - nvarchar(50)\n"
                + ");";
        af = DBConnection.getAffectedRowsFromUpdate(cn, sql, orderToAdd.getOrderID(), orderToAdd.getCustomerID(), orderToAdd.getOrderDate(), orderToAdd.getShipAddress(), orderToAdd.getStatus()); //truyền đúng tham số theo sql ko là đi
        cn.close();
        return (af > 0) ? orderToAdd : null; // thành công trả chính nó, ko thì null
    }

    //update thành công trả lại thằng vừa update không thì null hoặc thrown lỗi
    public Order updateOrder(Order orderToUpdate) throws Exception {
        Connection cn = DBConnection.getConnection();
        //1.kiểm tra có tồn tại chưa
        Order tmpOrder = this.getOrderByOrderID(orderToUpdate.getOrderID()); //thật ra có thể viết sql để tránh 2 lần connection gọi
        if (tmpOrder != null) {
            throw new Exception(ErrorMessage.ORDER_NOT_EXISTS.enumToString());
        }
        //GIỮ LẠI THUỘC TÍNH GÌ CŨ THÌ LẤY LẠI TMP USER XÀI

        //2.update vô db
        int af = 0;
        String sql = "UPDATE dbo.[Order] SET [OrderID]=?,[CustomerID]=?,[OrderDate]=?,[ShipAdress]=?,[Status]=? WHERE OrderID=?"; //CHÚ Ý CÁI WHERE LÀ CÁI CUỐI
        af = DBConnection.getAffectedRowsFromUpdate(cn, sql, orderToUpdate.getOrderID(), orderToUpdate.getCustomerID(), orderToUpdate.getOrderDate(), orderToUpdate.getShipAddress(), orderToUpdate.getStatus(), orderToUpdate.getOrderID()); //truyền đúng tham số theo sql ko là đi
        cn.close();
        return (af > 0) ? orderToUpdate : null; // thành công trả chính nó, ko thì null
    }

    //delete thành công trả lại thằng vừa delete không thì null hoặc thrown lỗi
    public Order deleteOrderByOrderId(String idToDelete) throws Exception {
        Connection cn = DBConnection.getConnection();
        //1.kiểm tra có tồn tại chưa
        Order tmpOrder = this.getOrderByOrderID(idToDelete); //thật ra có thể viết sql để tránh 2 lần connection gọi
        if (tmpOrder != null) {
            throw new Exception(ErrorMessage.ORDER_NOT_EXISTS.enumToString());
        }
        //GIỮ LẠI THUỘC TÍNH GÌ CŨ THÌ LẤY LẠI TMP USER XÀI

        String statusMessageDisable = DBMessage.DISSABLED.enumToString();
        //2.update vô db
        int af = 0;
        String sql = "UPDATE dbo.[Order] SET [OrderID]=?,[CustomerID]=?,[OrderDate]=?,[ShipAdress]=?,[Status]=? WHERE OrderID=?"; //CHÚ Ý CÁI WHERE LÀ CÁI CUỐI
        af = DBConnection.getAffectedRowsFromUpdate(cn, sql, tmpOrder.getOrderID(), tmpOrder.getCustomerID(), tmpOrder.getOrderDate(), tmpOrder.getShipAddress(), statusMessageDisable, tmpOrder.getOrderID()); //truyền đúng tham số theo sql ko là đi
        cn.close();
        return (af > 0) ? tmpOrder : null; // thành công trả chính nó, ko thì null
    }
}
//    public ArrayList<Category> searchCategoryByCategoryName(String searchValue) throws Exception {
//        //data
//        ArrayList<Category> categoryListHasFound = new ArrayList<>();
//
//        //1.lấy data từ db
//        Connection cn = DBConnection.getConnection();
//        ResultSet rs = null;
//        String sql = "SELECT CategoryID,CategoryName,Description,Status FROM dbo.Category WHERE CategoryName=? LIKE ?";
//        rs = DBConnection.getResultSetFromQuery(cn, sql, searchValue); //truyền đúng tham số theo sql ko là đi
//
//        //2.parse/map result
//        Category categoryRS = null;
//        if (rs != null && rs.next()) {
//            String categoryID = rs.getString(1); //theo lấy 1 2 theo đúng sql
//            String categoryName = rs.getString(2);
//            String description = rs.getString(3);
//            String status = rs.getString(4);
//            categoryRS = new Category(categoryID, categoryName, description, status);
//            rs.close();
//        }
//        cn.close();
//        categoryListHasFound.add(categoryRS);
//        return (categoryListHasFound.isEmpty() == true) ? null : categoryListHasFound;
//    }

