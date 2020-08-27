package com.example.fyp.Controller;

import com.example.fyp.Model.Product;
import com.example.fyp.Model.Storage;
import com.example.fyp.Repository.ProductRepository;
import com.example.fyp.Repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    ProductRepository ProductRepo;
    @Autowired
    StorageRepository StorageRepo;

    @GetMapping("products")
    public List<Product> getAllProducts() {
        return ProductRepo.findAll();
    }

    @PostMapping("createProduct")
    public Product createProduct(@RequestBody Map<String, String> payload) {
        String productsQuantity="0";
        StorageRepo.save(new Storage(payload.get("productsCategory"),payload.get("productsId"),payload.get("productsName"),productsQuantity));
       return ProductRepo.save(new Product(payload.get("productsCategory"),payload.get("productsId"),payload.get("productsName"),payload.get("productsDescription"),payload.get("productsPrice"),payload.get("productsImage")));

    }
    @PostMapping("getAllProductByCategory")
    public List<Product> getAllProductByCategory(@RequestBody Map<String, String> payload){
        List<Product> products = ProductRepo.findAllByProductsCategory(payload.get("productsCategory"));
        return products;
    }

    @PostMapping("deleteProduct")
    public Product deleteProduct(@RequestBody Map<String, String> payload) {
        return ProductRepo.findByProductsId(payload.get("productsId")).map(product -> {
            ProductRepo.delete(product);
            return product;
        }).orElseThrow(() -> new NullPointerException("Unable to update empty record"));
    }

    @PostMapping("productFilter")
    public List<Product> getAllProductByFilter(@RequestBody Map<String, String> payload){
        return ProductRepo.findAllByProductsCategory(payload.get("productsCategory"));
    }
}
