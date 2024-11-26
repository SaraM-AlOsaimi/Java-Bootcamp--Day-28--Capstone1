package com.example.capstone1amazonclone.Service;

import com.example.capstone1amazonclone.Model.Merchant;
import com.example.capstone1amazonclone.Model.MerchantStock;
import com.example.capstone1amazonclone.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;



import java.util.ArrayList;

@Service
public class MerchantService {

    private final ProductService productService;
    private final MerchantStockService merchantStockService;
    ArrayList<Merchant> merchants = new ArrayList<>();

    @Autowired
    public MerchantService(@Lazy ProductService productService, @Lazy MerchantStockService merchantStockService) {
        this.productService = productService;
        this.merchantStockService = merchantStockService;
    }

    //gets all Merchants
    public ArrayList<Merchant> getMerchants(){
        return merchants;
    }

    //add new merchants
    public void addMerchants(Merchant merchant){
        merchants.add(merchant);
    }

    //Update an existing merchant info
    public boolean updateMerchants(String id, Merchant merchant){
        for (int i = 0; i < merchants.size(); i++) {
            if(merchants.get(i).getId().equals(id)){ //  ensure that merchant is exist
                merchants.set(i, merchant); // update merchant info
                return true; // if it's exist and update his info return true , else return false
            }
        }
        return false;
    }

    // delete an existing merchant
    public boolean deleteMerchants(String id){
        for (int i = 0; i < merchants.size(); i++) {
           if(merchants.get(i).getId().equals(id)){ //  ensure that merchant is exist
               merchants.remove(i); // delete merchant
               return true;
           }
        }
        return false;
    }



    // 3- Third extra endpoint
    // This method allows a merchant to apply a discount on a specific product.
    public Integer applyDiscount(String merchantId, String productId, Integer discountAmount) {
        for (Merchant merchant : merchants) {
            if (merchant.getId().equals(merchantId)) {  // check if the merchant exists
                for (Product product : productService.getProducts()) {
                    if (product.getId().equals(productId)) {   // Check if the product exists
                        // Ensure that the product exists in stock
                        for (MerchantStock merchantStock : merchantStockService.getMerchantStocks()) {
                            if (merchantStock.getProductId().equals(productId) && merchantStock.getStock() > 0) {
                                // Check for valid discount amount
                                if (discountAmount > 0 && discountAmount <= product.getPrice()) {
                                    // Apply the discount to the product price
                                    product.setPrice(product.getPrice() - discountAmount);
                                    return 1; // Successfully applied discount
                                }
                                    return 5; // Invalid discount amount
                            }
                        }
                        return 2; // Product not in stock or stock is invalid
                    }
                }
                return 3; // Product not found
            }
        }
        return 4; // Merchant not found
    }





}
