/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import jamuna.Database;
import jamuna.Report;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Msg;
import model.Supplier;
import model.SupplierReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class SupplierReportController implements Initializable {
    @FXML
    private ComboBox<Supplier> select_supplier;
    @FXML
    private TextField phone;
    @FXML
    private TextArea address;
    @FXML
    private Button show_report;
    @FXML
    private Button export_report;
    @FXML
    private TableView<SupplierReport> report_table;
    @FXML
    private TableColumn<SupplierReport, String> sl;
    @FXML
    private TableColumn<SupplierReport, String> category;
    @FXML
    private TableColumn<SupplierReport, String> model;
    @FXML
    private TableColumn<SupplierReport, String> serial;
    @FXML
    private TableColumn<SupplierReport, String> purchasePrice;
    @FXML
    private TableColumn<SupplierReport, String> sellPrice;
    
    private ObservableList<SupplierReport> report_list;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        report_list = FXCollections.observableArrayList();
        
        sl.setCellValueFactory(new PropertyValueFactory<SupplierReport, String>("sl"));
        category.setCellValueFactory(new PropertyValueFactory<SupplierReport, String>("category"));
        model.setCellValueFactory(new PropertyValueFactory<SupplierReport, String>("model"));
        serial.setCellValueFactory(new PropertyValueFactory<SupplierReport, String>("serial"));
        purchasePrice.setCellValueFactory(new PropertyValueFactory<SupplierReport, String>("purchasePrice"));
        sellPrice.setCellValueFactory(new PropertyValueFactory<SupplierReport, String>("sellPrice"));
        
        this.report_table.setItems(report_list);
        
        Database db = new Database();
        Connection c = null;
        ResultSet rs = null;
        
        try {
            c = db.getConnection();
            rs  =c.createStatement().executeQuery("select * from supplier");
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                this.select_supplier.getItems().add(new Supplier(id, name, phone, address));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SupplierReportController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                rs.close();
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(SupplierReportController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }    

    @FXML
    private void onSupplierSelect(ActionEvent event) {
        this.phone.setText(this.select_supplier.getSelectionModel().getSelectedItem().getPhone());
        this.address.setText(this.select_supplier.getSelectionModel().getSelectedItem().getAddress());
    }

    @FXML
    private void onShowReportClick(ActionEvent event) {
        report_list.clear();
        Database db = new Database();
        Connection c = null;
        ResultSet rs = null;
        
        try {
            c = db.getConnection();
            rs = c.createStatement().executeQuery("select all_products.sl,all_products.p_price,all_products.s_price,(select name from model where id=all_products.model) as model,(select name from category where id=(select category_id from model where id=all_products.model)) as category from all_products where supplier=" + this.select_supplier.getSelectionModel().getSelectedItem().getId());
            int i = 1;
            while(rs.next()){
                String sl = String.valueOf(i);
                String serial = rs.getString("sl");
                String category = rs.getString("category");
                String model = rs.getString("model");
                String purchasePrice = rs.getString("p_price");
                String sellPrice = rs.getString("s_price");
                report_list.add(new SupplierReport(sl, category, model, serial, purchasePrice, sellPrice));
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierReportController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SupplierReportController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        }finally{
            try {
                rs.close();
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(SupplierReportController.class.getName()).log(Level.SEVERE, null, ex);
                Msg.showError("");
            }
        }
    }

    @FXML
    private void onExportReportClick(ActionEvent event) {
        Database db = new Database();
        Connection c = null;
        ResultSet rs = null;
        
        try {
            c = db.getConnection();
            rs = c.createStatement().executeQuery("select all_products.sl,all_products.p_price,all_products.s_price,(select name from model where id=all_products.model) as model,(select name from category where id=(select category_id from model where id=all_products.model)) as category from all_products where supplier=" + this.select_supplier.getSelectionModel().getSelectedItem().getId());
            int i = 1;
            Vector v = new Vector();
            HashMap params = new HashMap();
            while(rs.next()){
                String sl = String.valueOf(i);
                String serial = rs.getString("sl");
                String category = rs.getString("category");
                String model = rs.getString("model");
                String purchasePrice = rs.getString("p_price");
                String sellPrice = rs.getString("s_price");
                v.add(new SupplierReport(sl, category, model, serial, purchasePrice, sellPrice));
                i++;
            }
            params.put("supplier", this.select_supplier.getSelectionModel().getSelectedItem().getName());
            Report report = new Report();
            report.getReport("src\\report\\SupplierReport.jrxml", new JRBeanCollectionDataSource(v), params);
            
        } catch (SQLException ex) {
            Logger.getLogger(SupplierReportController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SupplierReportController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        }finally{
            try {
                rs.close();
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(SupplierReportController.class.getName()).log(Level.SEVERE, null, ex);
                Msg.showError("");
            }
        }
    }
    
}
