package com.example.ui;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.*;

public class SQLConnection {
    static Connection con = null;
    public static Connection connectDb(){
        String url = "jdbc:sqlserver://DESKTOP-9TRAU8I:1433; databaseName=AutomativePartsWeb;" +
                        "encrypt=true;trustServerCertificate=true";
        String user = "sa";
        String password = "Nhu1234@";
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, user, password);
            return con;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
            return null;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
