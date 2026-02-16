package com.example.Ecomerce.users.application.command.login;

import com.example.Ecomerce.common.application.Request;
import lombok.Data;

@Data
public class LoginUserRequest implements Request<LoginUserResponse> {

    private String email;
    private  String password;


}
