/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author mohar
 */
public class Model {
    private int id;
    private int category_id;
    private String name;
    private String color;
    private String manufacturer;
    private int warning_qty;
    private float purchase_price;
    private float sell_price;
    
    public Model(int id, int cat_id, String name, String color, String manufacturer, int warning_qty, float p_price, float s_price){
        this.id = id;
        this.category_id = cat_id;
        this.name = name;
        this.color = color;
        this.manufacturer = manufacturer;
        this.warning_qty = warning_qty;
        this.purchase_price = p_price;
        this.sell_price = s_price;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getWarning_qty() {
        return warning_qty;
    }

    public void setWarning_qty(int warning_qty) {
        this.warning_qty = warning_qty;
    }

    public float getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(float purchase_price) {
        this.purchase_price = purchase_price;
    }

    public float getSell_price() {
        return sell_price;
    }

    public void setSell_price(float sell_price) {
        this.sell_price = sell_price;
    }
    
    @Override
    public String toString(){
        return this.getManufacturer() + " -- " + this.getName();
    }
    
}
