package Controller.Authentication;

import Constant.ErrorMessage;
import Constant.RouteController;
import Constant.RoutePage;
import Model.DAO.AccountDAO;
import Model.DTO.Account;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hoangnn
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

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

        String userName = request.getParameter("txtUserName");
        String password = request.getParameter("txtPassword");

        try {
            AccountDAO accountDAO = new AccountDAO();
            Account currentUser = null;
            //0.try login in và bắt lỗi
            try {
                currentUser = accountDAO.login(userName, password);
            } catch (Exception e) {
                ArrayList<String> canCatchExceptionList = new ArrayList<String>();
                canCatchExceptionList.add(ErrorMessage.USERNAME_OR_PASSWORD_INCORRECT.enumToString());
                canCatchExceptionList.add(ErrorMessage.USERNAME_NOT_EXISTS.enumToString());
                canCatchExceptionList.add(ErrorMessage.ACCOUNT_NOT_EXISTS.enumToString());
                //0.1 check coi có văng lỗi nào mình kiểm soát đc ko
                if (canCatchExceptionList.contains(e.getMessage().toString())) {
                    //0.2 xử lý lỗi
                    forwardURL = RoutePage.LOGIN_PAGE.enumToString(); //quay lại login đê
                    messageForward = e.getMessage().toString(); //set message là cái message đã bắt đc

                }
            }

            //1.login thành công
            if (currentUser != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userLoggedIn", currentUser);
                //kt admin và forward sang admin
                if (currentUser.getType().equals("admin")) {
                    forwardURL = RoutePage.SEARCH_PAGE.enumToString();
                } else { // not admin
                    forwardURL = RouteController.USER_CONTROLLER_SERVLET + "?action=Details&&UserName=" + userName;
                }
            }

        } catch (Exception ex) { //catch ALL exception
            log("================== 500 ===================");
            log(ex.getMessage());
            ex = null; //tránh crash
            //route và message về mặc định set trên
        } finally {
            request.setAttribute("message", messageForward);
            RequestDispatcher rd = request.getRequestDispatcher(forwardURL);
            rd.forward(request, response);
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
