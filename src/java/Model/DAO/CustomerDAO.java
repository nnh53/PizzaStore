/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Constant.DBMessage;
import Constant.ErrorMessage;
import Model.DTO.Customer;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 * hàm nào cũng quăng lỗi hết quăng hết ra ngoài cho Error Handler tổng nhận ***
 *
 * @author hoangnn
 */
public class CustomerDAO {

    // Convert từ số thành ID dạng Uxxxx
    private static String generateID(int number) {
        String paddedNumber = String.format("%04d", number); //format 4 số
        return "C" + paddedNumber;
    }

    public String getLastCustomerID() throws Exception {
        //1.lấy data từ db
        Connection cn = DBConnection.getConnection();
        ResultSet rs = null;
        String sql = "SELECT COUNT(CustomerID) FROM Customer";
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

    public Customer getCustomerByCustomerID(String searchID) throws Exception {
        //1.lấy data từ db
        Connection cn = DBConnection.getConnection();
        ResultSet rs = null;
        String sql = "SELECT CustomerID,Password,ContactName,Address,Phone,Status FROM dbo.Customer WHERE [CustomerID]=?";
        rs = DBConnection.getResultSetFromQuery(cn, sql, searchID); //truyền đúng tham số theo sql ko là đi
        //2.parse/map result
        Customer customerRS = null;
        if (rs != null && rs.next()) {
            String customerID = rs.getString(1); //theo lấy 1 2 theo đúng sql
            String password = rs.getString(2);
            String contactName = rs.getString(3);
            String address = rs.getString(4);
            String phone = rs.getString(5);
            String status = rs.getString(6);
            customerRS = new Customer(customerID, password, contactName, address, phone, status);
            rs.close();
        }
        cn.close();
        if (customerRS == null) {
            throw new Exception(ErrorMessage.ACCOUNT_NOT_EXISTS.enumToString());
        }
        return customerRS;
    }//end getCustomerByCustomerID

    public Customer getCustomerByContactName(String searchContactName) throws Exception {
        //1.lấy data từ db
        Connection cn = DBConnection.getConnection();
        ResultSet rs = null;
        String sql = "SELECT CustomerID,Password,ContactName,Address,Phone,Status FROM dbo.Customer WHERE [ContactName]=?";
        rs = DBConnection.getResultSetFromQuery(cn, sql, searchContactName); //truyền đúng tham số theo sql ko là đi
        //2.parse/map result
        Customer customerRS = null;
        if (rs != null && rs.next()) {
            String customerID = rs.getString(1); //theo lấy 1 2 theo đúng sql
            String password = rs.getString(2);
            String contactName = rs.getString(3);
            String address = rs.getString(4);
            String phone = rs.getString(5);
            String status = rs.getString(6);
            customerRS = new Customer(customerID, password, contactName, address, phone, status);
            rs.close();
        }
        cn.close();
        if (customerRS == null) {
            throw new Exception(ErrorMessage.ACCOUNT_NOT_EXISTS.enumToString());
        }
        return customerRS;
    }//end getCustomerByContactName

    //add thành công trả lại thằng vừa add không thì null hoặc thrown lỗi
    public Customer addCustomer(Customer customerToAdd) throws Exception {
        //1.kiểm tra contactName có tồn tại chưa
        //  contact name cho trùng đc, tên người nhận pizza thôi
        //fix cứng ID tự tăng
        customerToAdd.setCustomerID(this.getLastCustomerID());
        //2.add vô db
        Connection cn = DBConnection.getConnection();
        int af = 0;
        String sql = "INSERT dbo.Customer(CustomerID, Password, ContactName, Address, Phone, Status)" + "VALUES (\n"
                + "? ," //-- CustomerID - nvarchar(30)\n"
                + "? ," //-- Password - nvarchar(30)\n"
                + "? ," //-- ContactName - nvarchar(50)\n"
                + "? ," //-- Address - nvarchar(50)\n"
                + "? ," //-- Phone - nvarchar(50)\n"
                + "? ," //-- Status - nvarchar(30)\n"
                + ");";
        af = DBConnection.getAffectedRowsFromUpdate(cn, sql, customerToAdd.getCustomerID(), customerToAdd.getPassword(), customerToAdd.getContactName(), customerToAdd.getAddress(), customerToAdd.getPhone(), customerToAdd.getStatus()); //truyền đúng tham số theo sql ko là đi
        cn.close();
        return (af > 0) ? customerToAdd : null; // thành công trả chính nó, ko thì null
    }//end addCustomer

    //update thành công trả lại thằng vừa update không thì null hoặc thrown lỗi
    public Customer updateCustomer(Customer customerToUpdate) throws Exception {
        Connection cn = DBConnection.getConnection();
        //1.kiểm tra có tồn tại chưa
        Customer tmpCustomer = this.getCustomerByCustomerID(customerToUpdate.getCustomerID()); //thật ra có thể viết sql để tránh 2 lần connection gọi
        if (tmpCustomer != null) {
            throw new Exception(ErrorMessage.ACCOUNT_NOT_EXISTS.enumToString());
        }
        //GIỮ LẠI THUỘC TÍNH GÌ CŨ THÌ LẤY LẠI TMP USER XÀI

        //2.update vô db
        int af = 0;
        String sql = "UPDATE dbo.Account SET [CustomerID]=?,[Password]=?,[ContactName]=?,[Address]=?,[Phone]=?,[Status]=? WHERE CustomerID=?"; //CHÚ Ý CÁI WHERE LÀ CÁI CUỐI
        af = DBConnection.getAffectedRowsFromUpdate(cn, sql, customerToUpdate.getCustomerID(), customerToUpdate.getPassword(), customerToUpdate.getContactName(), customerToUpdate.getAddress(), customerToUpdate.getPhone(), customerToUpdate.getStatus(), customerToUpdate.getCustomerID()); //truyền đúng tham số theo sql ko là đi
        cn.close();
        return (af > 0) ? customerToUpdate : null; // thành công trả chính nó, ko thì null
    }//end updateCustomer

    //delete thành công trả lại thằng vừa delete không thì null hoặc thrown lỗi
    public Customer deleteCustomerByCustomerId(String idToDelete) throws Exception {
        Connection cn = DBConnection.getConnection();
        //1.kiểm tra có tồn tại chưa
        Customer tmpCustomer = this.getCustomerByCustomerID(idToDelete); //thật ra có thể viết sql để tránh 2 lần connection gọi
        if (tmpCustomer != null) {
            throw new Exception(ErrorMessage.ACCOUNT_NOT_EXISTS.enumToString());
        }
        //GIỮ LẠI THUỘC TÍNH GÌ CŨ THÌ LẤY LẠI TMP USER XÀI

        //2.update vô db
        int af = 0;
        String statusMessageDisable = DBMessage.DISSABLED.enumToString();
        String sql = "UPDATE dbo.Account SET [CustomerID]=?,[Password]=?,[ContactName]=?,[Address]=?,[Phone]=?,[Status]=? WHERE CustomerID=?"; //CHÚ Ý CÁI WHERE LÀ CÁI CUỐI
        af = DBConnection.getAffectedRowsFromUpdate(cn, sql, tmpCustomer.getCustomerID(), tmpCustomer.getPassword(), tmpCustomer.getContactName(), tmpCustomer.getAddress(), tmpCustomer.getPhone(), statusMessageDisable, tmpCustomer.getCustomerID()); //truyền đúng tham số theo sql ko là đi
        cn.close();
        return (af > 0) ? tmpCustomer : null; // thành công trả chính nó, ko thì null
    }//end deleteCustomerByCustomerId

//    public ArrayList<Account> searchAccountFullName(String searchValue) throws Exception {
//        //data
//        ArrayList<Account> accountListHasFound = new ArrayList<>();
//
//        //1.lấy data từ db
//        Connection cn = DBConnection.getConnection();
//        ResultSet rs = null;
//        String sql = "SELECT AccountID,UserName,Password,FullName,Type,Status FROM dbo.Account WHERE [FullName] LIKE ?";
//        rs = DBConnection.getResultSetFromQuery(cn, sql, searchValue); //truyền đúng tham số theo sql ko là đi
//        //2.parse/map result
//        Account accountRS = null;
//        if (rs != null && rs.next()) {
//            String accountID = rs.getString(1); //theo lấy 1 2 theo đúng sql
//            String userName = rs.getString(2);
//            String password = rs.getString(3);
//            String fullName = rs.getString(4);
//            String type = rs.getString(5);
//            String status = rs.getString(6);
//            accountRS = new Account(accountID, userName, password, fullName, type, status);
//            accountListHasFound.add(accountRS);
//            rs.close();
//        }
//        cn.close();
//        return (accountListHasFound.isEmpty() == true) ? null : accountListHasFound;
//    }//end searchAccountFullName
}
