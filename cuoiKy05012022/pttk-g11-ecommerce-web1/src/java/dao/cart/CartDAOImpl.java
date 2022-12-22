/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.cart;

import dao.utils.ConDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Item.Item;
import model.order.Cart;

/**
 *
 * @author DELL
 */
public class CartDAOImpl implements CartDAO {

    private Connection conn;
    private final String GET_CART_BY_USERID = "SELECT * FROM cart where UserID= ?;";
    private final String CREATE_CART_BY_USERID = "INSERT INTO cart (UserID) VALUES (?);";
    private final String DELETE_ITEM_IN_CART_BY_ITEMID = "DELETE FROM cart_item Where ItemID=?;";
    private final String ADD_ITEM_IN_CART_BY_ITEMID = "INSERT INTO cart_item (Quantity, CartID, ItemID) VALUES (?, ?, ?);";
    private final String UPDATE_ITEM_AMOUNT = "UPDATE cart_item SET Quantity = ? WHERE (itemID = ?);";
    private final String GET_ITEM_AMOUNT_IN_CART_BY_ID_SQL = "SELECT quantity FROM cart_item WHERE itemid = ? AND cartid = ?";

    public CartDAOImpl() {
        conn = ConDB.getJDBCCOnection();
    }

    @Override
    public Cart getCartByUserID(int ID) {
        ResultSet rs;
        PreparedStatement prestatement;
        try {
            prestatement = conn.prepareStatement(GET_CART_BY_USERID);
            prestatement.setInt(1, ID);
            rs = prestatement.executeQuery();
            Cart c = new Cart();
            if (rs.next()) {
                c.setId(rs.getInt(1));
                c.setTotalPrice(rs.getFloat(4));
            }
            return c;
        } catch (SQLException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public int createCartByUserID(int ID) {
        ResultSet rs;
        PreparedStatement prestatement;
        int cartId = 0;

        try {
            prestatement = conn.prepareStatement(CREATE_CART_BY_USERID, Statement.RETURN_GENERATED_KEYS);
            prestatement.setInt(1, ID);
            int rowcount = prestatement.executeUpdate();

            if (rowcount > 0) {
                rs = prestatement.getGeneratedKeys();
                if (rs.next()) {
                    cartId = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return cartId;
        }
    }

    @Override
    public int deleteItemInCartByItemID(int ID) {
        ResultSet rs;
        PreparedStatement prestatement;
        try {
            prestatement = conn.prepareStatement(DELETE_ITEM_IN_CART_BY_ITEMID);
            prestatement.setInt(1, ID);
            int rowcount = prestatement.executeUpdate();
            return rowcount;
        } catch (SQLException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int addItemInCartByItemID(int quantity, int cartID, int itemID) {
        ResultSet rs;
        PreparedStatement prestatement;
        try {
            prestatement = conn.prepareStatement(ADD_ITEM_IN_CART_BY_ITEMID);
            prestatement.setInt(1, quantity);
            prestatement.setInt(2, cartID);
            prestatement.setInt(3, itemID);
            int rowcount = prestatement.executeUpdate();
            return rowcount;
        } catch (SQLException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int updateItemAmountByItemID(int quantity, int ID) {
        ResultSet rs;
        PreparedStatement prestatement;
        try {
            prestatement = conn.prepareStatement(UPDATE_ITEM_AMOUNT);
            prestatement.setInt(1, quantity);
            prestatement.setInt(2, ID);
            int rowcount = prestatement.executeUpdate();
            return rowcount;
        } catch (SQLException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int getItemAmountById(int cartId, int itemId) {
        int quantity = 0;

        try (PreparedStatement ps = conn.prepareStatement(GET_ITEM_AMOUNT_IN_CART_BY_ID_SQL)) {
            ps.setInt(1, itemId);
            ps.setInt(2, cartId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                quantity = rs.getInt("quantity");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return quantity;
        }
    }
}
