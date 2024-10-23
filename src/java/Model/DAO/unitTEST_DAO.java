///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Model.DAO;
//
//import Model.DTO.User;
//import java.util.ArrayList;
//
///**
// *
// * @author hoangnn
// */
//public class unitTEST_DAO {
//
//    public static void main(String[] args) {
//        UserDAO userDAO = new UserDAO();
//        try {
//            //1.THÊM
//            System.out.println("1.");
//            User tmpUser = userDAO.addUser(new User("userNameTest", "passwordTest", "lastNameTest", false));
//            System.out.println((tmpUser != null) ? "add success" : "add fail");
//
//            //2. DUYỆT
//            System.out.println("2.");
//            User tmpUser2 = userDAO.getUserByUserName("userNameTest");
//            System.out.println((tmpUser2 != null) ? "get success" : "get fail");
//
//            //3. TÌM
//            System.out.println("3.");
//            ArrayList<User> list = null;
//            list = userDAO.searchUserByLastName("lastNameTest");
//            for (User keyUser : list) {
//                keyUser.toString();
//            }
//            System.out.println((list != null) ? "tim success" : "tim fail");
//
//            //4. SỬA
//            System.out.println("4.");
//            User tmpUser4 = userDAO.updateUser(new User("userNameTest", "passwordTest", "AHIHIHI_sua", false));
//            System.out.println(tmpUser4);
//            System.out.println(tmpUser4.toString());
//            System.out.println((tmpUser4 != null) ? "sua success" : "sua fail");
//
//            //5. DELETE
//            System.out.println("5.");
//            User tmpUser5 = userDAO.deleteUserByUserName(tmpUser.getUserName());
//            System.out.println(tmpUser5);
//            System.out.println((tmpUser5 == null) ? "delete success" : "delete fail");
//
//            //LOGIN
//            System.out.println("6.");
//            User tmpUser6 = userDAO.login("hoangnn", "root");
//            System.out.println(tmpUser6.toString());
//
//        } catch (Exception e) {
//            e.getCause();
//            e.getMessage();
//            e.printStackTrace();
//            e = null;
//        }
//
//    }
//}
