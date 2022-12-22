/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.order;

import java.util.ArrayList;
import java.util.Date;
import model.order.Cart;
import model.order.Order;
import model.order.Payment;
import model.order.Shipment;

/**
 *
 * @author DELL
 */
public interface OrderDAO <T> {
    int createOrder(int userID,Order order,Payment payment,Shipment shipment,Cart cart,int quantity[],int itemID[]);
    int updateOrder(int orderID,String type,float cost,Date createdDate,String status,float amount);
    int deleteOrder(int orderID);
    T getUser(int userID);
    ArrayList<T> getCart(int cartID);
    int addNewCart(int userID,float totalPrice);
    void addItemToCart(int quantity[],int cartID,int itemID[]);
    void updateOrderIDToCart(int orderID,int cartID);
}
