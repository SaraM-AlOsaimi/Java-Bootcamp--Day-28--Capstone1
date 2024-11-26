package com.example.capstone1amazonclone.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    @NotEmpty(message = "id is empty")
    private String id;

    @NotEmpty(message = "Name is empty")
    @Size(min = 4 , message = "name have to be more than 3 characters long")
    private String name;

    @NotNull(message = "Price is null")
    @Positive(message = "Price must be positive number")
    private Integer price;

    @NotEmpty(message = "category id is empty")
    private String categoryID;

    private int ratingCount; // Number of ratings received
    private double totalRatingScore;  // Sum of all ratings
    private double averageRating = 0.0;  // Default value is 0.0, you can calculate and update this value



}
