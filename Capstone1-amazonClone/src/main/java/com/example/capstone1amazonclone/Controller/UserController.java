package com.example.capstone1amazonclone.Controller;

import com.example.capstone1amazonclone.ApiResponse.ApiResponse;
import com.example.capstone1amazonclone.Model.User;
import com.example.capstone1amazonclone.Service.UserService;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.status(200).body(userService.getUsers());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id , @RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = userService.updateUser(id,user);
        if (isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("User Updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("ID not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        boolean isDeleted = userService.deleteUser(id);
        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("User Deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("ID not found"));
    }


    @PutMapping("/buy/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> buyProduct(@PathVariable String userId ,@PathVariable String productId ,@PathVariable String merchantId){
        Integer isHeBuy = userService.buyProduct(userId , productId,merchantId);
        if(isHeBuy == 1){
            return ResponseEntity.status(200).body(new ApiResponse("Purchase successful!"));
        }
        if(isHeBuy == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Unable to complete the purchase due to insufficient balance"));
        }
        if(isHeBuy == 3){
            return ResponseEntity.status(400).body(new ApiResponse("Product is out of stock"));
        }
        if(isHeBuy == 4){
            return ResponseEntity.status(400).body(new ApiResponse("merchants not found"));
        }
        if(isHeBuy == 5){
            return ResponseEntity.status(400).body(new ApiResponse("product not found"));
        }
            return ResponseEntity.status(400).body(new ApiResponse("user not found"));
    }


    // ------------------------------

    // Extra endpoints
    // 1- First extra endpoint //Get
    @GetMapping("/get/product/by/{categoryId}/{minPrice}/{maxPrice}")
    public ResponseEntity<?> getProductsByCategoryAndPriceRange(@PathVariable String categoryId,@PathVariable Double minPrice,@PathVariable Double maxPrice){
        ArrayList productsByCategory = userService.getProductsByCategoryAndPriceRange(categoryId,minPrice,maxPrice);
        if(productsByCategory == null){
            return ResponseEntity.status(400).body(new ApiResponse("No products in the given category with the specified price range"));
        }
        return ResponseEntity.status(200).body(productsByCategory);
    }


    // 2- Second extra endpoint //Put
    @PutMapping("/rating/{userId}/{productId}/{ratingScale}")
    public ResponseEntity<?> ratingProduct(@PathVariable String userId,@PathVariable String productId,@PathVariable Integer ratingScale){
        if (ratingScale < 1 || ratingScale > 4) {
            return ResponseEntity.status(400).body(new ApiResponse("Rating scale must be between 1 and 4, where 1 = Very Good, 2 = Good, 3 = Average, 4 = Bad"));
        }
        String rating = userService.ratingProduct(userId,productId,ratingScale);
        if(rating.equals("A")){
            return ResponseEntity.status(200).body(new ApiResponse("Thank you for your rating!"));
        }
        if(rating.equals("B")){
            return ResponseEntity.status(400).body(new ApiResponse("Product you trying to rate not found"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User not found or not registered"));
    }

    // 4 - Forth Extra endpoint // Put
    @PutMapping("/return/{userid}/{productid}/{merchantid}/{paymentdate}")
    public ResponseEntity<?> returnProduct(@PathVariable String userid, @PathVariable String productid, @PathVariable String merchantid,@PathVariable LocalDate paymentdate){
       Integer returned = userService.returnProduct(userid,productid,merchantid,paymentdate);
       if(returned == 1){
           return ResponseEntity.status(200).body(new ApiResponse("Return process done successfully"));
       } else if(returned == 2){
           return ResponseEntity.status(400).body(new ApiResponse("Return period has passed"));
       } else if(returned == 3){
           return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
       } else if(returned == 4){
           return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
       }
       return ResponseEntity.status(400).body(new ApiResponse("User not found"));
    }

    // 5 - Fifth Extra endpoint
    @GetMapping("/get/merchant/{userid}")
    public ResponseEntity<?> getAllMerchants(@PathVariable String userid){
        ArrayList getMerchants = userService.getAllMerchants(userid);
        if(getMerchants == null){
            return ResponseEntity.status(400).body(new ApiResponse("User not found or insufficient permissions to view merchants"));
        }
        return ResponseEntity.status(200).body(getMerchants);
    }


    // -------------------
    // Extra credit endpoint // Get
    @GetMapping("/best/seller")
    public ResponseEntity<?> getBestSellers(){
        ArrayList bestSeller = userService.getBestSellers();
        if(bestSeller == null){
            return ResponseEntity.status(400).body(new ApiResponse("Not Products"));
        }
        return ResponseEntity.status(200).body(bestSeller);
    }


}
