package com.example.Ecomerce.users.infraestructure.api.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {

    private String email;
    private  String password;
    private  String firstname;
    private  String lastname;}
