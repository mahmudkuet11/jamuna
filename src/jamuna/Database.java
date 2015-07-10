/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jamuna;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author mohar
 */
public class Database {
    Connection c = null;
    Statement stmt;

    public Connection getConnection() throws SQLException, ClassNotFoundException{
        Connection con = null;
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:jamuna.sqlite");
        return con;    
        
    }
}
