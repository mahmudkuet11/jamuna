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
import model.CustomerReport;
import model.Msg;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class CustomerReportController implements Initializable {
    @FXML
    private TableColumn<CustomerReport, String> phone;
    @FXML
    private TableColumn<CustomerReport, String> address;
    @FXML
    private DatePicker start_date;
    @FXML
    private DatePicker end_date;
    @FXML
    private Button show_report;
    @FXML
    private Button export_report;
    @FXML
    private TableView<CustomerReport> report_table;
    @FXML
    private TableColumn<CustomerReport, String> sl;
    @FXML
    private TableColumn<CustomerReport, String> date;
    @FXML
    private TableColumn<CustomerReport, String> category;
    @FXML
    private TableColumn<CustomerReport, String> model;
    @FXML
    private TableColumn<CustomerReport, String> serial;
    @FXML
    private TableColumn<CustomerReport, String> amount;
    @FXML
    private TableColumn<CustomerReport, String> name;
    private ObservableList<CustomerReport> report_list;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        report_list = FXCollections.observableArrayList();
        sl.setCellValueFactory(new PropertyValueFactory<CustomerReport, String>("sl"));
        date.setCellValueFactory(new PropertyValueFactory<CustomerReport, String>("date"));
        category.setCellValueFactory(new PropertyValueFactory<CustomerReport, String>("category"));
        model.setCellValueFactory(new PropertyValueFactory<CustomerReport, String>("model"));
        serial.setCellValueFactory(new PropertyValueFactory<CustomerReport, String>("serial"));
        amount.setCellValueFactory(new PropertyValueFactory<CustomerReport, String>("amount"));
        name.setCellValueFactory(new PropertyValueFactory<CustomerReport, String>("name"));
        phone.setCellValueFactory(new PropertyValueFactory<CustomerReport, String>("phone"));
        address.setCellValueFactory(new PropertyValueFactory<CustomerReport, String>("address"));
        
        this.report_table.setItems(report_list);
        
    }   
    @FXML
    private void onShowReportButtonClick(ActionEvent event) {
        this.report_list.clear();
        
        String start_date = this.start_date.getValue().toString();
        String end_date = this.end_date.getValue().toString();
        
        //select sell.date,sell.customer_name,sell.phone,sell.address,sell.price,(select sl from all_products where stock_id=sell.stock_id) as sl,(select name from model where id=(select model from all_products where stock_id=sell.stock_id)) as model, (select name from category where id=(select category_id from model where id=(select model from all_products where stock_id=sell.stock_id))) as category from sell where date between '2015-08-04' and '2015-08-05'
        Database db = new Database();
        Connection c = null;
        ResultSet rs = null;
        
        try {
            c = db.getConnection();
            rs = c.createStatement().executeQuery("select sell.date,sell.customer_name,sell.phone,sell.address,sell.price,(select sl from all_products where stock_id=sell.stock_id) as sl,(select name from model where id=(select model from all_products where stock_id=sell.stock_id)) as model, (select name from category where id=(select category_id from model where id=(select model from all_products where stock_id=sell.stock_id))) as category from sell where date between '"+ start_date +"' and '"+ end_date +"'");
            int i=1;
            while(rs.next()){
                String date = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("date")));
                String name = rs.getString("customer_name");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String price = String.valueOf(rs.getFloat("price"));
                String sl = rs.getString("sl");
                String model = rs.getString("model");
                String category = rs.getString("category");
                
                report_list.add(new CustomerReport(String.valueOf(i), date, category, model, sl, price, name, phone, address));
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerReportController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CustomerReportController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        } catch (ParseException ex) {
            Logger.getLogger(CustomerReportController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        }finally{
            try {
                rs.close();
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerReportController.class.getName()).log(Level.SEVERE, null, ex);
                Msg.showError("");
            }
        }
    }

    @FXML
    private void onExportReportButtonClick(ActionEvent event) {
        String start_date = this.start_date.getValue().toString();
        String end_date = this.end_date.getValue().toString();
        
        //select sell.date,sell.customer_name,sell.phone,sell.address,sell.price,(select sl from all_products where stock_id=sell.stock_id) as sl,(select name from model where id=(select model from all_products where stock_id=sell.stock_id)) as model, (select name from category where id=(select category_id from model where id=(select model from all_products where stock_id=sell.stock_id))) as category from sell where date between '2015-08-04' and '2015-08-05'
        Database db = new Database();
        Connection c = null;
        ResultSet rs = null;
        
        try {
            c = db.getConnection();
            rs = c.createStatement().executeQuery("select sell.date,sell.customer_name,sell.phone,sell.address,sell.price,(select sl from all_products where stock_id=sell.stock_id) as sl,(select name from model where id=(select model from all_products where stock_id=sell.stock_id)) as model, (select name from category where id=(select category_id from model where id=(select model from all_products where stock_id=sell.stock_id))) as category from sell where date between '"+ start_date +"' and '"+ end_date +"'");
            int i=1;
            Vector v = new Vector();
            HashMap params = new HashMap();
            while(rs.next()){
                String date = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("date")));
                String name = rs.getString("customer_name");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String price = String.valueOf(rs.getFloat("price"));
                String sl = rs.getString("sl");
                String model = rs.getString("model");
                String category = rs.getString("category");
                
                v.add(new CustomerReport(String.valueOf(i), date, category, model, sl, price, name, phone, address));
                i++;
            }
            
            params.put("date", "From "+ new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(start_date)) +" to " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(end_date)));
            Report report = new Report();
            report.getReport("src\\report\\CustomerReport.jrxml", new JRBeanCollectionDataSource(v), params);
            
        } catch (SQLException ex) {
            Logger.getLogger(CustomerReportController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CustomerReportController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        } catch (ParseException ex) {
            Logger.getLogger(CustomerReportController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        }finally{
            try {
                rs.close();
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerReportController.class.getName()).log(Level.SEVERE, null, ex);
                Msg.showError("");
            }
        }
    }
    
}
