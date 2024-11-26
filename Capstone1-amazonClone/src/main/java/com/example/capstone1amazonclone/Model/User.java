package com.example.capstone1amazonclone.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "id is empty")
    private String id;

    @NotEmpty(message = "username is empty")
    @Size(min = 6, message = "username have to be 6 or more length characters long")
    private String username;

    //password (must not be empty, have to be more than 6 length long, must have
    //characters and digits).
    @NotEmpty(message = "Password is empty")
    @Pattern(regexp = "^(?=.*[a-zA-z])(?=.*\\d).{7,}$", message = "Password have to be 7 or more long, must have characters and digits")
    private String password;

    @NotBlank(message = "email is empty")
    @Email(message = "Enter a valid email format")
    private String email;

    @NotEmpty(message = "role is empty")
    @Pattern(regexp = "^Admin|Customer$", message = "Role have to be in Admin or Customer")
    private String role;

    @NotNull(message = "Balance is null")
    @Positive(message = "Balance have to be positive")
    private Integer balance;

}
