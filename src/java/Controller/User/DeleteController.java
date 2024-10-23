package Controller.User;

import Constant.ErrorMessage;
import Constant.RouteController;
import Constant.RoutePage;
import Model.DAO.UserDAO;
import Model.DTO.User;

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
@WebServlet(name = "DeleteController", urlPatterns = {"/DeleteController"})
public class DeleteController extends HttpServlet {

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
        PrintWriter out = response.getWriter();

        String forwardURL = RoutePage.NOT_FOUND_PAGE.enumToString(); //set mặc định
        String messageForward = "";  //set mặc định

        //start try tổng của servlet
        try {

            //0.try validate data input
            HttpSession session = request.getSession();
            User userLoggedIn = (User) session.getAttribute("userLoggedIn");
            String userName = request.getParameter("UserName");
            String searchValue = request.getParameter("txtSearchValue");

            //0.1 check userName not LOGIN
            if (userName.equals(userLoggedIn.getUserName())) {
                messageForward = "This user is logged in. Cannot be delete";
                throw new Exception(ErrorMessage.INPUT_INVALID.enumToString());
            }
            //0.2 check searchValue not emtpy
            if (searchValue.isEmpty()) {
                messageForward = "Search value must not be empty";
                throw new Exception(ErrorMessage.INPUT_INVALID.enumToString());
            }

            //1.try ... bắt lỗi và ghi vào message
            try {
                UserDAO userDAO = new UserDAO();
                userDAO.deleteUserByUserName(userName);
            } catch (Exception e) {
                ErrorMessage errorMessage = ErrorMessage.valueOf(e.getMessage()); //cố gắng parse error coi có dạng ErrorMessage ko
                switch (errorMessage) {
                    case USERNAME_NOT_EXISTS: {
                        messageForward = errorMessage.enumToString();
                        break;
                    }
                }
                //1.1 xử lý trường hợp sai (chuyển trang, vv)
                forwardURL = RouteController.USER_CONTROLLER_SERVLET.enumToString() + "?action=Search&txtSearchValue" + searchValue;
            }

            //2. thành công
            forwardURL = RouteController.USER_CONTROLLER_SERVLET.enumToString();
            messageForward = "";
            //2.1làm đẹp
            messageForward = "<b style='color: green'>" + messageForward + "</b>";
            messageForward = "Delete user successfully";

        } catch (Exception ex) { //catch ALL exception
            log(ex.getMessage());
            ex = null;
            //làm đẹp
            messageForward = "<b style='color: red'>" + messageForward + "</b>";
            request.setAttribute("message", messageForward);
        } finally {
            out.close();
            RequestDispatcher rd = request.getRequestDispatcher(forwardURL);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Expression servletEditorFold is undefined on line 90, column 54 in Templates/JSP_Servlet/Controller.java.">
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
