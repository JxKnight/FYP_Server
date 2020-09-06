package com.example.fyp.Model;

import javax.persistence.*;

public class Salary {


    private String userIc;
    private String month;
    private String year;
    private String amount;
    private String totalHours;

    public Salary(String userIc, String month, String year, String amount, String totalHours) {
        this.userIc = userIc;
        this.month = month;
        this.year = year;
        this.amount = amount;
        this.totalHours = totalHours;
    }

    public Salary(){
    }

    public String getUserIc() {
        return userIc;
    }

    public void setUserIc(String userIc) {
        this.userIc = userIc;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }
}
