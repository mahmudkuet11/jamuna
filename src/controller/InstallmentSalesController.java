/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import jamuna.AutoCompleteComboBoxListener;
import jamuna.Database;
import jamuna.EnglishNumberToWords;
import jamuna.Report;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Category;
import model.Customer;
import model.Model;
import model.Msg;
import model.Stock;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class InstallmentSalesController implements Initializable {
    @FXML
    private ComboBox<Customer> customer;
    @FXML
    private DatePicker date;
    @FXML
    private ComboBox<Category> category;
    @FXML
    private TextField phone;
    @FXML
    private TextArea address;
    @FXML
    private ComboBox<Model> model;
    @FXML
    private ComboBox<Stock> item;
    @FXML
    private TextField cash_price;
    @FXML
    private TextField hire_price;
    @FXML
    private TextField down_payment;
    @FXML
    private TextField number_of_installment;
    @FXML
    private TextField monthly_installment;
    @FXML
    private Button save;
    @FXML
    private Button cancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        Database db = new Database();
        Connection con = null;
        ResultSet rs = null;
        try {
            con = db.getConnection();
            // customer
            rs = con.createStatement().executeQuery("SELECT * FROM customer");
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                
                this.customer.getItems().add(new Customer(id, name, phone, address));
                
            }
            
            // category
            rs = con.createStatement().executeQuery("SELECT * FROM category");
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                
                this.category.getItems().add(new Category(id, name));
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(InstallmentSalesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InstallmentSalesController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                con.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(InstallmentSalesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        new AutoCompleteComboBoxListener<>(this.category);
        this.category.setOnHiding((e)->{
            Category a = this.category.getSelectionModel().getSelectedItem();
            this.category.setEditable(false);
            this.category.getSelectionModel().select(a);
        });
        this.category.setOnShowing((e)->{
            this.category.setEditable(true);
        });
        new AutoCompleteComboBoxListener<>(this.customer);
        this.customer.setOnHiding((e)->{
            Customer a = this.customer.getSelectionModel().getSelectedItem();
            this.customer.setEditable(false);
            this.customer.getSelectionModel().select(a);
        });
        this.customer.setOnShowing((e)->{
            this.customer.setEditable(true);
        });
        new AutoCompleteComboBoxListener<>(this.item);
        this.item.setOnHiding((e)->{
            Stock a = this.item.getSelectionModel().getSelectedItem();
            this.item.setEditable(false);
            this.item.getSelectionModel().select(a);
        });
        this.item.setOnShowing((e)->{
            this.item.setEditable(true);
        });
        
        new AutoCompleteComboBoxListener<>(this.model);
        this.model.setOnHiding((e)->{
            Model a = this.model.getSelectionModel().getSelectedItem();
            this.model.setEditable(false);
            this.model.getSelectionModel().select(a);
        });
        this.model.setOnShowing((e)->{
            this.model.setEditable(true);
        });
        
    }    

    @FXML
    private void onSelectCustomer(ActionEvent event) {
        String phone = this.customer.getSelectionModel().getSelectedItem().getPhone();
        String address = this.customer.getSelectionModel().getSelectedItem().getAddress();
        
        this.phone.setText(phone);
        this.address.setText(address);
    }

    @FXML
    private void onSelectCategory(ActionEvent event) {
        Database db = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            this.model.getSelectionModel().clearSelection();
            this.model.getItems().clear();
            this.item.getSelectionModel().clearSelection();
            this.item.getItems().clear();
            int selected_category = this.category.getSelectionModel().getSelectedItem().getId();
            db = new Database();
            con = db.getConnection();
            rs = con.createStatement().executeQuery("select * from model where category_id=" + selected_category);
            while(rs.next()){
                int id = rs.getInt("id");
                int category_id = rs.getInt("category_id");
                String name = rs.getString("name");
                String color = rs.getString("color");
                String manufacturer = rs.getString("manufacturer");
                int warning_qty = rs.getInt("warning_qty");
                float purchase_price = rs.getFloat("purchase_price");
                float sell_price = rs.getFloat("sell_price");
                
                this.model.getItems().add(new Model(id, category_id, name, color, manufacturer, warning_qty, purchase_price, sell_price));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(InstallmentSalesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InstallmentSalesController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                con.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(InstallmentSalesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @FXML
    private void onSelectModel(ActionEvent event) {
        
        this.item.getSelectionModel().clearSelection();
        this.item.getItems().clear();
        
        Database db = new Database();
        Connection con = null;
        ResultSet rs = null;
        
        try {
            con = db.getConnection();
            int model_id = this.model.getSelectionModel().getSelectedItem().getId();
            rs = con.createStatement().executeQuery("select * from stock where model=" + model_id);
            while(rs.next()){
                int id = rs.getInt("id");
                String sl = rs.getString("sl");
                int model = rs.getInt("model");
                float p_price = rs.getFloat("p_price");
                float s_price = rs.getFloat("s_price");
                int supplier = rs.getInt("supplier");
                
                this.item.getItems().add(new Stock(id, sl, model, p_price, s_price, supplier));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(InstallmentSalesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InstallmentSalesController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                con.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(InstallmentSalesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }

    @FXML
    private void onSelectItem(ActionEvent event) {
        this.cash_price.setText(String.valueOf(this.item.getSelectionModel().getSelectedItem().getS_price()));
    }

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        Connection c = null;
        ResultSet rs = null;
        try {
            int customer_id = this.customer.getSelectionModel().getSelectedItem().getId();
            String date = this.date.getValue().toString();
            int stock_id = this.item.getSelectionModel().getSelectedItem().getId();
            float cash_price = Float.parseFloat(this.cash_price.getText());
            float hire_price = Float.parseFloat(this.hire_price.getText());
            float down_payment = Float.parseFloat(this.down_payment.getText());
            int number_of_installments = Integer.parseInt(this.number_of_installment.getText());
            float monthly_installments = Float.parseFloat(this.monthly_installment.getText());
            
            Database db = new Database();
            c = db.getConnection();
            c.createStatement().execute("insert into hire (stock_id,customer_id,cash_price,hire_price,down_payment,monthly_installment,number_of_installments,date) values ("+ stock_id +","+ customer_id +","+ cash_price +","+ hire_price +","+ down_payment +","+ monthly_installments +","+ number_of_installments +",'"+ date +"')");
        
            // insert in sell table and delete from stock
            
            String customer_name = this.customer.getSelectionModel().getSelectedItem().getName();
            String phone = this.customer.getSelectionModel().getSelectedItem().getPhone();
            String address = this.customer.getSelectionModel().getSelectedItem().getAddress();
            float price = Float.parseFloat(this.hire_price.getText());
            String word = EnglishNumberToWords.convert(Long.parseLong(this.hire_price.getText()));
            c.createStatement().execute("insert into sell (date,customer_name,phone,address,price,word,stock_id) values ('"+ date +"','"+ customer_name +"','"+ phone +"','"+ address +"',"+ price +",'"+ word +"',"+ stock_id +")");
            c.createStatement().execute("delete from stock where id=" + stock_id);
            
            Msg.showInformation("Successfully Saved!!!");
            
            rs = c.createStatement().executeQuery("select id from hire order by id desc limit 1");
            String sl = "";
            while(rs.next()){
                sl = String.valueOf(rs.getInt("id"));
            }
            date = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));
            Vector v = new Vector();
            HashMap params = new HashMap();
            params.put("sl", sl);
            params.put("date", date);
            params.put("customer_name", customer_name);
            params.put("phone", phone);
            params.put("address", address);
            params.put("category", this.category.getSelectionModel().getSelectedItem().getName());
            params.put("model", this.model.getSelectionModel().getSelectedItem().getName());
            params.put("serial_no", this.item.getSelectionModel().getSelectedItem().getSl());
            params.put("hire_price", String.valueOf(hire_price));
            params.put("down_payment", String.valueOf(down_payment));
            params.put("number_of_installment", String.valueOf(number_of_installments));
            params.put("monthly_installment", String.valueOf(monthly_installments));
            
            v.add("a");
            Report report = new Report();
            report.getReport("src\\report\\InstallmentVoucher.jrxml", new JRBeanCollectionDataSource(v), params);
            
            this.save.getScene().getWindow().hide();
            
        } catch (SQLException ex) {
            Logger.getLogger(InstallmentSalesController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("Sorry. There is a problem. Please try again.");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InstallmentSalesController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("Sorry. There is a problem. Please try again.");
        } catch (Exception ex){
            Logger.getLogger(InstallmentSalesController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("Sorry. There is a problem. Please try again.");
        }finally{
            try {
                rs.close();
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(InstallmentSalesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }
    
}
