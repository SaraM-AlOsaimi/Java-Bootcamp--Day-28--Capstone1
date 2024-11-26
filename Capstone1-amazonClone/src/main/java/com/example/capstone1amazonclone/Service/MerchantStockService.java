package com.example.capstone1amazonclone.Service;

import com.example.capstone1amazonclone.Model.Merchant;
import com.example.capstone1amazonclone.Model.MerchantStock;
import com.example.capstone1amazonclone.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    // get all merchantStocks
    public ArrayList<MerchantStock> getMerchantStocks(){
        return merchantStocks;
    }

    private final ProductService productService;
    private final MerchantService merchantService;

    // add new merchantStocks
    public Integer addMerchantStocks(MerchantStock merchantStock){
        for (Product product : productService.getProducts()){
            if(product.getId().equals(merchantStock.getProductId())){  // check if the product is exist
                for (Merchant merchant : merchantService.getMerchants()){
                    if(merchant.getId().equals(merchantStock.getMerchantId())){  // check if the merchant is exist
                        merchantStocks.add(merchantStock);
                        return 1; // the product and merchant both exist, stock added successfully
                    }
                }
                return 2; // merchant not found
            }
        }
        return 3; // product not found
    }

    // update merchantStocks
    public boolean updateMerchantStocks(String id, MerchantStock merchantStock){
        for (int i = 0; i < merchantStocks.size(); i++) {
            if(merchantStocks.get(i).getId().equals(id)){
                merchantStocks.set(i, merchantStock);
                return true;
            }
        }
        return false;
    }

    // delete merchantStocks
    public boolean deleteMerchantStocks(String id){
        for (int i = 0; i < merchantStocks.size(); i++) {
            if(merchantStocks.get(i).getId().equals(id)){
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }



    // endpoint where merchant can add more stocks of product to a merchant Stock
    // this endpoint should accept a product id and merchant id and the amount of additional stock.
    public Integer addStockOfProductToMerchantStock(String productId , String merchantId , Integer amountOfAdditionalStock){
        for (Product product : productService.getProducts()){
            if(product.getId().equals(productId)){
                for (Merchant merchant :merchantService.getMerchants()){
                    if(merchant.getId().equals(merchantId)){
                       for (MerchantStock merchantStock : merchantStocks){
                           merchantStock.setStock(merchantStock.getStock() + amountOfAdditionalStock);
                           return 1;
                       }
                    }
                }
                return 2; // merchant not found
            }
        }
        return 3; // product not found
    }



}

