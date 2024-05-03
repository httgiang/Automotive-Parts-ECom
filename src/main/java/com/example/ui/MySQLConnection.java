package com.example.ui;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.*;

public class MySQLConnection {
    static Connection con = null;
    public static Connection connectDb(){
        String url = "jdbc:sqlserver://DESKTOP-F6P99QD;databaseName=ECommerceWebsite;encrypt=true;trustServerCertificate=true";
        String user = "sa";
        String password = "Giangdb2311";

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, user, password);
            JOptionPane.showMessageDialog(null, "Connection Established");
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
