/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import jamuna.AutoCompleteComboBoxListener;
import jamuna.Database;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Category;
import model.Model;
import model.Supplier;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class AddStockController implements Initializable {
    @FXML
    private ComboBox<Category> category;
    @FXML
    private ComboBox<Model> model;
    @FXML
    private TextField purchase_price;
    @FXML
    private TextField sell_price;
    @FXML
    private TextField sl_no;
    @FXML
    private Button save;
    @FXML
    private Button cancel;
    @FXML
    private ComboBox<Supplier> supplier;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        save.setGraphic(new ImageView(new Image("/images/save.png")));
        cancel.setGraphic(new ImageView(new Image("/images/cancel.png")));
        try{
            Database db = new Database();
            Connection c = db.getConnection();
            ResultSet rs = c.createStatement().executeQuery("select * from category");
            while(rs.next()){
                
                this.category.getItems().add(new Category(rs.getInt("id"), rs.getString("name")));
            }
            rs.close();
            c.close();
            
            Connection c2 = db.getConnection();
            ResultSet rs2 = c2.createStatement().executeQuery("select * from supplier");
            while(rs2.next()){
                this.supplier.getItems().add(new Supplier(rs2.getInt("id"), rs2.getString("name"), rs2.getString("phone"), rs2.getString("address")));
            }
            rs2.close();
            c2.close();
            
            
        }catch(Exception e){
            
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
        new AutoCompleteComboBoxListener<>(this.model);
        this.model.setOnHiding((e)->{
            Model a = this.model.getSelectionModel().getSelectedItem();
            this.model.setEditable(false);
            this.model.getSelectionModel().select(a);
        });
        this.model.setOnShowing((e)->{
            this.model.setEditable(true);
        });
        new AutoCompleteComboBoxListener<>(this.supplier);
        this.supplier.setOnHiding((e)->{
            Supplier a = this.supplier.getSelectionModel().getSelectedItem();
            this.supplier.setEditable(false);
            this.supplier.getSelectionModel().select(a);
        });
        this.supplier.setOnShowing((e)->{
            this.supplier.setEditable(true);
        });
        
    }    

    @FXML
    private void onSelectCategory(ActionEvent event) {
        try {
            this.model.getItems().clear();
            int cat_id = this.category.getSelectionModel().getSelectedItem().getId();
            Database db = new Database();
            Connection c = db.getConnection();
            ResultSet rs = c.createStatement().executeQuery("select * from model where category_id="+cat_id);
            while(rs.next()){
                this.model.getItems().add(new Model(rs.getInt("id"), rs.getInt("category_id"), rs.getString("name"), rs.getString("color"), rs.getString("manufacturer"), rs.getInt("warning_qty"), rs.getFloat("purchase_price"), rs.getFloat("sell_price")));
            }
            rs.close();
            c.close();
        } catch (Exception ex) {
            
        }
    }

    @FXML
    private void onSelectModel(ActionEvent event) {
        try {
            int model_id = this.model.getSelectionModel().getSelectedItem().getId();
            Database db = new Database();
            Connection c =  db.getConnection();
            ResultSet rs = c.createStatement().executeQuery("select * from model where id=" + model_id);
            while(rs.next()){
                this.purchase_price.setText(String.valueOf(rs.getFloat("purchase_price")));
                this.sell_price.setText(String.valueOf(rs.getFloat("sell_price")));
            }
            rs.close();
            c.close();
            
            
        } catch (Exception ex) {
            
        }
    }

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        try {
            String sl = this.sl_no.getText();
            int model_id = this.model.getSelectionModel().getSelectedItem().getId();
            int supplier_id = this.supplier.getSelectionModel().getSelectedItem().getId();
            float p_price = Float.parseFloat(this.purchase_price.getText());
            float s_price = Float.parseFloat(this.sell_price.getText());
            save.setDisable(true);
            
            Database db = new Database();
            Connection c = db.getConnection();
            c.createStatement().execute("insert into stock (sl,model,supplier,p_price,s_price) values ('"+ sl +"',"+ model_id +","+ supplier_id +","+ p_price +","+ s_price +")");
            ResultSet rs = c.createStatement().executeQuery("SELECT * FROM stock order by id desc limit 1");
            while(rs.next()){
                c.createStatement().execute("insert into all_products (sl,model,supplier,p_price,s_price,stock_id) values ('"+ sl +"',"+ model_id +","+ supplier_id +","+ p_price +","+ s_price +","+ rs.getInt("id") +")");
            }
            
            c.close();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Product has been added successfully!");
            alert.setGraphic(new ImageView(new Image("/images/success.jpg")));
            alert.showAndWait();
            
            this.category.getSelectionModel().clearSelection();
            this.model.getSelectionModel().clearSelection();
            this.purchase_price.setText("");
            this.sell_price.setText("");
            this.sl_no.setText("");
            this.save.setDisable(false);
            
            
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Sorry!! there is an error. Please try again.");
            alert.setGraphic(new ImageView(new Image("/images/error.jpg")));
            alert.showAndWait();
        }
        
        
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }
    
}
