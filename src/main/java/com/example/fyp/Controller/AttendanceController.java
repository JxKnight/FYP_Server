package com.example.fyp.Controller;

import com.example.fyp.Model.Attendance;
import com.example.fyp.Model.OTP;
import com.example.fyp.Model.User;
import com.example.fyp.Repository.AttendanceRepository;
import com.example.fyp.Repository.OTPRepository;
import com.example.fyp.Repository.UserRepository;
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
    @Autowired
    UserRepository userRepo;

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
        //return String.valueOf(otp());
        //springExampleApp.getQRCode(result);
    }

    @PostMapping("createAttendance")
    public void createAttendance(@RequestBody Map<String, String> payload) throws ParseException {
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd:MM:yyyy:HH:mm:ss");
        String dateTime = dateFormat.format(calendar.getTime());
        String[] date =dateTime.split(":");
        Date currentDate = new SimpleDateFormat("dd:MM:yyyy:HH:mm:ss").parse(dateTime);
        Optional<OTP> optional = otpRepo.findAllByUserIcAndOtpNum(payload.get("userIc"),payload.get("otpNum"));
        Date DateEnd = new SimpleDateFormat("dd:MM:yyyy:HH:mm:ss").parse(optional.get().getDateEnd());
        Optional<User> optionalUser = userRepo.findByUserIc(payload.get("userIc"));


        long diff = DateEnd.getTime()-currentDate.getTime();
        long x = 0l;
        if(diff<x){
           // status="false";
        }else if(diff>30000l){
           // status="not valid";
        }else{
            String day="";
            Integer count = 0;
            List<Attendance> list = getAllAttendance();
            for (Attendance countList : list) {
                count++;
            }
            if(Integer.parseInt(date[3])<8){
                day="midnight";
            }else if(Integer.parseInt(date[3])<13&&Integer.parseInt(date[3])>=8){
                day="morning";
            }else if(Integer.parseInt(date[3])>13&&Integer.parseInt(date[3])<19){
                day="evening";
            }else if(Integer.parseInt(date[3])>18){
                day="ot";
            }
            String attendanceId = "Attendance" + count;
            AttendanceRepo.save(new Attendance(attendanceId,optionalUser.get().getFirstName(), payload.get("userIc"), date[3]+":"+date[4]+":"+date[5], date[0]+":"+date[1]+":"+date[2],day));

        }
    }

    @GetMapping("getCurrentDayAttendance")
    public List<Attendance> currentAttendance(@RequestParam String day){
        List<Attendance> newestList = new ArrayList<>();
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd:MM:yyyy");
        String dateTime = dateFormat.format(calendar.getTime());
        List<Attendance> list = AttendanceRepo.findAllByDate(dateTime);
        for(Attendance var : list){
            if(var.getDay().equals(day)){
                newestList.add(var);
            }else if(var.getDay().equals(day)){
                newestList.add(var);
            }else if(var.getDay().equals(day)){
                newestList.add(var);
            }
        }
        return newestList;
    }

    public char[] otp() {
        String x = "0123456789";
        Random rndm_method = new Random();

        int length = 4;
        char[] otp = new char[length];

        do{
            for (int i = 0; i < length; i++) {
                otp[i] = x.charAt(rndm_method.nextInt(x.length()));
            }
        }while(checkOTP(String.valueOf(otp)).equals("false"));

        return otp;
    }

    public String checkOTP(String otp){
        String x ="";
        List<OTP> list = getAllOTP();
        for (OTP List : list) {
            if(List.getOtpNum().equals(otp)){
                x="false";
            }else{
                x="true";
            }
        }
        return x;
    }

    @PostMapping("calculateSalary")
    public List<Attendance> calculateSalary(@RequestBody Map<String, String> payload) {
        List<Attendance> list = AttendanceRepo.findAllByDateAndUserIc(payload.get("date"),payload.get("userIc"));
        //ArrayList<Attendance>
        return list;
    }
}
