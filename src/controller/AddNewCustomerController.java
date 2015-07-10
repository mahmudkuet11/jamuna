/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import jamuna.Database;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class AddNewCustomerController implements Initializable {
    @FXML
    private TextField name;
    @FXML
    private TextField phone;
    @FXML
    private TextArea address;
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
    }    

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        
        this.save.setDisable(true);
        
        String name = this.name.getText();
        String phone = this.phone.getText();
        String address = this.address.getText();
        
        try{
            Database db = new Database();
            Connection c = db.getConnection();
            c.createStatement().execute("insert into customer (name,phone,address) values ('"+ name +"','"+ phone +"','"+ address +"')");
            c.close();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Customer \""+ name +"\" has been added successfully!");
            alert.setGraphic(new ImageView(new Image("/images/success.jpg")));
            alert.showAndWait();
            save.getScene().getWindow().hide();
            
        }catch(Exception e){
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
