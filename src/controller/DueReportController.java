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
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DueReport;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class DueReportController implements Initializable {
    @FXML
    private TableView<DueReport> table;
    @FXML
    private TableColumn<DueReport, String> sl;
    @FXML
    private TableColumn<DueReport, String> category;
    @FXML
    private TableColumn<DueReport, String> model;
    @FXML
    private TableColumn<DueReport, String> name;
    @FXML
    private TableColumn<DueReport, String> phone;
    @FXML
    private TableColumn<DueReport, String> address;
    @FXML
    private TableColumn<DueReport, Float> price;
    @FXML
    private TableColumn<DueReport, Float> paid;
    @FXML
    private TableColumn<DueReport, Float> due;
    private List<DueReport> list;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.list = FXCollections.observableArrayList();
        
        sl.setCellValueFactory(new PropertyValueFactory("serial"));
        category.setCellValueFactory(new PropertyValueFactory("category"));
        model.setCellValueFactory(new PropertyValueFactory("model"));
        name.setCellValueFactory(new PropertyValueFactory("name"));
        phone.setCellValueFactory(new PropertyValueFactory("phone"));
        address.setCellValueFactory(new PropertyValueFactory("address"));
        price.setCellValueFactory(new PropertyValueFactory("price"));
        paid.setCellValueFactory(new PropertyValueFactory("paid"));
        due.setCellValueFactory(new PropertyValueFactory("due"));
        
        
        Database db = new Database();
        try {
            Connection c = db.getConnection();
            ResultSet rs = c.createStatement().executeQuery("select * from hire");
            while(rs.next()){
                int stock_id = rs.getInt("stock_id");
                int hire_id = rs.getInt("id");
                int customer_id = rs.getInt("customer_id");
                float price = rs.getFloat("hire_price");
                float down_payment = rs.getInt("down_payment");
                ResultSet rs2 = c.createStatement().executeQuery("select * from all_products where stock_id=" + stock_id);
                String sl = rs2.getString("sl");
                int model_id = rs2.getInt("model");
                ResultSet rs3 = c.createStatement().executeQuery("select * from model where id=" + model_id);
                String model = rs3.getString("name");
                int category_id = rs3.getInt("category_id");
                ResultSet rs4 = c.createStatement().executeQuery("select * from category where id=" + category_id);
                String category = rs4.getString("name");
                ResultSet rs5 = c.createStatement().executeQuery("select * from customer where id=" + customer_id);
                String name = rs5.getString("name");
                String phone = rs5.getString("phone");
                String address = rs5.getString("address");
                ResultSet rs6 = c.createStatement().executeQuery("select sum(amount) as amount from hire_payments where hire_id=" + hire_id);
                float paid = rs6.getFloat("amount");
                float total = down_payment + paid;
                float due = price - total;
                if(due > 0)
                    this.list.add(new DueReport(sl, category, model, name, phone, address, price, total, due));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DueReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DueReportController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            this.table.getItems().addAll(list);
        }
        
        
    }    
    
}
