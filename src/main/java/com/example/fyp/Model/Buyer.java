package com.example.fyp.Model;

import javax.persistence.*;

@Entity
@Table(name="buyer")
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "buyer_id",unique = true)
    private String buyerId;
    @Column(name = "buyer_name",unique = true)
    private String buyerName;
    @Column(name = "buyer_contact")
    private String buyerContact;
    @Column(name = "buyer_Location")
    private String buyerLocation;
    @Column(name = "buyer_address")
    private String buyerAddress;
    @Column(name = "buyer_rate")
    private String buyerRate;
    @Column(name = "user_ic")
    private String userIc;
    @Column(name = "admin_check")
    private String adminCheck;

    public Buyer() {
    }

    public Buyer(String buyerId, String buyerName, String buyerContact, String buyerLocation, String buyerAddress, String buyerRate, String userIc, String adminCheck) {
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.buyerContact = buyerContact;
        this.buyerLocation = buyerLocation;
        this.buyerAddress = buyerAddress;
        this.buyerRate = buyerRate;
        this.userIc = userIc;
        this.adminCheck = adminCheck;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerContact() {
        return buyerContact;
    }

    public void setBuyerContact(String buyerContact) {
        this.buyerContact = buyerContact;
    }


    public String getBuyerRate() {
        return buyerRate;
    }

    public void setBuyerRate(String buyerRate) {
        this.buyerRate = buyerRate;
    }

    public String getBuyerLocation() {
        return buyerLocation;
    }

    public void setBuyerLocation(String buyerLocation) {
        this.buyerLocation = buyerLocation;
    }

    public String getUserIc() {
        return userIc;
    }

    public void setUserIc(String userIc) {
        this.userIc = userIc;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getAdminCheck() {
        return adminCheck;
    }

    public void setAdminCheck(String adminCheck) {
        this.adminCheck = adminCheck;
    }
}
