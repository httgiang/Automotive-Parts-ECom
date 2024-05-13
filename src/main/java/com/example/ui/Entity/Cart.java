package com.example.ui.Entity;


import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;

public class Cart {
    private int pID;
    private String pName;
    private Integer quantity;
    private Float amount;
    private Button removeButton;

    private CheckBox select;

    public Cart(int pID, String pName, int quantity, float price) {
        this.pID = pID;
        this.pName = pName;
        this.quantity = quantity; //num of products of the same type
        this.amount = price * quantity;
        this.removeButton = new Button("remove");
        this.select = new CheckBox();
    }

    public int getPID() {
        return pID;
    }

    public String getPName() {
        return pName;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getAmount() {
        return amount;
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setPID(int pID) {
        this.pID = pID;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setRemoveButton(Button removeButton) {
        this.removeButton = removeButton;
    }


    public void setSelect(CheckBox selectCheck) {
        this.select = selectCheck;
    }

}
