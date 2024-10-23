/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Constant.ErrorMessage;
import Model.DTO.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * hàm nào cũng quăng lỗi hết quăng hết ra ngoài cho Error Handler tổng nhận ***
 *
 * @author hoangnn
 */
public class UserDAO {

    public User login(String userName, String password) throws Exception {
        //0.kiểm tra userName có tồn tại chưa
        User tmpUser = this.getUserByUserName(userName);
        if (tmpUser == null) {
            //throw new Exception(ErrorMessage.USERNAME_NOT_EXISTS.enumToString()); -hàm getUserByUserName quăng rồi
        }
        //1.lấy data từ db
        Connection cn = DBConnection.getConnection();
        ResultSet rs = null;
        String sql = "SELECT LastName,IsAdmin FROM dbo.Registration WHERE [UserName]=? AND [Password]=?";
        rs = DBConnection.getResultSetFromQuery(cn, sql, userName, password); //truyền đúng tham số theo sql ko là đi
        //2.parse/map result
        User userRS = null;
        if (rs != null && rs.next()) {
            String lastName = rs.getString(1); //theo lấy 1 2 theo đúng sql
            boolean isAdmin = rs.getBoolean(2);
            userRS = new User(userName, password, lastName, isAdmin);
            rs.close();
        }
        cn.close();
        if (userRS == null) {
            throw new Exception(ErrorMessage.USERNAME_OR_PASSWORD_INCORRECT.enumToString());
        }
        return userRS;
    }//end login

    public User getUserByUserName(String userName) throws Exception {
        //1.lấy data từ db
        Connection cn = DBConnection.getConnection();
        ResultSet rs = null;
        String sql = "SELECT LastName,IsAdmin,Password FROM dbo.Registration WHERE [UserName]=?";
        rs = DBConnection.getResultSetFromQuery(cn, sql, userName); //truyền đúng tham số theo sql ko là đi
        //2.parse/map result
        User userRS = null;
        if (rs != null && rs.next()) {
            String lastName = rs.getString(1); //theo lấy 1 2 theo đúng sql
            boolean isAdmin = rs.getBoolean(2);
            String password = rs.getString(3);
            userRS = new User(userName, password, lastName, isAdmin);
            rs.close();
        }
        cn.close();
        if (userRS == null) {
            throw new Exception(ErrorMessage.USERNAME_NOT_EXISTS.enumToString());
        }
        return userRS;
    }//end getUserByUserName

    public ArrayList<User> searchUserByLastName(String searchValue) throws Exception {
        //data
        ArrayList<User> userListHasFound = new ArrayList<>();

        //1.lấy data từ db
        Connection cn = DBConnection.getConnection();
        ResultSet rs = null;
        String sql = "SELECT UserName,LastName,IsAdmin,Password FROM dbo.Registration WHERE [LastName] LIKE ?";
        rs = DBConnection.getResultSetFromQuery(cn, sql, searchValue); //truyền đúng tham số theo sql ko là đi
        //2.parse/map result
        User userRS = null;
        if (rs != null && rs.next()) {
            String userName = rs.getString(1); //theo lấy 1 2 theo đúng sql
            String lastName = rs.getString(2);
            boolean isAdmin = rs.getBoolean(3);
            String password = rs.getString(4);
            userRS = new User(userName, password, lastName, isAdmin);
            userListHasFound.add(userRS);
            rs.close();
        }
        cn.close();
        return (userListHasFound.isEmpty() == true) ? null : userListHasFound;
    }//end searchUserByLastName

    //add thành công trả lại thằng vừa add không thì null hoặc thrown lỗi
    public User addUser(User userToAdd) throws Exception {
        //1.kiểm tra userName có tồn tại chưa
        User tmpUser = this.getUserByUserName(userToAdd.getUserName()); //thật ra có thể viết sql để tránh 2 lần connection gọi
        if (tmpUser != null) {
            throw new Exception(ErrorMessage.USERNAME_ALREADY_EXISTS.enumToString());
        }
        Connection cn = DBConnection.getConnection();
        //2.add vô db
        int af = 0;
        String sql = "INSERT dbo.Registration (UserName, Password, LastName, IsAdmin)" + "VALUES (\n"
                + "? ," //-- UserName - nvarchar(30)\n"
                + "? ," //-- Password - nvarchar(30)\n"
                + "? ," //-- LastName - nvarchar(50)\n"
                + "?   " //  -- IsAdmin - bit\n"
                + ");";
        af = DBConnection.getAffectedRowsFromUpdate(cn, sql, userToAdd.getUserName(), userToAdd.getPassword(), userToAdd.getLastName(), userToAdd.getIsAdmin()); //truyền đúng tham số theo sql ko là đi
        cn.close();
        return (af > 0) ? userToAdd : null; // thành công trả chính nó, ko thì null
    }//end addUser

    //delete thành công trả lại thằng vừa delete không thì null hoặc thrown lỗi
    public User deleteUserByUserName(String userNameToDelete) throws Exception {
        Connection cn = DBConnection.getConnection();
        //1.kiểm tra userName có tồn tại chưa
        User tmpUser = this.getUserByUserName(userNameToDelete);
        if (tmpUser == null) {
            //throw new Exception(ErrorMessage.USERNAME_NOT_EXISTS.enumToString()); -thrown rồi
        }
        //2.delete khỏi db
        int af = 0;
        String sql = "DELETE FROM dbo.Registration WHERE [UserName]=?";
        af = DBConnection.getAffectedRowsFromUpdate(cn, sql, userNameToDelete); //truyền đúng tham số theo sql ko là đi
        cn.close();
        return (af > 0) ? tmpUser : null; // thành công trả chính nó, ko thì null
    }//end deleteUserByUserName

    //update thành công trả lại thằng vừa update không thì null hoặc thrown lỗi
    public User updateUser(User userToUpdate) throws Exception {
        Connection cn = DBConnection.getConnection();
        //1.kiểm tra userName có tồn tại chưa
        User tmpUser = this.getUserByUserName(userToUpdate.getUserName());
        if (tmpUser == null) {
            throw new Exception(ErrorMessage.USERNAME_NOT_EXISTS.enumToString());
        }
        //GIỮ LẠI THUỘC TÍNH GÌ CŨ THÌ LẤY LẠI TMP USER XÀI

        //2.update vô db
        int af = 0;
        String sql = "UPDATE dbo.Registration SET [UserName]=?,[Password]=?,[LastName]=?,[IsAdmin]=? WHERE UserName=?"; //CHÚ Ý CÁI WHERE LÀ CÁI CUỐI
        af = DBConnection.getAffectedRowsFromUpdate(cn, sql, userToUpdate.getUserName(), userToUpdate.getPassword(), userToUpdate.getLastName(), userToUpdate.getIsAdmin(), tmpUser.getUserName()); //truyền đúng tham số theo sql ko là đi
        cn.close();
        return (af > 0) ? userToUpdate : null; // thành công trả chính nó, ko thì null
    }//end updateUser

}
