package com.example.Ecomerce.users.application.command.login;

import com.example.Ecomerce.common.application.RequestHandler;

import com.example.Ecomerce.users.dominio.port.AuthentificationPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LoginUserHandler implements RequestHandler<LoginUserRequest, LoginUserResponse> {

    private  final AuthentificationPort authentificationPort;

    @Override
    public LoginUserResponse handle(LoginUserRequest request) {

        String token =authentificationPort.authentication(request.getEmail(), request.getPassword());

        return new LoginUserResponse(token);
    }

    @Override
    public Class<LoginUserRequest> getRequestType() {
        return LoginUserRequest.class;
    }
}
