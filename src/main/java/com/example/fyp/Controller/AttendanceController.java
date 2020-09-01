package com.example.fyp.Controller;

import com.example.fyp.Model.Attendance;
import com.example.fyp.Model.Buyer;
import com.example.fyp.Model.Category;
import com.example.fyp.Model.OTP;
import com.example.fyp.Repository.AttendanceRepository;
import com.example.fyp.Repository.CategoryRepository;
import com.example.fyp.Repository.OTPRepository;
import com.example.fyp.SpringExampleApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class AttendanceController {

    Calendar calendar ;
    SimpleDateFormat dateFormat;

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

    @GetMapping("requestAttendance")
    public String requestAttendance(@RequestParam(value = "userIc", required = true) String text) {
        String result ="";
       long userIc = Long.parseLong(text);
        Integer otp = Integer.parseInt(String.valueOf(otp()));
        long[] newOtp = new long[]{(otp%10000)/1000,(otp%1000)/100,(otp%100)/10,(otp%10)};
        String[] data = new String[newOtp.length];
        for(int i=0;i< newOtp.length;i++){
           data[i]= String.valueOf(newOtp[i]+userIc);
           result = result+data[i]+"/";
        }
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd:MM:yyyy:HH:mm:ss");
        String day = dateFormat.format(calendar.getTime());
        String[] date = dateFormat.format(calendar.getTime()).split(":");
        Integer expiredTime = Integer.parseInt(date[5])+30;
        String etime="";
        String expiredDate="";
        DecimalFormat formatter = new DecimalFormat("00");
        if(expiredTime>=60){
            expiredTime = expiredTime -60;
            expiredDate = String.valueOf(Integer.parseInt(date[4])+1);
            etime = formatter.format(expiredTime);
        }
        String expired = date[0]+":"+date[1]+":"+date[2]+":"+date[3]+":"+expiredDate+":"+etime;
        Integer count=0;
        List<OTP> list = getAllOTP();
        for(OTP countList: list){
            count++;
        }
        String OtpId = "Otp"+count;
        otpRepo.save(new OTP(OtpId,day,expired,text,String.valueOf(otp())));
        return expired;
        //springExampleApp.getQRCode(result);
    }

    @PostMapping("createAttendance")
    public void createAttendance(@RequestBody Map<String, String> payload) {
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String[] date = dateFormat.format(calendar.getTime()).split(" ");
        Integer count=0;
        List<Attendance> list = getAllAttendance();
        for(Attendance countList: list){
            count++;
        }
        String attendanceId = "Attendance"+count;
        AttendanceRepo.save(new Attendance(attendanceId,payload.get("userIC"),date[0],date[1]));
    }

    public char[] otp() {
        String x = "0123456789";
        Random rndm_method = new Random();

        int length=4;
        char[] otp = new char[length];

        for(int i=0;i<length;i++){
            otp[i] = x.charAt(rndm_method.nextInt(x.length()));
        }
        return otp;
    }
}
