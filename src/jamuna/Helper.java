/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jamuna;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author mohar
 */
public class Helper {
    public static int getNextSerial(){
        int sl=0;
        try {
            Database db = new Database();
            Connection c = db.getConnection();
            ResultSet rs = c.createStatement().executeQuery("select id from sell order by id desc limit 1");
            sl = rs.getInt("id");
            rs.close();
            c.close();
        } catch (Exception e) {
        }
        return sl;
    }
}
