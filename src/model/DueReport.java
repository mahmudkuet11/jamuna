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
public class DueReport {
    private String serial;
    private String category;
    private String model;
    private String name;
    private String phone;
    private String address;
    private float price;
    private float paid;
    private float due;
    
    public DueReport(String serial, String category, String model, String name, String phone, String address, float price, float paid, float due){
        this.serial = serial;
        this.category = category;
        this.model = model;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.price = price;
        this.paid = paid;
        this.due = due;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPaid() {
        return paid;
    }

    public void setPaid(float paid) {
        this.paid = paid;
    }

    public float getDue() {
        return due;
    }

    public void setDue(float due) {
        this.due = due;
    }
}
