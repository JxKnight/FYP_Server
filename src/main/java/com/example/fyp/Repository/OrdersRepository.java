package com.example.fyp.Repository;

import com.example.fyp.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    Optional<Orders> findByOrdersId(String ordersId);
    List<Orders> findAllByBuyerId(String buyerId);
    List<Orders> findAllByOrdersStatus(String orderStatus);
}
