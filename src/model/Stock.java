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
public class Stock {
    private int id;
    private int model;
    private int supplier;
    private String sl;
    private float p_price;
    private float s_price;
    
    public Stock(int id, String sl, int model, float p_price, float s_price, int supplier){
        this.id = id;
        this.sl = sl;
        this.model = model;
        this.p_price = p_price;
        this.s_price = s_price;
        this.supplier = supplier;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public int getSupplier() {
        return supplier;
    }

    public void setSupplier(int supplier) {
        this.supplier = supplier;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public float getP_price() {
        return p_price;
    }

    public void setP_price(float p_price) {
        this.p_price = p_price;
    }

    public float getS_price() {
        return s_price;
    }

    public void setS_price(float s_price) {
        this.s_price = s_price;
    }
    
    @Override
    public String toString(){
        return this.getSl();
    }
    
}
