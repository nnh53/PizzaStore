package Controller.User;

import Constant.ErrorMessage;
import Constant.RouteController;
import Constant.RoutePage;

import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

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
        String action = request.getParameter("action");

        //start try tổng của servlet
        try {
            HttpSession session = request.getSession();
            boolean isLoggedIn = (session.getAttribute("userLoggedIn") == null) ? false : true;

            //1. không cần login
            if (isLoggedIn == false) {
                switch (action) {
                    case "Create": {
                        forwardURL = RouteController.CREATE_USER_CONTROLLER.enumToString();
                        break;
                    }
                }
            }
            //2. cần authen
            if (isLoggedIn == true) {
                switch (action) {
                    case "Search": {
                        forwardURL = RouteController.SEARCH_USER_CONTROLLER.enumToString();
                        break;
                    }
                    case "Delete": {
                        forwardURL = RouteController.DELETE_USER_CONTROLLER.enumToString();
                        break;
                    }
                    case "Update": {
                        forwardURL = RouteController.UPDATE_USER_CONTROLLER.enumToString();
                        break;
                    }
                    case "Details": {
                        forwardURL = RouteController.USER_DETAIL_CONTROLLER.enumToString();
                        break;
                    }
                }
            }
            //3. chưa đc login
            forwardURL = RoutePage.LOGIN_PAGE.enumToString();

        } catch (Exception ex) { //catch ALL exception
            log(ex.getMessage());
            ex = null;
            //bắt để đừng crash app
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
