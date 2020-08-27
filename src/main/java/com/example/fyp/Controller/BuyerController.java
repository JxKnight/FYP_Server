package com.example.fyp.Controller;

import com.example.fyp.Model.Buyer;
import com.example.fyp.Model.Role;
import com.example.fyp.Repository.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class BuyerController {
    @Autowired
    BuyerRepository BuyerRepo;

    @GetMapping("buyers")
    public List<Buyer> getAllBuyers() {
        return BuyerRepo.findAll();
    }

    @PostMapping("createBuyer")
    public void createBuyer(@RequestBody Map<String, String> payload) {
        Integer count=0;
        List<Buyer> list = getAllBuyers();
        for(Buyer countlist: list){
            count++;
        }
        String adminCheck="false";
        String buyerId = "buyer"+count;
        BuyerRepo.save(new Buyer(buyerId,payload.get("buyerName"),payload.get("buyerContact"),payload.get("buyerLocation"),payload.get("buyerAddress"),payload.get("userIc"),adminCheck));
    }
    @PostMapping("getBuyerByLocation")
    public List<Buyer> getBuyerByLocation(@RequestBody Map<String, String> payload){
        List<Buyer> buyer = BuyerRepo.findByBuyerLocation(payload.get("buyerLocation"));
        return buyer;
    }

    @PostMapping("buyerAdminCheck")
    public Buyer adminCheck(@RequestBody Map<String, String> payload){
        return BuyerRepo.findByBuyerId(payload.get("buyerId")).map(buyer -> {
            buyer.setAdminCheck(payload.get("adminCheck"));
            BuyerRepo.save(buyer);
            return buyer;
        }).orElseThrow(() -> new NullPointerException("Unable to update empty record"));
    }

    @PostMapping("getBuyerDetails")
    public Buyer getBuyerDetails(@RequestParam String buyerID){
        Optional<Buyer> buyer = BuyerRepo.findByBuyerId(buyerID);
        return buyer.orElseThrow(() -> new NullPointerException("No Record Found"));
    }

    @PostMapping("deleteBuyer")
    public Buyer deleteRole(@RequestBody Map<String, String> payload) {
        return BuyerRepo.findByBuyerId(payload.get("buyerId")).map(role -> {
            BuyerRepo.delete(role);
            return role;
        }).orElseThrow(() -> new NullPointerException("Unable to update empty record"));
    }
}
