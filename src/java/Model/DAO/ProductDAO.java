///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Model.DAO;
//
//import Constant.DBMessage;
//import Constant.ErrorMessage;
//import Model.DTO.Account;
//import Model.DTO.Category;
//import Model.DTO.Supplier;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//
///**
// * hàm nào cũng quăng lỗi hết quăng hết ra ngoài cho Error Handler tổng nhận ***
// *
// * @author hoangnn
// */
//public class ProductDAO {
//
//    public ArrayList<Product> searchProductByProductName(String searchValue) throws Exception {
//        //data
//        ArrayList<Product> productListHasFound = new ArrayList<>();
//
//        //1.lấy data từ db
//        Connection cn = DBConnection.getConnection();
//        ResultSet rs = null;
//        String sql = "SELECT ProductID,ProductName,Description,Status FROM dbo.Category WHERE CategoryName=? LIKE ?";
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
//
//    // Convert từ số thành ID dạng Uxxxx
//    private static String generateID(int number) {
//        String paddedNumber = String.format("%04d", number); //format 4 số
//        return "C" + paddedNumber;
//    }
//
//    public String getLastCategoryID() throws Exception {
//        //1.lấy data từ db
//        Connection cn = DBConnection.getConnection();
//        ResultSet rs = null;
//        String sql = "SELECT COUNT(CategoryID) FROM dbo.Category";
//        rs = DBConnection.getResultSetFromQuery(cn, sql); //truyền đúng tham số theo sql ko là đi
//        //2.parse/map result
//        int count = 0;
//        String lastID = null;
//        if (rs != null && rs.next()) {
//            count = rs.getInt(1); //theo lấy theo đúng sql
//            rs.close();
//        }
//        lastID = generateID(count);
//        cn.close();
//        return lastID;
//    }
//
//    public Category getCategoryByCategoryID(String searchID) throws Exception {
//        //1.lấy data từ db
//        Connection cn = DBConnection.getConnection();
//        ResultSet rs = null;
//        String sql = "SELECT CategoryID,CategoryName,Description,Status FROM dbo.Category WHERE CategoryID=?";
//        rs = DBConnection.getResultSetFromQuery(cn, sql, searchID); //truyền đúng tham số theo sql ko là đi
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
//        if (categoryRS == null) {
//            throw new Exception(ErrorMessage.CATEGORY_NOT_EXISTS.enumToString());
//        }
//        return categoryRS;
//    }
//
//    public Category getCategoryByCategoryName(String searchCategoryName) throws Exception {
//        //1.lấy data từ db
//        Connection cn = DBConnection.getConnection();
//        ResultSet rs = null;
//        String sql = "SELECT CategoryID,CategoryName,Description,Status FROM dbo.Category WHERE CategoryName=?";
//        rs = DBConnection.getResultSetFromQuery(cn, sql, searchCategoryName); //truyền đúng tham số theo sql ko là đi
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
//        if (categoryRS == null) {
//            throw new Exception(ErrorMessage.CATEGORY_NOT_EXISTS.enumToString());
//        }
//        return categoryRS;
//    }
//
//    //add thành công trả lại thằng vừa add không thì null hoặc thrown lỗi
//    public Category addCategory(Category categoryToAdd) throws Exception {
//        //1.kiểm tra Categoryname có tồn tại chưa
//        Category tmpCategory = this.getCategoryByCategoryName(categoryToAdd.getCategoryName()); //thật ra có thể viết sql để tránh 2 lần connection gọi
//        if (tmpCategory != null) {
//            throw new Exception(ErrorMessage.CATEGORY_ALREADY_EXISTS.enumToString());
//        }
//        //fix cứng ID tự tăng
//        categoryToAdd.setCategoryID(this.getLastCategoryID());
//        //2.add vô db
//        Connection cn = DBConnection.getConnection();
//        int af = 0;
//        String sql = "INSERT dbo.Category(CategoryID, CategoryName, Description, Status)" + "VALUES (\n"
//                + "? ," //-- CategoryID - nvarchar(30)\n"
//                + "? ," //-- CategoryName - nvarchar(30)\n"
//                + "? ," //-- Description - nvarchar(50)\n"
//                + "? ," //-- Status - nvarchar(50)\n"
//                + ");";
//        af = DBConnection.getAffectedRowsFromUpdate(cn, sql, categoryToAdd.getCategoryID(), categoryToAdd.getCategoryName(), categoryToAdd.getDescription(), categoryToAdd.getStatus()); //truyền đúng tham số theo sql ko là đi
//        cn.close();
//        return (af > 0) ? categoryToAdd : null; // thành công trả chính nó, ko thì null
//    }
//
//    //update thành công trả lại thằng vừa update không thì null hoặc thrown lỗi
//    public Category updateCategory(Category categoryToUpdate) throws Exception {
//        Connection cn = DBConnection.getConnection();
//        //1.kiểm tra có tồn tại chưa
//        Category tmpCategory = this.getCategoryByCategoryID(categoryToUpdate.getCategoryID()); //thật ra có thể viết sql để tránh 2 lần connection gọi
//        if (tmpCategory != null) {
//            throw new Exception(ErrorMessage.CATEGORY_NOT_EXISTS.enumToString());
//        }
//        //GIỮ LẠI THUỘC TÍNH GÌ CŨ THÌ LẤY LẠI TMP USER XÀI
//
//        //2.update vô db
//        int af = 0;
//        String sql = "UPDATE dbo.Category SET [CategoryID]=?,[CategoryName]=?,[Description]=?,[Status]=? WHERE CategoryID=?"; //CHÚ Ý CÁI WHERE LÀ CÁI CUỐI
//        af = DBConnection.getAffectedRowsFromUpdate(cn, sql, categoryToUpdate.getCategoryID(), categoryToUpdate.getCategoryName(), categoryToUpdate.getDescription(), categoryToUpdate.getStatus(), categoryToUpdate.getCategoryID()); //truyền đúng tham số theo sql ko là đi
//        cn.close();
//        return (af > 0) ? categoryToUpdate : null; // thành công trả chính nó, ko thì null
//    }
//
//    //delete thành công trả lại thằng vừa delete không thì null hoặc thrown lỗi
//    public Category deleteCategoryByCategoryId(String idToDelete) throws Exception {
//        Connection cn = DBConnection.getConnection();
//        //1.kiểm tra có tồn tại chưa
//        Category tmpCategory = this.getCategoryByCategoryID(idToDelete); //thật ra có thể viết sql để tránh 2 lần connection gọi
//        if (tmpCategory != null) {
//            throw new Exception(ErrorMessage.CATEGORY_NOT_EXISTS.enumToString());
//        }
//        //GIỮ LẠI THUỘC TÍNH GÌ CŨ THÌ LẤY LẠI TMP USER XÀI
//
//        //2.update vô db
//        int af = 0;
//        String statusMessageDisable = DBMessage.DISSABLED.enumToString();
//        String sql = "UPDATE dbo.Category SET [CategoryID]=?,[CategoryName]=?,[Description]=?,[Status]=? WHERE CategoryID=?"; //CHÚ Ý CÁI WHERE LÀ CÁI CUỐI
//        af = DBConnection.getAffectedRowsFromUpdate(cn, sql, tmpCategory.getCategoryID(), tmpCategory.getCategoryName(), tmpCategory.getDescription(), statusMessageDisable, tmpCategory.getCategoryID()); //truyền đúng tham số theo sql ko là đi
//        cn.close();
//        return (af > 0) ? tmpCategory : null; // thành công trả chính nó, ko thì null
//    }
//
//}
