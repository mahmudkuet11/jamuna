/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import jamuna.Database;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.Category;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class AddNewModelController implements Initializable {
    @FXML
    private ComboBox<Category> category;
    @FXML
    private TextField model;
    @FXML
    private ColorPicker color;
    @FXML
    private TextField manufacturer;
    @FXML
    private TextField warning_qty;
    @FXML
    private TextField purchase_price;
    @FXML
    private TextField sell_price;
    @FXML
    private Button save;
    @FXML
    private Button cancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        save.setGraphic(new ImageView(new Image("/images/save.png")));
        cancel.setGraphic(new ImageView(new Image("/images/cancel.png")));
        
        try {
            Database db = new Database();
            Connection c = db.getConnection();
            ResultSet rs = c.createStatement().executeQuery("select * from category");
            while(rs.next()){
                category.getItems().add(new Category(rs.getInt("id"), rs.getString("name")));
            }
            rs.close();
            c.close();
        } catch (Exception ex) {
            Logger.getLogger(AddNewModelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }    

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        this.save.setDisable(true);
        try {
            int category = this.category.getSelectionModel().getSelectedItem().getId();
            String model = this.model.getText();
            String color = this.color.getValue().toString();
            String manufacturer = this.manufacturer.getText();
            int warinig_qty = Integer.parseInt(this.warning_qty.getText());
            float purchase_price = Float.parseFloat(this.purchase_price.getText());
            float sell_price = Float.parseFloat(this.sell_price.getText());
            
            Database db = new Database();
            Connection c = db.getConnection();
            c.createStatement().execute("insert into model (category_id,name,color,manufacturer,warning_qty,purchase_price,sell_price) values ("+ category +",'"+ model +"','"+ color +"','"+ manufacturer +"',"+ warinig_qty +","+ purchase_price +","+ sell_price +")");
            c.close();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Model \""+ model +"\" has been added successfully!");
            alert.setGraphic(new ImageView(new Image("/images/success.jpg")));
            alert.showAndWait();
            save.getScene().getWindow().hide();
            
            
            
            
            
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Sorry!! there is an error. Please try again with different model name.");
            alert.setGraphic(new ImageView(new Image("/images/error.jpg")));
            alert.showAndWait();
        }
        
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        cancel.getScene().getWindow().hide();
    }
    
}
