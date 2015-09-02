/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import jamuna.Database;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Msg;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class ChallanMemoController implements Initializable {
    @FXML
    private Button add_new;
    @FXML
    private DatePicker date;
    @FXML
    private TextField company;
    @FXML
    private VBox container;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addNewRow();
    }    

    @FXML
    private void onAddNewButtonClick(ActionEvent event) {
        addNewRow();
    }

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        String date = this.date.getValue().toString();
        String company = this.company.getText();
        
        JSONArray array = new JSONArray();
        for(int i=0; i<this.container.getChildren().size(); i++){
            JSONObject obj = new JSONObject();
            HBox row = (HBox) this.container.getChildren().get(i);
            TextField Tname = (TextField) row.getChildren().get(0);
            TextField Tqty = (TextField) row.getChildren().get(1);
            TextField Trate = (TextField) row.getChildren().get(2);
            TextField Ttotal = (TextField) row.getChildren().get(3);
            obj.put("name", Tname.getText());
            obj.put("qty", Tqty.getText());
            obj.put("rate", Trate.getText());
            obj.put("total", Ttotal.getText());
            array.put(obj);
        }
        Database db = new Database();
        try {
            Connection c = db.getConnection();
            c.createStatement().execute("insert into chalan (date,company,items) values ('"+ date +"','"+ company +"','"+ array.toString() +"')");
            Msg.showInformation("success");
            this.container.getChildren().clear();
        } catch (SQLException ex) {
            Logger.getLogger(ChallanMemoController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChallanMemoController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        } catch (Exception ex) {
            Logger.getLogger(ChallanMemoController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        }
        
    }
    
    private void addNewRow(){
        HBox row = new HBox();
        row.setSpacing(5);
        TextField name = new TextField();
        TextField quantity = new TextField();
        TextField rate = new TextField();
        TextField total = new TextField();
        Button delete = new Button("Remove");
        row.getChildren().addAll(name, quantity, rate, total, delete);
        this.container.getChildren().add(row);
        delete.setOnAction((e)->{
            this.container.getChildren().remove(row);
        });
        rate.setOnKeyReleased((e)->{
            int qty = quantity.getText().isEmpty() ? 0 : Integer.parseInt(quantity.getText());
            float rate2 = rate.getText().isEmpty() ? 0 : Float.parseFloat(rate.getText());
            total.setText(String.valueOf(qty * rate2));
        });
        quantity.setOnKeyReleased((e)->{
            int qty = quantity.getText().isEmpty() ? 0 : Integer.parseInt(quantity.getText());
            float rate2 = rate.getText().isEmpty() ? 0 : Float.parseFloat(rate.getText());
            total.setText(String.valueOf(qty * rate2));
        });
    }
    
}
