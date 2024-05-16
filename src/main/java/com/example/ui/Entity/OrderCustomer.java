package com.example.ui.Entity;

public class OrderCustomer {
    private int ProductID;
    private String Product;
    private int OrderId;
    private int quantity;
    private float amount;
    private String status;

    public int getProductID() {
        return ProductID;
    }

    public String getProduct() {
        return Product;
    }

    public int getOrderId() {
        return OrderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public OrderCustomer(int ProductID, String Product, int OrderId, int quantity, float price, int status){
        this.ProductID = ProductID;
        this.Product = Product;
        this.OrderId = OrderId;
        this.quantity = quantity;
        this.amount = price * quantity;
        if (status ==0){
            this.status = "UNSHIPPED";
        }
        else {
            this.status = "SHIPPED";
        }
    }

}
