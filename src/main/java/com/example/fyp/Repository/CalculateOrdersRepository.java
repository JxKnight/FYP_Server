package com.example.fyp.Repository;

import com.example.fyp.Model.Attendance;
import com.example.fyp.Model.CalculateOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalculateOrdersRepository extends JpaRepository<CalculateOrders, Integer> {

}
