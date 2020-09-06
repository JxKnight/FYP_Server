package com.example.fyp.Model;

import javax.persistence.*;

@Entity
@Table(name="QRCode")
public class QRCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "qrCodeString")
    private String qrCodeString;

    public QRCode(){

    }

    public QRCode(String qrCodeString) {
        this.qrCodeString = qrCodeString;
    }

    public String getQrCodeString() {
        return qrCodeString;
    }

    public void setQrCodeString(String qrCodeString) {
        this.qrCodeString = qrCodeString;
    }
}
