package com.example.capstone1amazonclone.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

    @NotEmpty(message = "id is empty")
    private String id;

    @NotEmpty(message = "Name is empty")
    @Size(min = 4 , message = "name have to be more than 3 characters long")
    private String name;


}
