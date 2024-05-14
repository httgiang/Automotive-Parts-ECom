package com.example.ui.Entity;

public class OrderDetails {
    private int pID;
    private String pName;
    private int quantity;
    private float amount;

    public OrderDetails(int pID, String pName, int quantity, float price) {
        this.pID = pID;
        this.pName = pName;
        this.quantity = quantity;
        this.amount = price * quantity;
    }


    public int getPID() {
        return pID;
    }

    public void setPID(int pID) {
        this.pID = pID;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
