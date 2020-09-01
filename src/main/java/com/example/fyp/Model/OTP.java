package com.example.fyp.Model;

import javax.persistence.*;

@Entity
@Table(name="OTP")
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "Otp_ID", unique = true)
    private String otpID;
    @Column(name = "Date_Start")
    private String dateStart;
    @Column(name = "Date_End")
    private String dateEnd;
    @Column(name = "user_Ic")
    private String userIc;
    @Column(name = "Otp_num")
    private String otpNum;



    public OTP() {

    }

    public OTP(String otpID, String dateStart, String dateEnd, String userIc,String otpNum) {
        this.otpID = otpID;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.userIc = userIc;
        this.otpNum = otpNum;
    }

    public String getOtpID() {
        return otpID;
    }

    public void setOtpID(String otpID) {
        this.otpID = otpID;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getUserIc() {
        return userIc;
    }

    public void setUserIc(String userIc) {
        this.userIc = userIc;
    }

    public String getOtpNum() {
        return otpNum;
    }

    public void setOtpNum(String otpNum) {
        this.otpNum = otpNum;
    }
}
