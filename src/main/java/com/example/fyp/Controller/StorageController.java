package com.example.fyp.Controller;

import com.example.fyp.Model.Product;
import com.example.fyp.Model.Storage;
import com.example.fyp.Model.User;
import com.example.fyp.Repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StorageController {
    @Autowired
    StorageRepository StorageRepo;

    @GetMapping("warehouse")
    public List<Storage> getAllStorage() {
        return StorageRepo.findAll();
    }

//    @PostMapping("createStorage")
//    public void createStorage(@RequestBody Map<String, String> payload) {
//        StorageRepo.save(new Storage(payload.get("productsCategory"),payload.get("productsId"),payload.get("productsName"),payload.get("productsQuantity")));
//    }

    @PostMapping("warehouseFilter")
    public List<Storage> getAllStorageByFilter(@RequestBody Map<String, String> payload){
        return StorageRepo.findAllByProductsCategory(payload.get("productsCategory"));
    }
    @PostMapping("updateWarehouse")
    public Storage updateStorage(@RequestBody Map<String, String> payload) {
        return StorageRepo.findByProductsId(payload.get("productsId")).map(product -> {
            product.setProductsQuantity(payload.get("productsQuantity"));
            product.setUserUpdate(payload.get("userUpdate"));
            StorageRepo.save(product);
            return product;
        }).orElseThrow(() -> new NullPointerException("Unable to update empty record"));
    }

}
