/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.List;
import model.Item.Item;

/**
 *
 * @author Admin
 */
public class ItemUtils {

    public static float calcTotalPrice(List<Item> listItem, List<Integer> listQuantity) {
        float totalPrice = 0;

        for (int i = 0; i < listItem.size(); i++) {
            Item item = listItem.get(i);
            totalPrice += (item.getPrice() - item.getPrice() * (item.getDiscount()/100)) * listQuantity.get(i);
        }

        return totalPrice;
    }
}
