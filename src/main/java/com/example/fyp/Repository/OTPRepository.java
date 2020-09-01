package com.example.fyp.Repository;

import com.example.fyp.Model.Attendance;
import com.example.fyp.Model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Integer> {
}
