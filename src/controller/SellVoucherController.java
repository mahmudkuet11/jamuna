/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import jamuna.AutoCompleteComboBoxListener;
import jamuna.Database;
import jamuna.EnglishNumberToWords;
import jamuna.Helper;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import model.Category;
import model.Customer;
import model.Model;
import model.Stock;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class SellVoucherController implements Initializable {
    @FXML
    private DatePicker date;
    @FXML
    private ComboBox<Customer> select_customer;
    @FXML
    private TextField name;
    @FXML
    private TextField phone;
    @FXML
    private TextArea address;
    @FXML
    private ComboBox<Category> category;
    @FXML
    private ComboBox<Model> model;
    @FXML
    private ComboBox<Stock> sl_no;
    @FXML
    private TextField price;
    @FXML
    private TextField price_word;
    @FXML
    private Button reset;
    @FXML
    private Button ok;
    @FXML
    private Button cancel;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ok.setGraphic(new ImageView(new Image("/images/save.png")));
        reset.setGraphic(new ImageView(new Image("/images/reset.png")));
        cancel.setGraphic(new ImageView(new Image("/images/cancel.png")));
        
        try{
            Database db = new Database();
            Connection c = db.getConnection();
            ResultSet customers = c.createStatement().executeQuery("select * from customer");
            while(customers.next()){
                this.select_customer.getItems().add(new Customer(customers.getInt("id"), customers.getString("name"), customers.getString("phone"), customers.getString("address")));
                
            }
            customers.close();
            c.close();
        }catch(Exception e){
            
        }
        
        try{
            Database db = new Database();
            Connection c = db.getConnection();
            ResultSet categories = c.createStatement().executeQuery("select * from category");
            while(categories.next()){
                this.category.getItems().add(new Category(categories.getInt("id"),categories.getString("name")));
            }
            categories.close();
            c.close();
        }catch(Exception e){
            
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
        
        new AutoCompleteComboBoxListener<>(this.model);
        this.model.setOnHiding((e)->{
            Model a = this.model.getSelectionModel().getSelectedItem();
            this.model.setEditable(false);
            this.model.getSelectionModel().select(a);
        });
        this.model.setOnShowing((e)->{
            this.model.setEditable(true);
        });
        
        new AutoCompleteComboBoxListener<>(this.category);
        this.category.setOnHiding((e)->{
            Category a = this.category.getSelectionModel().getSelectedItem();
            this.category.setEditable(false);
            this.category.getSelectionModel().select(a);
        });
        this.category.setOnShowing((e)->{
            this.category.setEditable(true);
        });
        new AutoCompleteComboBoxListener<>(this.sl_no);
        this.sl_no.setOnHiding((e)->{
            Stock a = this.sl_no.getSelectionModel().getSelectedItem();
            this.sl_no.setEditable(false);
            this.sl_no.getSelectionModel().select(a);
        });
        this.sl_no.setOnShowing((e)->{
            this.sl_no.setEditable(true);
        });
        
       
     
        
    }    

    @FXML
    private void onCusomerSelect(ActionEvent event) {
        try {
            int id = this.select_customer.getSelectionModel().getSelectedItem().getId();
            Database db = new Database();
            Connection c = db.getConnection();
            ResultSet rs  = c.createStatement().executeQuery("select * from customer where id=" + id);
            this.name.setText(rs.getString("name"));
            this.phone.setText(rs.getString("phone"));
            this.address.setText(rs.getString("address"));
            c.close();
        } catch (Exception e) {
            
        }
    }

    @FXML
    private void onCategorySelect(ActionEvent event) {
        this.model.getItems().clear();
        try {
            Database db = new Database();
            Connection c = db.getConnection();
            ResultSet rs = c.createStatement().executeQuery("select * from model where category_id=" + this.category.getSelectionModel().getSelectedItem().getId());
            while(rs.next()){
                int id, cat_id,warning_qty;
                String name,color,manufacturer;
                float p_price,s_price;
                id = rs.getInt("id");
                cat_id = rs.getInt("category_id");
                name = rs.getString("name");
                color = rs.getString("color");
                manufacturer = rs.getString("manufacturer");
                warning_qty = rs.getInt("warning_qty");
                p_price = rs.getFloat("purchase_price");
                s_price = rs.getFloat("sell_price");
                this.model.getItems().add(new Model(id,cat_id,name,color,manufacturer,warning_qty,p_price,s_price));
            }
            rs.close();
            c.close();
        } catch (Exception e) {
        }
        
    }

    @FXML
    private void onModelSelect(ActionEvent event) {
        this.sl_no.getItems().clear();
        try {
            Database db = new Database();
            Connection c = db.getConnection();
            ResultSet rs = c.createStatement().executeQuery("select * from stock where model=" + this.model.getSelectionModel().getSelectedItem().getId());
            while (rs.next()) {
                int id = rs.getInt("id");
                String sl = rs.getString("sl");
                int model = rs.getInt("model");
                float p_price = rs.getFloat("p_price");
                float s_price = rs.getFloat("s_price");
                int supplier = rs.getInt("supplier");
                this.sl_no.getItems().add(new Stock(id,sl,model,p_price,s_price,supplier));
            }
            rs.close();
            c.close();
        } catch (Exception e) {
        }
    }

    @FXML
    private void onSerialSelect(ActionEvent event) {
        String price = String.valueOf(this.sl_no.getSelectionModel().getSelectedItem().getS_price());
        this.price.setText(price);
        updateInWord();
    }

    @FXML
    private void onResetButtonClick(ActionEvent event) {
        this.select_customer.getSelectionModel().clearSelection();
        this.name.setText("");
        this.phone.setText("");
        this.address.setText("");
        this.date.setValue(null);
        this.category.getSelectionModel().clearSelection();
        this.model.getSelectionModel().clearSelection();
        this.sl_no.getSelectionModel().clearSelection();
        this.price.setText("");
        this.price_word.setText("");
    }

    @FXML
    private void onOkButtonClick(ActionEvent event) {
        this.ok.setDisable(true);
        
        String date = "";
        try {
            date = this.date.getValue().toString();
        } catch (Exception ex) {
            Logger.getLogger(SellVoucherController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String customer_name = this.name.getText();
        String phone = this.phone.getText();
        String address = this.address.getText();
        int stock_id = this.sl_no.getSelectionModel().getSelectedItem().getId();
        float price = Float.parseFloat(this.price.getText());
        String word = this.price_word.getText();
        Connection c = null;
        try {
            Database db = new Database();
            c = db.getConnection();
            c.createStatement().execute("insert into sell (date,customer_name,phone,address,price,word,stock_id) values ('"+ date +"','"+ customer_name +"','"+ phone +"','"+ address +"',"+ price +",'"+ word +"',"+ stock_id +")");
            c.createStatement().execute("delete from stock where id=" + stock_id);
            c.close();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Product has been sold successfully!");
            alert.setGraphic(new ImageView(new Image("/images/success.jpg")));
            alert.showAndWait();
            
            Report report = new Report();
            HashMap params = new HashMap();
            params.put("sl", String.valueOf(Helper.getNextSerial()));
            params.put("date", new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.date.getValue().toString())));
            params.put("customer_name", this.name.getText());
            params.put("address", this.address.getText());
            params.put("phone", this.phone.getText());
            params.put("category", this.category.getSelectionModel().getSelectedItem().getName());
            params.put("model", this.model.getSelectionModel().getSelectedItem().getName());
            params.put("serial_no", this.sl_no.getSelectionModel().getSelectedItem().getSl());
            params.put("amount", this.price.getText());
            params.put("total_words", this.price_word.getText());
            
            Vector v = new Vector();
            v.add("a");
            
            report.getReport("src\\report\\SellVoucher.jrxml", new JRBeanCollectionDataSource(v), params);
            
            
            
        } catch (Exception ex) {
            Logger.getLogger(SellVoucherController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Sorry!! there is an error. Please try again");
            alert.setGraphic(new ImageView(new Image("/images/error.jpg")));
            alert.showAndWait();
        }finally{
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(SellVoucherController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }
    
    @FXML
    private void onEnterPrice(KeyEvent event) {
        updateInWord();
    }
    public void updateInWord(){
        this.price_word.setText(EnglishNumberToWords.convert(Float.valueOf(this.price.getText()).longValue()) + " taka only");
    }
}
