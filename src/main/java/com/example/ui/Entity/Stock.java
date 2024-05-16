package com.example.ui.Entity;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class Stock {
    private String pID;
    private String pName;
    private Integer quantity;
    private String type;
    private Float price;
    private Button removeButton;

    private CheckBox action;

    public String getPID() {
        return pID;
    }

    public String getPName() {
        return pName;
    }

    public String getType() {
        return type;
    }

    public Float getPrice() {
        return price;
    }


    public CheckBox getSelect() {
        return action;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public Stock(String pID, String pName, int quantity, float price, String type) {
        this.pID = pID;
        this.pName = pName;
        this.quantity = quantity; //num of products of the same type
        this.price = price;
        this.type = type;
        this.removeButton = new Button("remove");
        this.action = new CheckBox();
    }


}
