package com.example.fyp.Model;

import javax.persistence.*;

@Entity
@Table(name="Attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "Attendance_ID", unique = true)
    private String AttendanceID;
    @Column(name = "user_Ic")
    private String userIc;
    @Column(name = "time")
    private String time;
    @Column(name = "date")
    private String date;


    public Attendance() {

    }

    public Attendance(String attendanceID, String userIc, String time, String date) {
        AttendanceID = attendanceID;
        this.userIc = userIc;
        this.time = time;
        this.date = date;
    }

    public String getAttendanceID() {
        return AttendanceID;
    }

    public void setAttendanceID(String attendanceID) {
        AttendanceID = attendanceID;
    }

    public String getUserIc() {
        return userIc;
    }

    public void setUserIc(String userIc) {
        this.userIc = userIc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
