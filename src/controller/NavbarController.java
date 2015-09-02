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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author mohar
 */
public class NavbarController implements Initializable{
    @FXML
    private MenuBar menubar;
    @FXML
    private ImageView dashboard_image;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dashboard_image.setImage(new Image("/images/logo.png"));
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

    @FXML
    private void onInstallmentSaleClick(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/view/InstallmentSales.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Installment Sales");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(NavbarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onInstallmentPaymentClick(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/view/InstallmentPayment.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Installment Payment");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(NavbarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onSellReportClick(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/view/SellReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Sell Report");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(NavbarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onCustomerReportClick(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/view/CustomerReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Customer Report");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(NavbarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onSupplierReportClick(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/view/SupplierReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Suppleier Report");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(NavbarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onLogoutButtonClick(ActionEvent event) {
        this.menubar.getScene().getWindow().hide();
    }

    @FXML
    private void onStockReportClick(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/view/StockReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Stock Report");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(NavbarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onDueReportClick(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/view/DueReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Due Report");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(NavbarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onNewChallanMemoClick(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/view/ChallanMemo.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Chalan Memo");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(NavbarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onViewChallanMemoClick(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/view/ViewChallanMemo.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("View Chalan Memo");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(NavbarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
