package com.example.fyp.Controller;

import com.example.fyp.Model.CalculateOrders;
import com.example.fyp.Model.Orders;
import com.example.fyp.Model.Storage;
import com.example.fyp.Repository.OrdersRepository;
import com.example.fyp.Repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class OrdersController {

    Calendar calendar;
    SimpleDateFormat dateFormat;
    String date;

    @Autowired
    OrdersRepository OrdersRepo;
    @Autowired
    StorageRepository storageRepo;

    @GetMapping("orders")
    public List<Orders> getAllOrders() {
        return OrdersRepo.findAll();
    }

    @PostMapping("createOrder")
    public Orders createOrder(@RequestBody Map<String, String> payload) {
        Integer count = 0;
        List<Orders> list = getAllOrders();
        for (Orders countlist : list) {
            count++;
        }
        dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        date = dateFormat.format(calendar.getTime());
        String ordersId = "order" + count;
        return OrdersRepo.save(new Orders(ordersId, payload.get("ordersDescription"), date, payload.get("buyerId"), payload.get("productsId"), payload.get("productsQuantity"), payload.get("userIc"), payload.get("ordersStatus")));
    }

    @PostMapping("buyerHistoryList")
    public List<Orders> getBuyerHistoryList(@RequestBody Map<String, String> payload) {
        List<Orders> orders = OrdersRepo.findAllByBuyerId(payload.get("buyerId"));
        return orders;
    }

    @GetMapping("ordersByStatus")
    public List<Orders> orderStatusFalse(@RequestParam(name = "ordersStatus") String selectStatus) {
        List<Orders> orders = OrdersRepo.findAllByOrdersStatus(selectStatus);
        return orders;
    }

    @GetMapping("calculationOrders")
    public List<CalculateOrders> calculationOrders() {
        String x = "true";
        String[] products = new String[0], quantity = new String[0];
        List<String> productID = new ArrayList<>();
        List<CalculateOrders> allNewOrders = new ArrayList<>();
        List<String> newProductID = new ArrayList<>();
        List<CalculateOrders> TotalNewOrders = new ArrayList<>();
        List<CalculateOrders> finalResult = new ArrayList<>();
        List<Orders> orders = OrdersRepo.findAllByOrdersStatus(x);
        for (Orders var : orders) {
            products = var.getProductsId().split("/");
            quantity = var.getProductsQuantity().split("/");
            for (int i = 0; i < products.length; i++) {
                CalculateOrders all = new CalculateOrders(products[i], quantity[i]);
                allNewOrders.add(all);
            }
        }
        for (CalculateOrders current : allNewOrders) {
            productID.add(current.getProductID());
        }
        LinkedHashSet<String> hashSet = new LinkedHashSet<>(productID);
        newProductID = new ArrayList<>(hashSet);

        for (String var : newProductID) {
            Integer totalQuantity = 0;
            for (CalculateOrders var1 : allNewOrders) {
                if (var.equals(var1.getProductID())) {
                    totalQuantity = totalQuantity + Integer.parseInt(var1.getQuantity());
                }
            }
            CalculateOrders calculateOrders = new CalculateOrders(var, String.valueOf(totalQuantity));
            TotalNewOrders.add(calculateOrders);
        }
        for (CalculateOrders calculateOrders : TotalNewOrders) {
            String xx = String.valueOf(calculate(calculateOrders.getProductID(), calculateOrders.getQuantity()));
            CalculateOrders calculateOrders1 = new CalculateOrders(calculateOrders.getProductID(),xx);
            finalResult.add(calculateOrders1);
        }

        return finalResult;
    }
    public Integer calculate(String x, String y) {
        Optional<Storage> call = storageRepo.findByProductsId(x);
        Integer diff =Integer.parseInt(call.get().getProductsQuantity()) - Integer.parseInt(y);
        return diff;
    }


    @GetMapping("compare")
    public String compare(@RequestParam("x") String x, @RequestParam("y")String y) {
        //Optional<Storage> call = storageRepo.findByProductsId(x);
        Integer diff =Integer.parseInt(x) - Integer.parseInt(y);
        Integer diFF;
        String finalX="";
        if(diff<0){
            diFF = Math.abs(diff);
            ArrayList<Integer> array = new ArrayList<Integer>();
            do{
                array.add(diFF % 10);
                diFF /= 10;
            } while  (diFF > 0);
            for(int i=0;i<(array.size()-1);i++){
                if(array.get(i)>=5){
                    array.set(i+1,array.get(i+1)+1);
                    array.set(i,0);
                }
            }
            for(int i=array.size()-1;i>=0;i--){
                finalX = finalX + array.get(i);
            }
        }else{

        }
        return finalX;
    }
}
