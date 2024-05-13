package com.example.ui.Entity;

import javafx.scene.control.Button;

public class OrderSeller {
    private int oID;
    private String purName;
    private String purAddress;
    private float money;
    private Button viewBtn;
    private Button shipBtn;

    public OrderSeller(int oID, String purName, String purAddress, float money) {
        this.oID = oID;
        this.purName = purName;
        this.purAddress = purAddress;
        this.money = money;
    }

    public int getOID() {
        return oID;
    }

    public void setOID(int oID) {
        this.oID = oID;
    }

    public String getPurName() {
        return purName;
    }

    public void setPurName(String purName) {
        this.purName = purName;
    }

    public String getPurAddress() {
        return purAddress;
    }

    public void setPurAddress(String purAddress) {
        this.purAddress = purAddress;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public Button getViewBtn() {
        return viewBtn;
    }

    public void setViewBtn(Button viewBtn) {
        this.viewBtn = viewBtn;
    }

    public Button getShipBtn() {
        return shipBtn;
    }

    public void setShipBtn(Button shipBtn) {
        this.shipBtn = shipBtn;
    }
}
