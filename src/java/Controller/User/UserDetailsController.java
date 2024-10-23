package Controller.User;

import Constant.ErrorMessage;
import Constant.RouteController;
import Constant.RoutePage;
import Model.DAO.UserDAO;
import Model.DTO.User;

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
@WebServlet(name = "UserDetailsController", urlPatterns = {"/UserDetailsController"})
public class UserDetailsController extends HttpServlet {

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

            //0.try validate data input
            String userName = request.getParameter("txtUserName");
            //login rồi ms đc vô
            String searchValue = request.getParameter("txtSearchValue");
            //0.1 check searchValue not emtpy
            if (searchValue.isEmpty()) {
                messageForward = "Search value must not be empty";
                forwardURL = RoutePage.USER_DETAIL_PAGE.enumToString();
                throw new Exception(ErrorMessage.INPUT_INVALID.enumToString());
            }

            //1.try ... bắt lỗi và ghi vào message
            User userDetail = null;
            try {
                UserDAO userDAO = new UserDAO();
                userDetail = userDAO.getUserByUserName(userName);

            } catch (Exception e) {
                ArrayList<String> canCatchExceptionList = new ArrayList<String>();
                canCatchExceptionList.add(ErrorMessage.USERNAME_NOT_EXISTS.enumToString());
                //1.1 check coi có văng lỗi nào mình kiểm soát đc ko
                if (canCatchExceptionList.contains(e.getMessage().toString())) {
                    //1.2 xử lý lỗi / trường hợp sai (chuyển trang, vv)
                    forwardURL = RoutePage.USER_DETAIL_PAGE.enumToString(); //quay lại
                    messageForward = e.getMessage().toString(); //set message là cái message đã bắt đc
                }
            }

            //2. thành công
            request.setAttribute("UserDetail", userDetail);
            forwardURL = RouteController.USER_CONTROLLER_SERVLET.enumToString();
            messageForward = "User Detail get successfully";
        } catch (Exception ex) { //catch ALL exception
            log(ex.getMessage());
            ex = null;
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
