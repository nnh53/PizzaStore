package Controller.User;

import Constant.ErrorMessage;
import Constant.RouteController;
import Constant.RoutePage;
import Model.DAO.AccountDAO;
import Model.DTO.Account;
import Model.DTO.UserError;

import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hoangnn
 */
@WebServlet(name = "UpdateController", urlPatterns = {"/UpdateController"})
public class UpdateController extends HttpServlet {

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
            boolean isError = false;

            //0.try validate data input
            UserError userError = new UserError();
            String userName = request.getParameter("txtUserName");
            String password = request.getParameter("txtPassword");
            String lastName = request.getParameter("txtLastName");
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
            if (!lastName.matches(".{5,20}")) {
                userError.setLastNameError("Last name must be 5-20 characters");
                isError = true;
            }
            //0.4 isAdmin parse
            boolean isAdmin = (adminCheckParam == null) ? false : true;
            //0.5 quăng isError ra kết thúc
            if (isError) {
                messageForward = "Something is wrong";
                request.setAttribute("ErrorDetail", userError);
                throw new Exception(ErrorMessage.INPUT_INVALID.enumToString());
            }

            //1.try ... bắt lỗi và ghi vào message
            try {
                AccountDAO userDAO = new AccountDAO();
//                Account userToUpdate = new Account(userName, password, lastName, isAdmin);
//                userDAO.updateAcccount(userToUpdate);

            } catch (Exception e) {
                ErrorMessage errorMessage = ErrorMessage.valueOf(e.getMessage()); //cố gắng parse error coi có dạng ErrorMessage ko
                switch (errorMessage) {
                    case USERNAME_NOT_EXISTS: {
                        messageForward = errorMessage.enumToString();
                        break;
                    }
                }
                //1.1 xử lý trường hợp sai (chuyển trang, vv)
                forwardURL = RouteController.USER_CONTROLLER_SERVLET.enumToString();

            }

            //2. thành công
            forwardURL = RouteController.USER_CONTROLLER_SERVLET.enumToString() + "?action=Details&UserName" + userName;
            messageForward = "Update user successfully";
            //2.1làm đẹp
            messageForward = "<b style='color: green'>" + messageForward + "</b>";

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
