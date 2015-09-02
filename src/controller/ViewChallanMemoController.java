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
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Chalan;
import model.ChalanItem;
import model.Msg;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class ViewChallanMemoController implements Initializable {
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;
    @FXML
    private TableView<Chalan> table;
    @FXML
    private TableColumn<Chalan, String> date;
    @FXML
    private TableColumn<Chalan, String> company;
    private List<Chalan> list;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.list = FXCollections.observableArrayList();
        this.date.setCellValueFactory(new PropertyValueFactory("date"));
        this.company.setCellValueFactory(new PropertyValueFactory("company"));
        
    }    

    @FXML
    private void onShowButtonClick(ActionEvent event) {
        this.table.getItems().clear();
        this.list.clear();
        String start_date = this.start.getValue().toString();
        String end_date = this.end.getValue().toString();
        
        Database db = new Database();
        try {
            Connection c = db.getConnection();
            ResultSet rs = c.createStatement().executeQuery("select * from chalan where date between '"+ start_date +"' and '"+ end_date +"'");
            while(rs.next()){
                String date = rs.getString("date");
                String company = rs.getString("company");
                String items = rs.getString("items");
                this.list.add(new Chalan(date, company, items));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ViewChallanMemoController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ViewChallanMemoController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        } catch (Exception ex) {
            Logger.getLogger(ViewChallanMemoController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        } finally{
            this.table.getItems().addAll(this.list);
        }
    }

    @FXML
    private void onExportButtonClick(ActionEvent event) {
        String items = this.table.getSelectionModel().getSelectedItem().getItems();
        String date = this.table.getSelectionModel().getSelectedItem().getDate();
        String company = this.table.getSelectionModel().getSelectedItem().getCompany();
        JSONArray array = new JSONArray(items);
        System.out.println(array);
        Vector v = new Vector();
        for(int i=0; i<array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            String name = obj.getString("name");
            int qty = obj.getInt("qty");
            float rate = Float.parseFloat(obj.get("rate").toString());
            float total = Float.parseFloat(obj.get("total").toString());
            v.add(new ChalanItem(name, qty, rate, total));
        }
        HashMap params = new HashMap();
        params.put("date", date);
        params.put("company", company);
        Report report = new Report();
        report.getReport("src\\report\\ChalanMemo.jrxml", new JRBeanCollectionDataSource(v), params);
    }
    
}
