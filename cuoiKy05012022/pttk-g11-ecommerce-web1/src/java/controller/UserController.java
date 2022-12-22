/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.user.UserDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.user.Address;
import model.user.FullName;
import model.user.User;

/**
 *
 * @author Admin
 */
public class UserController extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String route = request.getPathInfo();
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect("/g11/home");
            return;
        }

        if (route == null || route.equals("/account/profile")) {
            Integer userId = (Integer) session.getAttribute("userId");

            if (userId == null) {
                sendToLogout(response);
                return;
            }

            User user = getUserInfo(userId);

//            send to logout route to destroy session if user not exist
            if (user == null) {
                sendToLogout(response);
                return;
            }

            request.setAttribute("user", user);
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/user-profile.jsp");
            rd.forward(request, response);
        }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String route = request.getPathInfo();
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect("/g11/home");
            return;
        }

        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            sendToLogout(response);
            return;
        }

        if (route.equals("/update")) {
            String fullName = request.getParameter("fullname");
            String email = request.getParameter("email");
            String gender = request.getParameter("gender");
            String phoneNumber = request.getParameter("phonenumber");
            String detailAddress = request.getParameter("detailaddress");
            String district = request.getParameter("district");
            String city = request.getParameter("city");

            updateUserInfo(userId, fullName, email, gender, phoneNumber, new Address(detailAddress, district, city));
            response.sendRedirect("/g11/user/account/profile");
        }
    }

    private User getUserInfo(int userId) {
        User user = new UserDAOImpl().getUserById(userId);
        return user;
    }

    private int updateUserInfo(int userId, String fullNameStr, String email, String gender, String phoneNumber, Address address) {
        FullName fullName = (fullNameStr == null ? null : new FullName(fullNameStr));
        return new UserDAOImpl().updateCustomer(new User(userId, phoneNumber, email, gender, null, fullName, address, null));
    }

    private void sendToLogout(HttpServletResponse response) throws IOException {
        response.sendRedirect("/g11/auth/logout");
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
