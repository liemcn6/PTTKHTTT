/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.book.BookDAOImpl;
import dao.clothes.ClothesDAOImpl;
import dao.item.ItemDAOImpl;
import dao.shoes.ShoesDAOImpl;
import java.io.IOException;
import java.util.List;
import javafx.util.Pair;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Item.Item;
import model.book.Book;
import model.book.BookItem;
import model.clothes.Clothes;
import model.clothes.ClothesItem;
import model.electrnoics.Electronics;
import model.electrnoics.ElectronicsItem;
import model.shoes.Shoes;
import model.shoes.ShoesItem;

/**
 *
 * @author Admin
 */
public class ItemController extends HttpServlet {

    private final int limitItemPerPage = 15;

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

//        path = "/product/" -> get product list
        if (route == null) {
            String page = request.getParameter("page");
            String category = request.getParameter("category");
            String searchQuery = request.getParameter("q");

            int pageNumber = getPageNumber(page);

            List<Item> listItem = null;
            if (category == null) {
                listItem = getItems(pageNumber, searchQuery);
            } else {
                listItem = getItemsByCategory(pageNumber, category, searchQuery);
            }

            request.setAttribute("listItem", listItem);
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/list-item.jsp");
            rd.forward(request, response);

        } else {

            int itemId = 0;

//            item id not number -> not found
            try {
                itemId = Integer.parseInt(route.substring(1));
            } catch (NumberFormatException e) {
                System.err.println(e);
                response.sendRedirect(request.getContextPath() + "/not-found");
                return;
            }

//            category null -> not found
            String itemCategory = getItemCategory(itemId);
            if (itemCategory == null) {
                response.sendRedirect(request.getContextPath() + "/not-found");
                return;
            }

//            call correspoding method for get item detail
            switch (itemCategory) {
                case "1":
                    Pair<BookItem, Book> bookDetail = getBookDetail(itemId);
                    request.setAttribute("bookItem", bookDetail.getKey());
                    request.setAttribute("book", bookDetail.getValue());
                    request.setAttribute("category", 1);
                    break;
                case "2":
                    Pair<ClothesItem, Clothes> clothesDetail = getClothesDetail(itemId);
                    request.setAttribute("clothesItem", clothesDetail.getKey());
                    request.setAttribute("clothes", clothesDetail.getValue());
                    request.setAttribute("category", 2);
                    System.out.println(clothesDetail.getKey().getName());
                    break;
                case "3":
                    Pair<ShoesItem, Shoes> shoeDetail = getShoeDetail(itemId);
                    request.setAttribute("shoeItem", shoeDetail.getKey());
                    request.setAttribute("shoe", shoeDetail.getValue());
                    System.out.println(shoeDetail.getValue().getRemainingQuantity());
                    request.setAttribute("category", 3);

                    break;
                case "4":
                    Pair<ElectronicsItem, Electronics> electronicDetail = getElectronicDetail(itemId);
                    request.setAttribute("electronicItem", electronicDetail.getKey());
                    request.setAttribute("electronic", electronicDetail.getValue());
                    request.setAttribute("category", 4);

                    break;
                default:
                    return;
            }

            RequestDispatcher rd = request.getRequestDispatcher("/jsp/item-detail.jsp");
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

    private List<Item> getItems(int page, String searchQuery) {
        int from = page * limitItemPerPage + limitItemPerPage;
        List<Item> listItem = new ItemDAOImpl().getNewItems(limitItemPerPage, from, searchQuery);
        return listItem;
    }

    private List<Item> getItemsByCategory(int page, String category, String searchQuery) {
        int from = page * limitItemPerPage + limitItemPerPage;
        List<Item> listItem = new ItemDAOImpl().getNewItemsByCategory(limitItemPerPage, from, category, searchQuery);
        return listItem;
    }

    private int getPageNumber(String page) {
        if (page != null) {
            return Integer.parseInt(page);
        }

        return 0;
    }

    private BookItem getBookItem(int bookItemId) {
        return new BookDAOImpl().getBookIT(bookItemId);
    }

    private String getItemCategory(int itemId) {
        return new ItemDAOImpl().getItemCategory(itemId);
    }

    protected Pair<BookItem, Book> getBookDetail(int itemId) {
        return new BookDAOImpl().getBookAllStt(itemId);
    }

    protected Pair<ElectronicsItem, Electronics> getElectronicDetail(int itemId) {
//        return new ElectronicDAOImpl().get
        return null;
    }

    protected Pair<ShoesItem, Shoes> getShoeDetail(int itemId) {
        return new ShoesDAOImpl().getShoesAllStt(itemId);
    }

    protected Pair<ClothesItem, Clothes> getClothesDetail(int itemId) {
        return new ClothesDAOImpl().getClothesAllStt(itemId);
    }
}
