package com.example.ui.Entity;


public class User {
    private String name;
    private String email;
    private int mobile;
    private String address;
    private int pincode;
    private String password;
    private String type;
    private static User instance;

    // Singleton pattern to ensure only one instance of UserController exists
    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }
    private User() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public int getPincode() {
        return pincode;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
