package com.example.capstone1amazonclone.Controller;

import com.example.capstone1amazonclone.ApiResponse.ApiResponse;
import com.example.capstone1amazonclone.Model.Merchant;
import com.example.capstone1amazonclone.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchants(){
        return ResponseEntity.status(200).body(merchantService.getMerchants());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchants(@RequestBody @Valid Merchant merchant, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        merchantService.addMerchants(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("Merchant added"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id,@RequestBody @Valid Merchant merchant ,Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = merchantService.updateMerchants(id, merchant);
        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("Merchants updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("ID not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id){
        boolean isDeleted = merchantService.deleteMerchants(id);
        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("ID not found"));
    }

    // 3- Third Extra endpoint // Put
    @PutMapping("/apply-discount/{merchantId}/{productId}/{discountAmount}")
    public ResponseEntity<?> applyDiscount(@PathVariable String merchantId, @PathVariable String productId, @PathVariable Integer discountAmount) {
        Integer result = merchantService.applyDiscount(merchantId, productId, discountAmount);

        if (result == 1) {
            return ResponseEntity.status(200).body(new ApiResponse("Discount applied successfully"));
        } else if (result == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("Product not in stock or invalid stock"));
        } else if (result == 3) {
            return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
        } else if (result == 4) {
            return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
        } else
            return ResponseEntity.status(400).body(new ApiResponse("Invalid discount amount"));
    }

}
