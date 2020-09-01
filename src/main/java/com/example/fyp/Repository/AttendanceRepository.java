package com.example.fyp.Repository;

import com.example.fyp.Model.Attendance;
import com.example.fyp.Model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
}
