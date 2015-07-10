/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

/**
 *
 * @author mohar
 */
public class NavbarController implements Initializable{
    @FXML
    private MenuBar menubar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onAddNewCategoryClick(ActionEvent event) {
        
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/view/AddNewCategory.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Add new category");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(NavbarController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void onAddNewModelClick(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/view/AddNewModel.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Add new Model");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(NavbarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onAddStockClick(ActionEvent event) {
         try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/view/AddStock.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Add Stock");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(NavbarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onAddSupplierClick(ActionEvent event) {
         try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/view/AddNewSupplier.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Add New Supplier");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(NavbarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onAddCustomerClick(ActionEvent event) {
         try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/view/AddNewCustomer.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Add New Customer");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(NavbarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onSellVoucherClick(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/view/SellVoucher.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Sell Voucher");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(NavbarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
