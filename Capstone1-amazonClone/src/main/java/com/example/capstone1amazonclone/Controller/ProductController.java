package com.example.capstone1amazonclone.Controller;

import com.example.capstone1amazonclone.ApiResponse.ApiResponse;
import com.example.capstone1amazonclone.Model.Product;
import com.example.capstone1amazonclone.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity<?> getProducts(){
        return ResponseEntity.status(200).body(productService.getProducts());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody @Valid Product product , Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isAdded = productService.addProduct(product);
        if (isAdded){
            return ResponseEntity.status(200).body(new ApiResponse("Product added Successfully"));
        }
      return ResponseEntity.status(400).body(new ApiResponse("Category not found"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id , @RequestBody @Valid Product product , Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = productService.updateProduct(id,product);
        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("Product updated Successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("ID not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){
        boolean isDeleted = productService.deleteProduct(id);
        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Product Deleted Successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("ID not found"));
    }




}
