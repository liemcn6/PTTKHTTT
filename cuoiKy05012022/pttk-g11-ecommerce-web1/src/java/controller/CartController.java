/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.cart.CartDAO;
import dao.cart.CartDAOImpl;
import dao.item.ItemDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javafx.util.Pair;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Item.Item;
import model.order.Cart;
import utils.ItemUtils;

/**
 *
 * @author Admin
 */
public class CartController extends HttpServlet {

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

        if (route == null) {
            HttpSession session = request.getSession(false);

            if (session == null) {
                response.sendRedirect("/g11/home");
                return;
            }

            Integer userId = (Integer) session.getAttribute("userId");
            if (userId == null) {
                response.sendRedirect("/g11/auth/logout");
                return;
            }

            Pair<List<Item>, List<Integer>> listItemAndQuantity = getCartItem(userId);
            List<Item> listItem = listItemAndQuantity.getKey();
            List<Integer> listQuantity = listItemAndQuantity.getValue();
            
            request.setAttribute("listItem", listItem);
            request.setAttribute("listQuantity", listQuantity);
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/cart.jsp");
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
        String route = request.getPathInfo();
        String itemId = request.getParameter("itemid");
        String itemQuantity = request.getParameter("quantity");

        if (route != null && route.equalsIgnoreCase("/add-to-cart")) {
            HttpSession session = request.getSession(false);

            if (session == null) {
                response.sendRedirect("/g11/home");
                return;
            }

            Integer userId = (Integer) session.getAttribute("userId");

            if (userId == null) {
                response.sendRedirect("/g11/auth/logout");
                return;
            }

            int rowAffected = addItemToCart(itemId, userId, itemQuantity);
            if (rowAffected <= 0) {
                sendResponse(response, "503;");
            } else {
                sendResponse(response, "201;");
            }
        }
    }

    private Pair<List<Item>, List<Integer>> getCartItem(int userId) {
        Cart cart = new CartDAOImpl().getCartByUserID(userId);
        Pair<List<Item>, List<Integer>> listItem = new ItemDAOImpl().getItemOfCartByCartID(cart.getId());

        return listItem;
    }

    private int addItemToCart(String itemId, int userId, String itemQuantity) {
        Integer itemIdNumber = Integer.parseInt(itemId);
        Integer itemQuantityNumber = Integer.parseInt(itemQuantity);
        int rowAffected = 0;

        CartDAO cartDAO = new CartDAOImpl();

        Cart cart = cartDAO.getCartByUserID(userId);
        if (cart != null) {
            rowAffected = cartDAO.addItemInCartByItemID(itemQuantityNumber, cart.getId(), itemIdNumber);
            return rowAffected;
        }

        return rowAffected;
    }

    private void sendResponse(HttpServletResponse response, String responseData) throws IOException {
        response.setHeader("Content-Type", "text/plain");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write(responseData);
        writer.close();
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
