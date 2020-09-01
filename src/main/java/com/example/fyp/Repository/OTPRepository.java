package com.example.fyp.Repository;

import com.example.fyp.Model.Attendance;
import com.example.fyp.Model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Integer> {
    Optional<OTP> findAllByUserIcAndOtpNum(String userIc, String OtpNum);
}
