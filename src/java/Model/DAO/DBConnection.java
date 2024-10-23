/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author hoangnn
 */
public class DBConnection {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        //DATA
        String instance = "";
        String serverName = "localhost";
        String portNumber = "1433";
        String dbName = "PizzaStore";
        String userID = "sa";
        String password = "12345";

        String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + "\\" + instance + ";databaseName=" + dbName;
        if (instance == null || instance.trim().isEmpty()) {
            url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName;
        }
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, userID, password);
    }

    //for get sql
    public static ResultSet getResultSetFromQuery(Connection cn, String sql, Object... paramArray) throws SQLException {
        ResultSet rs2 = (ResultSet) DBConnection.executeSQL("QUERY", cn, sql, paramArray);
        return rs2;
    }

    //for update sql
    public static int getAffectedRowsFromUpdate(Connection cn, String sql, Object... paramArray) throws SQLException {
        int af = (int) DBConnection.executeSQL("UPDATE", cn, sql, paramArray);
        return af;
    }

    /**
     * Chạy SQL xuống DB trả ra ResultSet hoặc int tùy theo mode, khi xài thì ép
     * kiểu trả ra. 2 mode "QUERY" hoặc "UPDATE"
     */
    private static Object executeSQL(String mode, Connection cn, String sql, Object... paramArray) throws SQLException {
        //0.kiểm tra mode - "QUERY" hoặc "UPDATE"
        if (!mode.equals("QUERY") && !mode.equals("UPDATE")) {
            throw new IllegalArgumentException("Invalid mode: " + mode);
        }
        //1.chuẩn bị Statement
        PreparedStatement preStm = null;
        try {
            preStm = cn.prepareStatement(sql);
            System.out.println("LOG SQL PREPARE: " + sql);
            System.out.println("============================================================");
            //1.1 parse kiểu data type rồi setString tương ứng vào SQL
            for (int i = 0; i < paramArray.length; i++) { //bắt buộc phải là 0 vì destruct thành cái array bắt đầu từ 0
                String paramType = paramArray[i].getClass().getSimpleName(); //chuyển class name thành String chuẩn để switch
                switch (paramType) {
                    case "String":
                        preStm.setString(i + 1, (String) paramArray[i]); //MẢNG BẮT ĐẦU TỪ 0 NHƯNG preStm SET bắt đầu từ 1
                        break;
                    case "Integer":
                        preStm.setInt(i + 1, (Integer) paramArray[i]);
                        break;
                    case "Float":
                        preStm.setFloat(i + 1, (Float) paramArray[i]);
                        break;
                    case "Boolean":
                        preStm.setBoolean(i + 1, (Boolean) paramArray[i]);
                        break;
                    default:
                        throw new SQLException("Unsupported parameter type: " + paramType);
                }
            }
            //1.2 execute
            Object rs = null;
            if (mode.equals("QUERY")) {
                rs = preStm.executeQuery();
            } else if (mode.equals("UPDATE")) {
                rs = preStm.executeUpdate();
            }
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Error executing SQL", e);
        }
    }//end executeSQL
}

//ĐỂ QUA CHO TỪNG DAO LÀM - DO PHẢI PARSE XONG MỚI ĐC ĐÓNG CONNECTION
//        } finally {
//            if (preStm != null) {
//                preStm.close();
//            }
//            if (cn != null) {
//                cn.close();
//            }
