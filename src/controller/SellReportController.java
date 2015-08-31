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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Msg;
import model.SellReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class SellReportController implements Initializable {
    @FXML
    private DatePicker start_date;
    @FXML
    private DatePicker end_date;
    @FXML
    private Button show_report;
    @FXML
    private TableView<SellReport> report;
    @FXML
    private TableColumn<SellReport, Integer> sl;
    @FXML
    private TableColumn<SellReport, String> date;
    @FXML
    private TableColumn<SellReport, String> category;
    @FXML
    private TableColumn<SellReport, String> model;
    @FXML
    private TableColumn<SellReport, String> serial;
    @FXML
    private TableColumn<SellReport, String> name;
    @FXML
    private TableColumn<SellReport, String> address;
    @FXML
    private TableColumn<SellReport, String> phone;
    @FXML
    private TableColumn<SellReport, Float> amount;
    @FXML
    private Button export_report;
    
    private ObservableList<SellReport> report_list;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        report_list = FXCollections.observableArrayList();
        sl.setCellValueFactory(new PropertyValueFactory<SellReport,Integer>("sl"));
        date.setCellValueFactory(new PropertyValueFactory<SellReport,String>("date"));
        category.setCellValueFactory(new PropertyValueFactory<SellReport,String>("category"));
        model.setCellValueFactory(new PropertyValueFactory<SellReport,String>("model"));
        serial.setCellValueFactory(new PropertyValueFactory<SellReport,String>("serial"));
        name.setCellValueFactory(new PropertyValueFactory<SellReport,String>("name"));
        address.setCellValueFactory(new PropertyValueFactory<SellReport,String>("address"));
        phone.setCellValueFactory(new PropertyValueFactory<SellReport,String>("phone"));
        amount.setCellValueFactory(new PropertyValueFactory<SellReport,Float>("amount"));
        
        this.report.setItems(report_list);
    }    

    @FXML
    private void onShowReportButtonClick(ActionEvent event) {
        this.report_list.clear();
        String start_date = this.start_date.getValue().toString();
        String end_date = this.end_date.getValue().toString();
        
        Database db = new Database();
        Connection c = null;
        ResultSet rs = null;
        
        try {
            c = db.getConnection();
            rs = c.createStatement().executeQuery("select sell.date,sell.customer_name,sell.phone,sell.address,sell.price,all_products.sl,(select model.name from model where model.id=all_products.model) as model,(select name from category where id=(select category_id from model where id=all_products.model)) as category from sell,all_products where sell.stock_id=all_products.stock_id and date between '"+ start_date +"' and '"+ end_date +"'");
            int sl = 1;
            while(rs.next()){
                String date = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("date")));
                String category = rs.getString("category");
                String model = rs.getString("model");
                String serial = rs.getString("sl");
                String name = rs.getString("customer_name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                Float amount = rs.getFloat("price");
                
                report_list.add(new SellReport(sl, date, category, model, serial, name, address, phone, amount));
                
                sl++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SellReportController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SellReportController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        } catch (ParseException ex) {
            Logger.getLogger(SellReportController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                rs.close();
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(SellReportController.class.getName()).log(Level.SEVERE, null, ex);
                Msg.showError("");
            }
        }
    }

    @FXML
    private void onExportReportButtonClick(ActionEvent event) {
        String start_date = this.start_date.getValue().toString();
        String end_date = this.end_date.getValue().toString();
        
        Database db = new Database();
        Connection c = null;
        ResultSet rs = null;
        
        try {
            c = db.getConnection();
            rs = c.createStatement().executeQuery("select sell.date,sell.customer_name,sell.phone,sell.address,sell.price,all_products.sl,(select model.name from model where model.id=all_products.model) as model,(select name from category where id=(select category_id from model where id=all_products.model)) as category from sell,all_products where sell.stock_id=all_products.stock_id and date between '"+ start_date +"' and '"+ end_date +"'");
            int sl = 1;
            Vector v = new Vector();
            HashMap params = new HashMap();
            while(rs.next()){
                String date = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("date")));
                String category = rs.getString("category");
                String model = rs.getString("model");
                String serial = rs.getString("sl");
                String name = rs.getString("customer_name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                Float amount = rs.getFloat("price");
                
                v.add(new SellReport(sl, date, category, model, serial, name, address, phone, amount));
                
                sl++;
            }
            
            params.put("date", "From "+ new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(start_date)) +" to " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(end_date)));
            Report report = new Report();
            report.getReport("src\\report\\SellReport.jrxml", new JRBeanCollectionDataSource(v), params);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(SellReportController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SellReportController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        } catch (ParseException ex) {
            Logger.getLogger(SellReportController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                rs.close();
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(SellReportController.class.getName()).log(Level.SEVERE, null, ex);
                Msg.showError("");
            }
        }
    }
    
}
