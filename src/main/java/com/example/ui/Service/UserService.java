package com.example.ui.Service;

import com.example.ui.Entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserService {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public UserService() {
    }

    public User queryUserInfo(String userEmail){
        User user = null;
        String sql = "SELECT * FROM USERS WHERE email = ?";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, userEmail);
            rs = pst.executeQuery();
            if(rs.next()){
                String email = userEmail;
                String name = rs.getString("name");
                int mobile = rs.getInt("mobile");
                String address = rs.getString("name");
                int pincode = rs.getInt("pincode");
                String password = rs.getString("password");
               // user = new User(email, name, mobile, address, pincode, password);
                return user;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
