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
import java.text.ParseException;
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
import model.Customer;
import model.Msg;
import model.Stock;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class InstallmentPaymentController implements Initializable {
    @FXML
    private ComboBox<Customer> select_customer;
    @FXML
    private TextField name;
    @FXML
    private TextArea address;
    @FXML
    private TextField phone;
    @FXML
    private ComboBox<Stock> select_serial;
    @FXML
    private DatePicker date;
    @FXML
    private TextField amount;
    @FXML
    private Button save;
    @FXML
    private Button cancel;
    @FXML
    private TextField category;
    @FXML
    private TextField model;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Database db = new Database();
        Connection c = null;
        ResultSet rs = null;
        try {
            c = db.getConnection();
            rs = c.createStatement().executeQuery("select * from customer");
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                Customer customer = new Customer(id,name,phone,address);
                this.select_customer.getItems().add(customer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(InstallmentPaymentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InstallmentPaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                rs.close();
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(InstallmentPaymentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        new AutoCompleteComboBoxListener<>(this.select_customer);
        this.select_customer.setOnHiding((e)->{
            Customer a = this.select_customer.getSelectionModel().getSelectedItem();
            this.select_customer.setEditable(false);
            this.select_customer.getSelectionModel().select(a);
        });
        this.select_customer.setOnShowing((e)->{
            this.select_customer.setEditable(true);
        });
        new AutoCompleteComboBoxListener<>(this.select_serial);
        this.select_serial.setOnHiding((e)->{
            Stock a = this.select_serial.getSelectionModel().getSelectedItem();
            this.select_serial.setEditable(false);
            this.select_serial.getSelectionModel().select(a);
        });
        this.select_serial.setOnShowing((e)->{
            this.select_serial.setEditable(true);
        });
    }    

    @FXML
    private void onCustomerSelect(ActionEvent event) {
        this.select_serial.getItems().clear();
        String name = this.select_customer.getSelectionModel().getSelectedItem().getName();
        String phone = this.select_customer.getSelectionModel().getSelectedItem().getPhone();
        String address = this.select_customer.getSelectionModel().getSelectedItem().getAddress();
        
        this.name.setText(name);
        this.phone.setText(phone);
        this.address.setText(address);
        
        Database db = new Database();
        Connection c = null;
        Connection c2  =null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        try {
            c = db.getConnection();
            c2 = db.getConnection();
            rs = c.createStatement().executeQuery("SELECT * FROM hire where customer_id=" + this.select_customer.getSelectionModel().getSelectedItem().getId());
            
            while(rs.next()){
                rs2 = c2.createStatement().executeQuery("select * from all_products where stock_id=" + rs.getInt("stock_id"));
                while(rs2.next()){
                    int id = rs2.getInt("stock_id");
                    String sl = rs2.getString("sl");
                    int model = rs2.getInt("model");
                    float p_price = rs2.getFloat("p_price");
                    float s_price = rs2.getFloat("s_price");
                    int supplier = rs2.getInt("supplier");
                    this.select_serial.getItems().add(new Stock(id,sl,model,p_price,s_price,supplier));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(InstallmentPaymentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InstallmentPaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                c.close();
                c2.close();
                rs.close();
                rs2.close();
            } catch (SQLException ex) {
                Logger.getLogger(InstallmentPaymentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }


    @FXML
    private void onSaveButtonClick(ActionEvent event) throws ParseException {
        
        Database db = new Database();
        Connection c = null;
        ResultSet rs = null;
        int hire_id = 0;
        String date = this.date.getValue().toString();
        String amount = this.amount.getText();
        try {
            c = db.getConnection();
            rs = c.createStatement().executeQuery("select id from hire where stock_id=(select stock_id from all_products where sl='"+ this.select_serial.getSelectionModel().getSelectedItem().getSl() +"' and model="+ this.select_serial.getSelectionModel().getSelectedItem().getModel() +")");
            while(rs.next()){
                hire_id = rs.getInt("id");
            }
            
            c.createStatement().execute("insert into hire_payments (hire_id,date,amount) values ("+ hire_id +",'"+ date +"',"+ amount +")");
            Msg.showInformation("Payments received successfully");
            this.save.getScene().getWindow().hide();
            
            // show receipt
            rs = c.createStatement().executeQuery("select id from hire_payments order by id desc limit 1");
            String sl = null;
            while(rs.next()){
                sl = String.valueOf(rs.getInt("id"));
            }
            date = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.date.getValue().toString()));
            String customer_name = this.name.getText();
            String phone = this.phone.getText();
            String address = this.address.getText();
            String model = this.model.getText();
            String category = this.category.getText();
            String serial_no = this.select_serial.getSelectionModel().getSelectedItem().getSl();
            String received_amount = this.amount.getText();
            String word = EnglishNumberToWords.convert(Long.parseLong(received_amount));
            HashMap params = new HashMap();
            params.put("sl", sl);
            params.put("date", date);
            params.put("customer_name", customer_name);
            params.put("phone", phone);
            params.put("address", address);
            params.put("category", category);
            params.put("model", model);
            params.put("serial_no", serial_no);
            params.put("received_amount", received_amount);
            params.put("word", word + " taka only");
            
            Report report = new Report();
            Vector v = new Vector();
            v.add("");
            
            report.getReport("src\\report\\InstallmentPaymentReceipt.jrxml", new JRBeanCollectionDataSource(v), params);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(InstallmentPaymentController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InstallmentPaymentController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        }finally{
            try {
                c.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(InstallmentPaymentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }

    @FXML
    private void onSerialSelect(ActionEvent event) {
        int model_id = this.select_serial.getSelectionModel().getSelectedItem().getModel();
        Database db = new Database();
        ResultSet rs = null;
        Connection c = null;
        
        try {
            c = db.getConnection();
            rs = c.createStatement().executeQuery("select model.name as model,cat.name as category from model,(select name from category where id=(select category_id from model where id="+ model_id +")) as cat where id=" + model_id);
            while(rs.next()){
                this.category.setText(rs.getString("category"));
                this.model.setText(rs.getString("model"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(InstallmentPaymentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InstallmentPaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                rs.close();
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(InstallmentPaymentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
