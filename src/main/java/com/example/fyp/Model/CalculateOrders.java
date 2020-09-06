package com.example.fyp.Model;

import javax.persistence.*;

@Entity
@Table(name="CalculateOrders")
public class CalculateOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "COID",unique = true)
    String CO;
    @Column(name = "productID")
    String productID;
    @Column(name="productQuantity")
    String quantity;
    @Column(name="date")
    String date;
    public CalculateOrders(String productID, String quantity) {
        this.productID = productID;
        this.quantity = quantity;
    }

    public CalculateOrders(String CO, String productID, String quantity, String date) {
        this.CO = CO;
        this.productID = productID;
        this.quantity = quantity;
        this.date = date;
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
