package com.example.fyp.Controller;

import com.example.fyp.Model.Orders;
import com.example.fyp.Repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class OrdersController {
    @Autowired
    OrdersRepository OrdersRepo;

    @GetMapping("orders")
    public List<Orders> getAllOrders() {
        return OrdersRepo.findAll();
    }

    @PostMapping("createOrder")
    public Orders createOrder(@RequestBody Map<String, String> payload) {
        Integer count=0;
        List<Orders> list = getAllOrders();
        for(Orders countlist: list){
            count++;
        }
        String ordersId = "order"+count;
        return OrdersRepo.save(new Orders(ordersId,payload.get("ordersDescription"),payload.get("ordersDate"),payload.get("buyerId"),payload.get("productsId"),payload.get("productsQuantity"),payload.get("userIc"),payload.get("ordersStatus")));
    }

    @PostMapping("buyerHistoryList")
    public List<Orders> getBuyerHistoryList(@RequestBody Map<String, String>payload){
        List<Orders> orders = OrdersRepo.findAllByBuyerId(payload.get("buyerId"));
        return orders;
    }
}
