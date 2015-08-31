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
public class SellReport {
    private int sl;
    private String date;
    private String category;
    private String model;
    private String serial;
    private String name;
    private String address;
    private String phone;
    private float amount;
    
    private String slString,amountString;
    
    
    public SellReport(int sl, String date, String category, String model, String serial, String name, String address, String phone, float amount){
        this.sl = sl;
        this.date = date;
        this.category = category;
        this.model = model;
        this.serial = serial;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.amount = amount;
        this.setSlString(String.valueOf(sl));
        this.setAmountString(String.valueOf(amount));
    }
    
    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getSlString() {
        return slString;
    }

    public void setSlString(String slString) {
        this.slString = slString;
    }

    public String getAmountString() {
        return amountString;
    }

    public void setAmountString(String amountString) {
        this.amountString = amountString;
    }
    
}
