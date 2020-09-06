package com.example.fyp.Repository;

import com.example.fyp.Model.Attendance;
import com.example.fyp.Model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findAllByDate(String date);
    List<Attendance> findAllByUserIc(String userIc);
    List<Attendance> findAllByDateAndUserIc(String date,String userIc);
}
