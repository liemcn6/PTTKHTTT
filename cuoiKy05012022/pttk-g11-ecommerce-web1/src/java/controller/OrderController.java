/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.cart.CartDAO;
import dao.cart.CartDAOImpl;
import dao.item.ItemDAO;
import dao.item.ItemDAOImpl;
import dao.order.OrderDAO;
import dao.order.OrderDAOImpl;
import dao.user.UserDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import model.user.User;

/**
 *
 * @author Admin
 */
public class OrderController extends HttpServlet {

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

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("/g11/auth/logout");
            return;
        }

        if (route != null && route.equalsIgnoreCase("/fill-info")) {
            User user = new UserDAOImpl().getUserById(userId);
            Pair<List<Item>, List<Integer>> listItemAndQuantity = getCartItem(userId);
            List<Item> listItem = listItemAndQuantity.getKey();
            List<Integer> listQuantity = listItemAndQuantity.getValue();

            request.setAttribute("listItem", listItem);
            request.setAttribute("listQuantity", listQuantity);
            request.setAttribute("user", user);
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/checkout.jsp");
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

        if (route != null && route.equalsIgnoreCase("/create-order-cart")) {
            String itemIdStr = request.getParameter("itemIdStr");
            int rowAffected = 0;

            if (itemIdStr != null) {
                Cart cart = getCart(userId);
                rowAffected = createOrderCart(itemIdStr, cart, userId);
            }

            if (rowAffected > 0) {
                sendResponse(response, "201;");
            } else {
                sendResponse(response, "503;");
            }
        }
    }

    private int createOrderCart(String itemIdStr, Cart cart, int userId) {
        String[] itemId = itemIdStr.split(";");
        ItemDAO itemDAO = new ItemDAOImpl();

        List<Item> listItem = new ArrayList<>();

        for (String id : itemId) {
            try {
                int idNumber = Integer.parseInt(id);
                Item item = itemDAO.getItem(idNumber);
                listItem.add(item);
            } catch (NumberFormatException e) {
                System.err.println(e);
            }
        }

        CartDAO cartDAO = new CartDAOImpl();

        float totalPrice = 0;
        int[] quantityArr = new int[listItem.size()];
        int[] itemIdArr = new int[listItem.size()];

        for (int i = 0; i < listItem.size(); i++) {
            Item item = listItem.get(i);
            int quantity = cartDAO.getItemAmountById(cart.getId(), item.getID());

            quantityArr[i] = quantity;
            itemIdArr[i] = item.getID();

            totalPrice += calcItemTotalPrice(item, quantity);
        }

        OrderDAO orderDAO = new OrderDAOImpl();
        int newCartId = orderDAO.addNewCart(userId, totalPrice);

        if (newCartId <= 0) {
            return 0;
        }

        orderDAO.addItemToCart(quantityArr, newCartId, itemIdArr);
        return 1;
    }

    private Cart getCart(int userId) {
        return new CartDAOImpl().getCartByUserID(userId);
    }

    private float calcItemTotalPrice(Item item, int quantity) {
        return (item.getPrice() - item.getPrice() * item.getDiscount()) * quantity;
    }

    private void sendResponse(HttpServletResponse response, String responseData) throws IOException {
        response.setHeader("Content-Type", "text/plain");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write(responseData);
        writer.close();
    }

    private Pair<List<Item>, List<Integer>> getCartItem(int userId) {
        Cart cart = new CartDAOImpl().getCartByUserID(userId);
        Pair<List<Item>, List<Integer>> listItem = new ItemDAOImpl().getItemOfCartByCartID(cart.getId());

        return listItem;
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
