package Controller.User;

import Constant.DBMessage;
import Constant.ErrorMessage;
import Constant.RouteController;
import Constant.RoutePage;
import Model.DAO.AccountDAO;
import Model.DTO.Account;
import Model.DTO.UserError;

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
@WebServlet(name = "CreateController", urlPatterns = {"/CreateController"})
public class CreateController extends HttpServlet {

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
            boolean isError = false;

            //0.try validate data input
            UserError userError = new UserError();
            String userName = request.getParameter("txtUserName");
            String password = request.getParameter("txtPassword");
            String fullname = request.getParameter("txtLastName");
            String adminCheckParam = request.getParameter("chkIsAdmin");

            //0.1 check userName format Uxxx, x is digit
            if (!userName.matches("U\\d{3}")) {
                userError.setUserNameError("Username must be Uxxx, x is digit");
                isError = true;
            }
            //0.2 check password must be 3-15 characters
            if (!password.matches(".{3,15}")) {
                userError.setPasswordError("Password must be 3-15 characters");
                isError = true;
            }
            //0.3 check lastName must be 5-20 characters
            if (!fullname.matches(".{5,20}")) {
                userError.setLastNameError("Last name must be 5-20 characters");
                isError = true;
            }
            //0.4 isAdmin parse
            boolean isAdmin = (adminCheckParam == null) ? false : true;
            //0.5 nếu có lỗi validate quăng ErrorDetail cho jsp
            if (isError) {
                messageForward = ErrorMessage.INPUT_INVALID.enumToString();
                request.setAttribute("ErrorDetail", userError);
                forwardURL = RoutePage.CREATE_USER_PAGE.enumToString(); //quay lại
                throw new Exception(ErrorMessage.INPUT_INVALID.enumToString());
            }

            //1.try ... bắt lỗi và ghi vào message
            try {
                AccountDAO userDAO = new AccountDAO();
                String id = userDAO.generateID();
                if (isAdmin) {
                    Account userToAdd = new Account(id, userName, password, fullname, "admin", DBMessage.ACTIVE.toString());
                    userDAO.addAccount(userToAdd);
                } else {
                    Account userToAdd = new Account(id, userName, password, fullname, "user", DBMessage.ACTIVE.toString());
                    userDAO.addAccount(userToAdd);
                }
            } catch (Exception e) {
                ArrayList<String> canCatchExceptionList = new ArrayList<String>();
                canCatchExceptionList.add(ErrorMessage.USERNAME_ALREADY_EXISTS.enumToString());
                //1.1 check coi có văng lỗi nào mình kiểm soát đc ko
                if (canCatchExceptionList.contains(e.getMessage().toString())) {
                    //1.2 xử lý lỗi / trường hợp sai (chuyển trang, vv)
                    forwardURL = RoutePage.CREATE_USER_PAGE.enumToString(); //quay lại
                    messageForward = e.getMessage().toString(); //set message là cái message đã bắt đc
                }
            }

            //2. thành công
            forwardURL = RoutePage.CREATE_USER_PAGE.enumToString();
            messageForward = "Create user successfully";

        } catch (Exception ex) { //catch ALL exception
            log(ex.getMessage());
            ex = null; //tránh crash
            //route và message về mặc định set trên
        } finally {
            request.setAttribute("message", messageForward);
            RequestDispatcher rd = request.getRequestDispatcher(forwardURL);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Expression servletEditorFold is undefined on line 89, column 54 in Templates/JSP_Servlet/Controller.java.">
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
