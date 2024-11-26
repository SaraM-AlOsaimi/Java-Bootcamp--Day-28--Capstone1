package com.example.capstone1amazonclone.Controller;

import com.example.capstone1amazonclone.ApiResponse.ApiResponse;
import com.example.capstone1amazonclone.Model.MerchantStock;
import com.example.capstone1amazonclone.Service.MerchantStockService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant-stocks")
@RequiredArgsConstructor
public class MerchantStocksController {

    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchantStocks(){
        return ResponseEntity.status(200).body(merchantStockService.getMerchantStocks());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchantStocks(@RequestBody @Valid MerchantStock merchantStock , Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        Integer addMerchantStocks =  merchantStockService.addMerchantStocks(merchantStock);
        if(addMerchantStocks == 1){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Stocks added"));
        }
        if (addMerchantStocks == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Merchant not found "));
        }
        return ResponseEntity.status(400).body(new ApiResponse("product not found"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchantStocks(@PathVariable String id , @RequestBody @Valid MerchantStock merchantStock,Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = merchantStockService.updateMerchantStocks(id,merchantStock);
        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("MerchantStocks Updated "));
        }
        return ResponseEntity.status(400).body(new ApiResponse("ID not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchantStocks(@PathVariable String id){
        boolean isDeleted = merchantStockService.deleteMerchantStocks(id);
        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("MerchantStocks Deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("ID not found"));
    }



    @PutMapping("/added/stock/{productId}/{merchantId}/{amountOfAdditionalStock}")
    //String productId , String merchantId , Integer amountOfAdditionalStock
    public ResponseEntity<?> addStockOfProductToMerchantStock(@PathVariable String productId , @PathVariable String merchantId , @PathVariable @Positive Integer amountOfAdditionalStock){
        Integer addedStock = merchantStockService.addStockOfProductToMerchantStock(productId , merchantId,amountOfAdditionalStock);
        if(addedStock == 1){
            return ResponseEntity.status(200).body(new ApiResponse("Stock amount updated"));
        }
        if(addedStock == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
    }



}
