/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.order;

/**
 *
 * @author Admin
 */
public class Shipment {
    private int id;
    private String type;
    private float cost;

    public Shipment() {
    }

    public Shipment(int id, String type, float cost) {
        this.id = id;
        this.type = type;
        this.cost = cost;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public float getCost() {
        return cost;
    }
    
    
}
