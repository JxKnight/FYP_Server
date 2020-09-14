package com.example.fyp.Controller;

import com.example.fyp.Model.*;
import com.example.fyp.Repository.*;
import com.example.fyp.SpringExampleApp;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
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
    @Autowired
    QRCodeRepository qrCodeRepo;
    @Autowired
    RoleRepository RoleRepo;

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
        qrCodeRepo.save(new QRCode(result));
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
            } else {
                eMin = formatter.format(expiredMin);
                expired = date[0] + ":" + date[1] + ":" + date[2] + ":" + date[3] + ":" + eMin + ":" + eSec;
            }
        } else {
            expired = date[0] + ":" + date[1] + ":" + date[2] + ":" + date[3] + ":" + date[4] + ":" + expiredSec;
        }
        Integer count = 0;
        List<OTP> list = getAllOTP();
        for (OTP countList : list) {
            count++;
        }
        String OtpId = "Otp" + count;
        otpRepo.save(new OTP(OtpId, day, expired, payload.get("userIc"), String.valueOf(otp())));
        //qrCodeRepo.save(new QRCode(result));
        expired = "";
        return result;
        //return String.valueOf(otp());
        //springExampleApp.getQRCode(result);

    }

    @PostMapping("createAttendance")
    public void createAttendance(@RequestBody Map<String, String> payload) throws ParseException {
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd:MM:yyyy:HH:mm:ss");
        String dateTime = dateFormat.format(calendar.getTime());
        String[] date = dateTime.split(":");
        Date currentDate = new SimpleDateFormat("dd:MM:yyyy:HH:mm:ss").parse(dateTime);
        Optional<OTP> optional = otpRepo.findAllByUserIcAndOtpNum(payload.get("userIc"), payload.get("otpNum"));
        Date DateEnd = new SimpleDateFormat("dd:MM:yyyy:HH:mm:ss").parse(optional.get().getDateEnd());
        Optional<User> optionalUser = userRepo.findByUserIc(payload.get("userIc"));


        long diff = DateEnd.getTime() - currentDate.getTime();
        long x = 0l;
        if (diff < x) {
            // status="false";
        } else if (diff > 30000l) {
            // status="not valid";
        } else {
            String day = "";
            Integer count = 0;
            List<Attendance> list = getAllAttendance();
            for (Attendance countList : list) {
                count++;
            }
            if (Integer.parseInt(date[3]) < 8) {
                day = "midnight";
            } else if (Integer.parseInt(date[3]) <= 13 && Integer.parseInt(date[3]) >= 8) {
                day = "morning";
            } else if (Integer.parseInt(date[3]) >= 14 && Integer.parseInt(date[3]) < 19) {
                day = "evening";
            } else if (Integer.parseInt(date[3]) >= 19) {
                day = "ot";
            }
            String attendanceId = "Attendance" + count;
            AttendanceRepo.save(new Attendance(attendanceId, optionalUser.get().getFirstName(), payload.get("userIc"), date[3] + ":" + date[4] + ":" + date[5], date[0] + ":" + date[1] + ":" + date[2], day));
        }
    }

    @GetMapping("getCurrentDayAttendance")
    public List<Attendance> currentAttendance(@RequestParam String day) {
        List<Attendance> newestList = new ArrayList<>();
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd:MM:yyyy");
        String dateTime = dateFormat.format(calendar.getTime());
        List<Attendance> list = AttendanceRepo.findAllByDate(dateTime);
        for (Attendance var : list) {
            if (var.getDay().equals(day)) {
                newestList.add(var);
            } else if (var.getDay().equals(day)) {
                newestList.add(var);
            } else if (var.getDay().equals(day)) {
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

        do {
            for (int i = 0; i < length; i++) {
                otp[i] = x.charAt(rndm_method.nextInt(x.length()));
            }
        } while (checkOTP(String.valueOf(otp)).equals("false"));

        return otp;
    }

    public String checkOTP(String otp) {
        String x = "";
        List<OTP> list = getAllOTP();
        for (OTP List : list) {
            if (List.getOtpNum().equals(otp)) {
                x = "false";
            } else {
                x = "true";
            }
        }
        return x;
    }

    @GetMapping("monthlySalary")
    public Salary monthlySalary(@RequestParam(name = "month") String selectMonth, @RequestParam(name = "userIc") String userIc) throws ParseException {
        Salary back = null;
        String[] x = new String[0];
        List<String> dayList;
        List<String> unList = new ArrayList<>();
        Optional<User> user = userRepo.findByUserIc(userIc);
        Optional<Role> optional = RoleRepo.findByRoleNum(user.get().getUserRole());
        List<Attendance> allList = AttendanceRepo.findAllByUserIc(userIc);
        if (optional.get().getRoleRate().equals("0")) {
            System.out.println("Admin");
        } else {
            if (allList.size() > 0) {
                for (Attendance var : allList) {
                    int i = 0;
                    x = var.getDate().split(":");
                    if (x[1].equals(selectMonth)) {
                        unList.add(var.getDate());
                    }
                }
                Integer result = 0;
                LinkedHashSet<String> hashSet = new LinkedHashSet<>(unList);
                dayList = new ArrayList<>(hashSet);
                for (String var : dayList) {
                    result = result + dailySalary(var, userIc, optional.get().getRoleRate());
                }
                Integer finalHours = result / Integer.parseInt(optional.get().getRoleRate());
                // System.out.println("!EMPTY");
                back = new Salary(userIc,selectMonth,x[2], String.valueOf(result), String.valueOf(finalHours));
            }
        }
        return back;
    }

    public Integer dailySalary(String date, String userIc, String roleNum) throws ParseException {
        List<Attendance> dailyList = AttendanceRepo.findAllByDateAndUserIc(date, userIc);
        //calculation(dailyList.get(0).getTime(), dailyList.get(1).getTime(), Integer.parseInt(roleNum));
        Integer result = 0;
        if (dailyList.size() < 5 && dailyList.size() > 2) {
            result = result + calculation(dailyList.get(0).getTime(), dailyList.get(1).getTime(), Integer.parseInt(roleNum));
            result = result + calculation(dailyList.get(2).getTime(), dailyList.get(3).getTime(), Integer.parseInt(roleNum));
        } else if (dailyList.size() < 3) {
            result = result + calculation(dailyList.get(0).getTime(), dailyList.get(1).getTime(), Integer.parseInt(roleNum));
        } else if (dailyList.size() > 5) {
            result = result + calculation(dailyList.get(0).getTime(), dailyList.get(1).getTime(), Integer.parseInt(roleNum));
            result = result + calculation(dailyList.get(2).getTime(), dailyList.get(3).getTime(), Integer.parseInt(roleNum));
            result = result + calculation(dailyList.get(4).getTime(), dailyList.get(5).getTime(), Integer.parseInt(roleNum));
        }
        return result;
    }

    public Integer calculation(String x, String y, Integer rate) throws ParseException {
        Date dayMorningOut = new SimpleDateFormat("HH:mm:ss").parse(y);
        Date dayMorningIn = new SimpleDateFormat("HH:mm:ss").parse(x);
        long dailyEarnMs = dayMorningOut.getTime() - dayMorningIn.getTime();
        int dailyEarnHour = (int) (((dailyEarnMs / 1000) / 60) / 60);
        return dailyEarnHour * rate;
    }

    @GetMapping("getQRCode")
    public ResponseEntity getQRCode() {
        String x = "";
        System.out.println("Request in ");
        return ResponseEntity.ok(qrCodeRepo.findAll().get(0));
    }


    @GetMapping("MonthlyAttendance")
    public List<Attendance> MonthlyAttendance(@RequestParam(name = "month") String selectMonth, @RequestParam(name = "userIc") String userIc) throws ParseException {
        String[] x = new String[0];
        List<Attendance> dayList;
        List<Attendance> unList = new ArrayList<>();
        Optional<User> user = userRepo.findByUserIc(userIc);
        List<Attendance> allList = AttendanceRepo.findAllByUserIc(userIc);
        for (Attendance var : allList) {
            int i = 0;
            x = var.getDate().split(":");
            if (x[1].equals(selectMonth)) {
                unList.add(var);
            }
        }
//        LinkedHashSet<Attendance> hashSet = new LinkedHashSet<>(unList);
//        dayList = new ArrayList<>(hashSet);
        return unList;
    }
}
