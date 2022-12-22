/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.item;

import dao.utils.ConDB;
import dao.utils.Mapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import model.Item.Item;

/**
 *
 * @author ADMIN
 */
public class ItemDAOImpl implements ItemDAO {

    private final Connection conn;
    private final String sql1 = "DELETE FROM shopbanhang.item where ID = ?;";
    private final String sql2 = "Update item SET Description=?,Price=?,Discount=?,SellingStatus=?,Image=?,Category=? where ID=?";
    private final String sql3 = "SELECT * FROM item WHERE ID = ?;";
    private final String GET_NEW_ITEMS_LIMIT_SQL = "SELECT * FROM item WHERE id < ? ORDER BY id DESC LIMIT ?";
    private final String GET_ITEMS_FILTER_BY_NAME_SQL = "SELECT * FROM item WHERE id < ? AND name LIKE ? ORDER BY id DESC LIMIT ?";
    private final String GET_NEW_ITEMS_LIMIT_BY_CATEGORY_SQL = "SELECT * FROM item WHERE id <= ? AND category = ? ORDER BY id DESC LIMIT ?";
    private final String GET_ITEMS_FILTER_BY_CATEGORY_AND_NAME_SQL = "SELECT * FROM item WHERE id <= ? AND category = ? AND name LIKE ? ORDER BY id DESC LIMIT ?";
    private final String GET_ITEM_CATEGORY_SQL = "SELECT category FROM item WHERE id = ?";
    private final String GET_ITEM_OF_CART_ITEM_BY_CARTID = "SELECT * FROM cart_item WHERE CartID = ? ";

    public ItemDAOImpl() {
        conn = ConDB.getJDBCCOnection();
    }

    @Override
    public int deleteItem(int id) {
        PreparedStatement prestatement;
        ResultSet rs;

        try {
            prestatement = conn.prepareStatement(sql1);
            prestatement.setInt(1, id);
            int rowDeleted = prestatement.executeUpdate();
            return rowDeleted;
        } catch (SQLException ex) {
            Logger.getLogger(ItemDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public int updateItem(Item item) {
        PreparedStatement prestatement;

        try {
            prestatement = conn.prepareStatement(sql2);
            prestatement.setString(1, item.getDescription());
            prestatement.setFloat(2, item.getPrice());
            prestatement.setFloat(3, item.getDiscount());
            prestatement.setString(4, item.getSellingStatus());
            prestatement.setString(5, item.getImage());
            prestatement.setString(6, item.getCategory());
            prestatement.setInt(7, item.getID());
            int rowcount1 = prestatement.executeUpdate();
            return rowcount1;
        } catch (SQLException ex) {
            Logger.getLogger(ItemDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    @Override
    public Item getItem(int id) {
        PreparedStatement preStatement;
        ResultSet rs;
        try {
            preStatement = conn.prepareStatement(sql3);
            preStatement.setInt(1, id);
            rs = preStatement.executeQuery();
            Item i = new Item();
            while (rs.next()) {
                i.setName(rs.getString("name"));
                i.setDescription(rs.getString(2));
                i.setPrice(rs.getFloat(3));
                i.setDiscount(rs.getFloat(4));
                i.setSellingStatus(rs.getString(5));
                i.setImage(rs.getString(6));
                i.setCategory(rs.getString(7));
            }
            return i;
        } catch (SQLException ex) {
            Logger.getLogger(ItemDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Item> getNewItems(int limit, int from, String itemName) {
        List<Item> listItem = new ArrayList<>();

        try {
            PreparedStatement ps;

            if (itemName == null) {
                ps = conn.prepareStatement(GET_NEW_ITEMS_LIMIT_SQL);
                ps.setInt(1, from);
                ps.setInt(2, limit);
            } else {
                ps = conn.prepareStatement(GET_ITEMS_FILTER_BY_NAME_SQL);
                ps.setInt(1, from);
                ps.setString(2, "%" + itemName + "%");
                ps.setInt(3, limit);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = Mapper.mapItem(rs);
                listItem.add(item);
            }

            System.out.println("List item length " + listItem.size());
        } catch (SQLException ex) {
            Logger.getLogger(ItemDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return listItem;
        }
    }

    @Override
    public List<Item> getNewItemsByCategory(int limit, int from, String category, String itemName) {
        List<Item> listItem = new ArrayList<>();

        try {
            PreparedStatement ps;

            if (itemName == null) {
                ps = conn.prepareStatement(GET_NEW_ITEMS_LIMIT_BY_CATEGORY_SQL);
                ps.setInt(1, from);
                ps.setString(2, category);
                ps.setInt(3, limit);
            } else {
                ps = conn.prepareStatement(GET_ITEMS_FILTER_BY_CATEGORY_AND_NAME_SQL);
                ps.setInt(1, from);
                ps.setString(2, category);
                ps.setString(3, "%" + itemName + "%");
                ps.setInt(4, limit);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = Mapper.mapItem(rs);
                listItem.add(item);
            }

            System.out.println("List item length " + listItem.size());
        } catch (SQLException ex) {
            Logger.getLogger(ItemDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return listItem;
        }
    }

    @Override
    public String getItemCategory(int itemId) {
        String category = null;

        try (PreparedStatement ps = conn.prepareStatement(GET_ITEM_CATEGORY_SQL)) {
            ps.setInt(1, itemId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                category = rs.getString("category");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return category;
        }
    }

    @Override
    public Pair<List<Item>, List<Integer>> getItemOfCartByCartID(int id) {
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = conn.prepareStatement(GET_ITEM_OF_CART_ITEM_BY_CARTID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            List<Integer> quantity= new ArrayList<>();
            List<Integer> itemID= new ArrayList<>();
            while (rs.next()) {
                quantity.add(rs.getInt(2));
                itemID.add(rs.getInt(4));
            }
            List<Item> item= new ArrayList<>();
            for(int i=0;i<itemID.size();i++){
                Item tmp= getItem(itemID.get(i));
                tmp.setID(itemID.get(i));
                item.add(tmp);
            }
            Pair<List<Item>, List<Integer>> pair= new Pair<>(item,quantity);
            return pair;
        } catch (Exception ex) {
            Logger.getLogger(ItemDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
