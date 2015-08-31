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
import model.StockReport;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class StockReportController implements Initializable {
    @FXML
    private TableView<StockReport> table;
    @FXML
    private TableColumn<StockReport, String> category;
    @FXML
    private TableColumn<StockReport, String> model;
    @FXML
    private TableColumn<StockReport, String> serial;
    @FXML
    private TableColumn<StockReport, Float> p_price;
    @FXML
    private TableColumn<StockReport, Float> s_price;
    @FXML
    private TableColumn<StockReport, String> supplier;
    
    private List<StockReport> list;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list = FXCollections.observableArrayList();
        category.setCellValueFactory(new PropertyValueFactory("category"));
        model.setCellValueFactory(new PropertyValueFactory("model"));
        serial.setCellValueFactory(new PropertyValueFactory("serial"));
        supplier.setCellValueFactory(new PropertyValueFactory("supplier"));
        p_price.setCellValueFactory(new PropertyValueFactory("p_price"));
        s_price.setCellValueFactory(new PropertyValueFactory("s_price"));
        
        Database db = new Database();
        try {
            Connection c = db.getConnection();
            ResultSet rs = c.createStatement().executeQuery("select * from stock");
            while(rs.next()){
                String serial = rs.getString("sl");
                float p_price = rs.getFloat("p_price");
                float s_price = rs.getFloat("s_price");
                int model_id = rs.getInt("model");
                int supplier_id = rs.getInt("supplier");
                
                ResultSet rs2 = c.createStatement().executeQuery("select category_id,name from model where id=" + model_id);
                int category_id = rs2.getInt("category_id");
                String model = rs2.getString("name");
                rs2 = c.createStatement().executeQuery("select name from category where id=" + category_id);
                String category = rs2.getString("name");
                rs2 = c.createStatement().executeQuery("select name from supplier where id=" + supplier_id);
                String supplier = rs2.getString("name");
                
                list.add(new StockReport(category, model, serial, supplier, p_price, s_price));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(StockReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StockReportController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            this.table.getItems().addAll(list);
        }
        
        
        
    }    
    
}
