package com.example.ui.Entity;

public class OrderCustomer {
    private String Product;
    private String OrderId;
    private int quantity;
    private String amount;
    private String status;
    public OrderCustomer(String Product, String OrderId, int quantity, String amount, String status){
        this.Product = Product;
        this.OrderId = OrderId;
        this.quantity = quantity;
        this.amount = amount;
        this.status = status;
    }

}
