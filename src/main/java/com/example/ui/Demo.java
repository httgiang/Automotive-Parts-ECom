package com.example.ui;
import javax.swing.*;
import java.security.Security;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Demo {
    public static void main(String[] args) {
        Connection conn = null;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://DESKTOP-F6P99QD:1433;databaseName=ECommerceWebsite";
            String user = "sa";
            String password = "Giangdb2311";
            conn = DriverManager.getConnection(url, user, password);
            JOptionPane.showMessageDialog(null, "Connection Established");
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }  finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}