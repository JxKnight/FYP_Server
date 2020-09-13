package com.example.fyp.Model;

import javax.persistence.*;

public class CalculateOrders {

    String productID;
    String quantity;
    public CalculateOrders(String productID, String quantity) {
        this.productID = productID;
        this.quantity = quantity;
    }

    public CalculateOrders() {

    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
