package com.example.Ecomerce.users.application.command.register;

import com.example.Ecomerce.common.application.Request;
import lombok.Data;

@Data
public class RegisterUserRequest  implements Request<RegisterUserResponse> {

    private String email;
    private  String password;
    private  String firstname;
    private  String lastname;
}
