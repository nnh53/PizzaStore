package Controller.Account;

import Controller.Account.*;
import Constant.ErrorMessage;
import Constant.RouteController;
import Constant.RoutePage;
import Model.DAO.AccountDAO;
import Model.DTO.Account;

import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hoangnn
 */
@WebServlet(name = "UserDetailController", urlPatterns = {"/UserDetailController"})
public class UserDetailController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String forwardURL = RoutePage.NOT_FOUND_PAGE.enumToString(); //set mặc định
        String messageForward = ErrorMessage.SOMETHING_WRONG.enumToString();  //set mặc định

        //start try tổng của servlet
        try {
            //login rồi ms đc vô
            String userName = request.getParameter("UserName");

            Account userDetail = null;
            AccountDAO userDAO = new AccountDAO();
            userDetail = userDAO.getAccountByUserName(userName); //tìm

            //2. thành công
            request.setAttribute("userDetail", userDetail);
            forwardURL = RoutePage.USER_DETAIL_PAGE.enumToString();
            messageForward = "User Detail get successfully";
        } catch (Exception ex) { //catch ALL exception
            ArrayList<String> canCatchExceptionList = new ArrayList<String>();
            canCatchExceptionList.add(ErrorMessage.USERNAME_NOT_EXISTS.enumToString());
            //1.1 check coi có văng lỗi nào mình kiểm soát đc ko
            if (canCatchExceptionList.contains(ex.getMessage().toString())) {
                //1.2 xử lý lỗi / trường hợp sai (chuyển trang, vv)
                forwardURL = RoutePage.USER_DETAIL_PAGE.enumToString(); //quay lại
                messageForward = ex.getMessage().toString(); //set message là cái message đã bắt đc
            } else {
                log("================== 500 ===================");
                log(ex.getMessage());
                ex = null; //tránh crash
                forwardURL = RoutePage.NOT_FOUND_PAGE.enumToString(); //set mặc định
                messageForward = ErrorMessage.SOMETHING_WRONG.enumToString();  //set mặc định
            }
        } finally {
            request.setAttribute("message", messageForward);
            RequestDispatcher rd = request.getRequestDispatcher(forwardURL);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Expression servletEditorFold is undefined on line 91, column 54 in Templates/JSP_Servlet/Controller.java.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
