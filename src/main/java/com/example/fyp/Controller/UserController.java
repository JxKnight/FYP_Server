package com.example.fyp.Controller;

import com.example.fyp.Model.Buyer;
import com.example.fyp.Model.Orders;
import com.example.fyp.Model.User;
import com.example.fyp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository UserRepo;

    @GetMapping("users")
    public List<User> getAllUser() {
        return UserRepo.findAll();
    }

    @PostMapping("registerUser")
    public User createUser(@RequestBody Map<String, String> payload) {
        String firstEntry = "true";
       return UserRepo.save(new User(payload.get("userPassword"),payload.get("userIc"), payload.get("userContact"),firstEntry));
    }


    @PostMapping("updateUser")
    public User updateUser(@RequestBody Map<String, String> payload) {
        return UserRepo.findByUserIc(payload.get("userIc")).map(user -> {
            user.setUserPic(payload.get("userPic"));
            user.setFirstName(payload.get("firstName"));
            user.setLastName(payload.get("lastName"));
            user.setUserContact(payload.get("userContact"));
            user.setUserAddress(payload.get("userAddress"));
            user.setUserPostCode(payload.get("userPostCode"));
            user.setUserState(payload.get("userState"));
            UserRepo.save(user);
            return user;
        }).orElseThrow(() -> new NullPointerException("Unable to update empty record"));
    }


    @PostMapping("searchUser")//login
    public User searchUser(@RequestBody Map<String, String> payload) {
        Optional<User> userOptional = UserRepo.findByUserIcAndUserPassword(payload.get("userIc"), payload.get("userPassword"));
        return userOptional.orElseThrow(() -> new NullPointerException("No Record Found"));
    }


    @PostMapping("currentUser")
    public User searchCurrentUser(@RequestBody Map<String, String> payload) {
        Optional<User> userOptional = UserRepo.findByUserIc(payload.get("userIc"));
        return userOptional.orElseThrow(() ->  new NullPointerException("No record "));
    }

    @GetMapping("Employees")
    public List<User> Employees (){
        List<User> employeeList = new ArrayList<>();
        List<User> list = getAllUser();
        for(User currentUser: list){
            if(currentUser.getUserRole().equals("null")) {
                //employeeList.add(currentUser);
            }else{
                employeeList.add(currentUser);
            }
        }
        return employeeList;
    }

    @GetMapping("Users")
    public List<User> Users() {
        List<User> usersList = new ArrayList<>();
        List<User> list = getAllUser();
        for(User currentUser: list){
            if(currentUser.getUserRole().equals("null")){
                usersList.add(currentUser);
            }
        }
        return usersList;
    }

    @PostMapping("updateUserRole")
    public User updateUserRole(@RequestBody Map<String, String> payload) {
        return UserRepo.findByUserIc(payload.get("userIc")).map(user -> {
            user.setUserRole(payload.get("userRole"));
            UserRepo.save(user);
            return user;
        }).orElseThrow(() -> new NullPointerException("Unable to update empty record"));
    }

    @PostMapping("deleteUser")
    public User deleteUser(@RequestBody Map<String, String> payload) {
        return UserRepo.findByUserIc(payload.get("userIc")).map(user -> {
            UserRepo.delete(user);
            return user;
        }).orElseThrow(() -> new NullPointerException("Unable to update empty record"));
    }
}

