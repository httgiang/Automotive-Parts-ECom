package com.example.ui.Entity;

import javafx.scene.control.Button;

public class OrderPurchaser {
    private int oID;
    private String sellerName;
    private int quantity;
    private float amount;

    public OrderPurchaser(int oID, String sellerName, int quantity, float amount) {
        this.oID = oID;
        this.sellerName = sellerName;
        this.quantity = quantity;
        this.amount = amount;
    }

    public int getOID() {
        return oID;
    }

    public void setOID(int oID) {
        this.oID = oID;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
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

