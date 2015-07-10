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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class LoginController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void onLoginButtonClick(ActionEvent event) {
        String username = this.username.getText();
        String password = this.password.getText();
        
        Database db = new Database();
        try {
            Connection c = db.getConnection();
            ResultSet rs = c.createStatement().executeQuery("select * from users where username='"+ username +"'");
            if(rs.getString("password").equals(password)){
                Parent root = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
                Scene scene = this.username.getScene();
                Stage stage = (Stage)this.username.getScene().getWindow();
                scene.setRoot(root);
                stage.setScene(scene);
            }else{
                System.out.println("incorrect password");
            }
            rs.close();
            c.close();
        } catch (Exception ex) {
            System.out.println("error");
        }
    }
    
}
