package com.example.fyp.Controller;

import com.example.fyp.Model.Attendance;
import com.example.fyp.Model.OTP;
import com.example.fyp.Repository.AttendanceRepository;
import com.example.fyp.Repository.OTPRepository;
import com.example.fyp.SpringExampleApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class AttendanceController {

    Calendar calendar;
    SimpleDateFormat dateFormat;
    String expired;
    @Autowired
    AttendanceRepository AttendanceRepo;
    @Autowired
    SpringExampleApp springExampleApp;
    @Autowired
    OTPRepository otpRepo;

    @GetMapping("Attendance")
    public List<Attendance> getAllAttendance() {
        return AttendanceRepo.findAll();
    }

    public List<OTP> getAllOTP() {
        return otpRepo.findAll();
    }

    @PostMapping("requestAttendance")
    public String requestAttendance(@RequestBody Map<String, String> payload) {

        String result = "";
        long userIc = Long.parseLong(payload.get("userIc"));
        Integer otp = Integer.parseInt(String.valueOf(otp()));
        long[] newOtp = new long[]{(otp % 10000) / 1000, (otp % 1000) / 100, (otp % 100) / 10, (otp % 10)};
        String[] data = new String[newOtp.length];
        for (int i = 0; i < newOtp.length; i++) {
            data[i] = String.valueOf(newOtp[i] + userIc);
            result = result + data[i] + "/";
        }

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd:MM:yyyy:HH:mm:ss");
        String day = dateFormat.format(calendar.getTime());
        String[] date = dateFormat.format(calendar.getTime()).split(":");
        Integer expiredSec = Integer.parseInt(date[5]) + 30;
        String eSec = "", eMin = "";
        Integer expiredMin;
        DecimalFormat formatter = new DecimalFormat("00");
        if (expiredSec > 59) {
            expiredSec = expiredSec - 60;
            expiredMin = Integer.parseInt(date[4]) + 1;
            eSec = formatter.format(expiredSec);
            if (expiredMin > 59) {
                expiredMin = expiredMin - 60;
                date[3] = date[3] + 1;
                eMin = formatter.format(expiredMin);
                expired = date[0] + ":" + date[1] + ":" + date[2] + ":" + date[3] + ":" + eMin + ":" + eSec;
            }else{
                eMin = formatter.format(expiredMin);
                expired = date[0] + ":" + date[1] + ":" + date[2] + ":" + date[3] + ":" + eMin + ":" + eSec;
            }
        }else{
            expired = date[0] + ":" + date[1] + ":" + date[2] + ":" + date[3] + ":" + date[4] + ":" + expiredSec;
        }
        Integer count = 0;
        List<OTP> list = getAllOTP();
        for (OTP countList : list) {
            count++;
        }
        String OtpId = "Otp" + count;
        otpRepo.save(new OTP(OtpId, day, expired, payload.get("userIc"), String.valueOf(otp())));
        expired="";
        return result;
        //springExampleApp.getQRCode(result);
    }

    @PostMapping("createAttendance")
    public void createAttendance(@RequestBody Map<String, String> payload) throws ParseException {
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd:MM:yyyy:HH:mm:ss");
        String dateTime = dateFormat.format(calendar.getTime());
        String[] date="02:09:2020:00:27:45".split(":");
        Date currentDate = new SimpleDateFormat("dd:MM:yyyy:HH:mm:ss").parse(dateTime);
        Optional<OTP> optional = otpRepo.findAllByUserIcAndOtpNum(payload.get("userIc"),payload.get("otpNum"));
        Date DateEnd = new SimpleDateFormat("dd:MM:yyyy:HH:mm:ss").parse(optional.get().getDateEnd());

        long diff = DateEnd.getTime()-currentDate.getTime();
        long x = 0l;
        if(diff<x){
           // status="false";
        }else if(diff>30000l){
           // status="not valid";
        }else{
            Integer count = 0;
            List<Attendance> list = getAllAttendance();
            for (Attendance countList : list) {
                count++;
            }
            String attendanceId = "Attendance" + count;
            AttendanceRepo.save(new Attendance(attendanceId, payload.get("userIc"), date[3]+":"+date[4]+":"+date[5], date[0]+":"+date[1]+":"+date[2]));
        }
    }

    public char[] otp() {
        String x = "0123456789";
        Random rndm_method = new Random();

        int length = 4;
        char[] otp = new char[length];

        for (int i = 0; i < length; i++) {
            otp[i] = x.charAt(rndm_method.nextInt(x.length()));
        }
        return otp;
    }
}
