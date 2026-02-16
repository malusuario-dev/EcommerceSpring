package com.example.Ecomerce.users.infraestructure.api.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginRequestDto {
    @Email
    String email;
    String password;
}
