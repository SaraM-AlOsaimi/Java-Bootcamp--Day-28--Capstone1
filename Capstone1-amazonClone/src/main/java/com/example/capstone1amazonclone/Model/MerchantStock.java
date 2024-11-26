package com.example.capstone1amazonclone.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty(message = "id is empty")
    private String id;

    @NotEmpty(message = "product id is empty")
    private String productId;

    @NotEmpty(message = "merchant id is empty")
    private String merchantId;

    @NotNull(message = "Stock is empty")
    @Min(value = 11 , message = "Stock have to be more than 10 at start")
    private Integer stock;

}
