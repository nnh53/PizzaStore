/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Constant.ErrorMessage;
import Model.DTO.Account;
import Model.DTO.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * hàm nào cũng quăng lỗi hết quăng hết ra ngoài cho Error Handler tổng nhận ***
 *
 * @author hoangnn
 */
public class AccountDAO {

    // Convert từ số thành ID dạng Uxxxx
    private static String generateID(int number) {
        String paddedNumber = String.format("%04d", number); //format 4 số
        return "A" + paddedNumber;
    }

    public String getLastAccountID() throws Exception {
        //1.lấy data từ db
        Connection cn = DBConnection.getConnection();
        ResultSet rs = null;
        String sql = "SELECT COUNT(accountID) FROM Account";
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

    public Account getAccountByAccountID(String searchID) throws Exception {
        //1.lấy data từ db
        Connection cn = DBConnection.getConnection();
        ResultSet rs = null;
        String sql = "SELECT AccountID,UserName,Password,FullName,Type FROM dbo.Account WHERE [AccountID]=?";
        rs = DBConnection.getResultSetFromQuery(cn, sql, searchID); //truyền đúng tham số theo sql ko là đi
        //2.parse/map result
        Account accountRS = null;
        if (rs != null && rs.next()) {
            String accountID = rs.getString(1); //theo lấy 1 2 theo đúng sql
            String userName = rs.getString(2);
            String password = rs.getString(3);
            String fullName = rs.getString(4);
            String type = rs.getString(5);
            accountRS = new Account(accountID, userName, password, fullName, type);
            rs.close();
        }
        cn.close();
        if (accountRS == null) {
            throw new Exception(ErrorMessage.ACCOUNT_NOT_EXISTS.enumToString());
        }
        return accountRS;
    }//end getAccountByAccountID

    public Account getAccountByUserName(String searchUserName) throws Exception {
        //1.lấy data từ db
        Connection cn = DBConnection.getConnection();
        ResultSet rs = null;
        String sql = "SELECT AccountID,UserName,Password,FullName,Type FROM dbo.Account WHERE [UserName]=?";
        rs = DBConnection.getResultSetFromQuery(cn, sql, searchUserName); //truyền đúng tham số theo sql ko là đi
        //2.parse/map result
        Account accountRS = null;
        if (rs != null && rs.next()) {
            String accountID = rs.getString(1); //theo lấy 1 2 theo đúng sql
            String userName = rs.getString(2);
            String password = rs.getString(3);
            String fullName = rs.getString(4);
            String type = rs.getString(5);
            accountRS = new Account(accountID, userName, password, fullName, type);
            rs.close();
        }
        cn.close();
        if (accountRS == null) {
            throw new Exception(ErrorMessage.ACCOUNT_NOT_EXISTS.enumToString());
        }
        return accountRS;
    }//end getAccountByUserName

    //add thành công trả lại thằng vừa add không thì null hoặc thrown lỗi
    public Account addAccount(Account accountToAdd) throws Exception {
        //1.kiểm tra userName có tồn tại chưa
        Account tmpAccount = this.getAccountByUserName(accountToAdd.getUserName()); //thật ra có thể viết sql để tránh 2 lần connection gọi
        if (tmpAccount != null) {
            throw new Exception(ErrorMessage.USERNAME_ALREADY_EXISTS.enumToString());
        }
        //fix cứng ID tự tăng
        accountToAdd.setAccountID(this.getLastAccountID());
        //2.add vô db
        Connection cn = DBConnection.getConnection();
        int af = 0;
        String sql = "INSERT dbo.Account(AccountID, UserName, Password, FullName, Type)" + "VALUES (\n"
                + "? ," //-- AccountID - nvarchar(30)\n"
                + "? ," //-- UserName - nvarchar(30)\n"
                + "? ," //-- Password - nvarchar(50)\n"
                + "? ," //-- FullName - nvarchar(50)\n"
                + "? ," //-- Type - nvarchar(50)\n"
                + ");";
        af = DBConnection.getAffectedRowsFromUpdate(cn, sql, accountToAdd.getAccountID(), accountToAdd.getUserName(), accountToAdd.getPassword(), accountToAdd.getFullName(), accountToAdd.getType()); //truyền đúng tham số theo sql ko là đi
        cn.close();
        return (af > 0) ? accountToAdd : null; // thành công trả chính nó, ko thì null
    }//end addAccount

//    //delete thành công trả lại thằng vừa delete không thì null hoặc thrown lỗi
//    public Account deleteUserByUserName(String userNameToDelete) throws Exception {
//        Connection cn = DBConnection.getConnection();
//        //1.kiểm tra userName có tồn tại chưa
//        User tmpUser = this.getUserByUserName(userNameToDelete);
//        if (tmpUser == null) {
//            //throw new Exception(ErrorMessage.USERNAME_NOT_EXISTS.enumToString()); -thrown rồi
//        }
//        //2.delete khỏi db
//        int af = 0;
//        String sql = "DELETE FROM dbo.Registration WHERE [UserName]=?";
//        af = DBConnection.getAffectedRowsFromUpdate(cn, sql, userNameToDelete); //truyền đúng tham số theo sql ko là đi
//        cn.close();
//        return (af > 0) ? tmpUser : null; // thành công trả chính nó, ko thì null
//    }//end deleteUserByUserName
//
//    public User login(String userName, String password) throws Exception {
//        //0.kiểm tra userName có tồn tại chưa
//        User tmpUser = this.getUserByUserName(userName);
//        if (tmpUser == null) {
//            //throw new Exception(ErrorMessage.USERNAME_NOT_EXISTS.enumToString()); -hàm getUserByUserName quăng rồi
//        }
//        //1.lấy data từ db
//        Connection cn = DBConnection.getConnection();
//        ResultSet rs = null;
//        String sql = "SELECT LastName,IsAdmin FROM dbo.Registration WHERE [UserName]=? AND [Password]=?";
//        rs = DBConnection.getResultSetFromQuery(cn, sql, userName, password); //truyền đúng tham số theo sql ko là đi
//        //2.parse/map result
//        User userRS = null;
//        if (rs != null && rs.next()) {
//            String lastName = rs.getString(1); //theo lấy 1 2 theo đúng sql
//            boolean isAdmin = rs.getBoolean(2);
//            userRS = new User(userName, password, lastName, isAdmin);
//            rs.close();
//        }
//        cn.close();
//        if (userRS == null) {
//            throw new Exception(ErrorMessage.USERNAME_OR_PASSWORD_INCORRECT.enumToString());
//        }
//        return userRS;
//    }//end login
//
//    public ArrayList<User> searchUserByLastName(String searchValue) throws Exception {
//        //data
//        ArrayList<User> userListHasFound = new ArrayList<>();
//
//        //1.lấy data từ db
//        Connection cn = DBConnection.getConnection();
//        ResultSet rs = null;
//        String sql = "SELECT UserName,LastName,IsAdmin,Password FROM dbo.Registration WHERE [LastName] LIKE ?";
//        rs = DBConnection.getResultSetFromQuery(cn, sql, searchValue); //truyền đúng tham số theo sql ko là đi
//        //2.parse/map result
//        User userRS = null;
//        if (rs != null && rs.next()) {
//            String userName = rs.getString(1); //theo lấy 1 2 theo đúng sql
//            String lastName = rs.getString(2);
//            boolean isAdmin = rs.getBoolean(3);
//            String password = rs.getString(4);
//            userRS = new User(userName, password, lastName, isAdmin);
//            userListHasFound.add(userRS);
//            rs.close();
//        }
//        cn.close();
//        return (userListHasFound.isEmpty() == true) ? null : userListHasFound;
//    }//end searchUserByLastName
//
//    //update thành công trả lại thằng vừa update không thì null hoặc thrown lỗi
//    public User updateUser(User userToUpdate) throws Exception {
//        Connection cn = DBConnection.getConnection();
//        //1.kiểm tra userName có tồn tại chưa
//        User tmpUser = this.getUserByUserName(userToUpdate.getUserName());
//        if (tmpUser == null) {
//            throw new Exception(ErrorMessage.USERNAME_NOT_EXISTS.enumToString());
//        }
//        //GIỮ LẠI THUỘC TÍNH GÌ CŨ THÌ LẤY LẠI TMP USER XÀI
//
//        //2.update vô db
//        int af = 0;
//        String sql = "UPDATE dbo.Registration SET [UserName]=?,[Password]=?,[LastName]=?,[IsAdmin]=? WHERE UserName=?"; //CHÚ Ý CÁI WHERE LÀ CÁI CUỐI
//        af = DBConnection.getAffectedRowsFromUpdate(cn, sql, userToUpdate.getUserName(), userToUpdate.getPassword(), userToUpdate.getLastName(), userToUpdate.getIsAdmin(), tmpUser.getUserName()); //truyền đúng tham số theo sql ko là đi
//        cn.close();
//        return (af > 0) ? userToUpdate : null; // thành công trả chính nó, ko thì null
//    }//end updateUser
}
