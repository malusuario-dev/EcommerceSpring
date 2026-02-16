package com.example.Ecomerce.users.application.command.register;

import com.example.Ecomerce.common.application.RequestHandler;
import com.example.Ecomerce.users.dominio.Role;
import com.example.Ecomerce.users.dominio.User;
import com.example.Ecomerce.users.dominio.port.AuthentificationPort;
import com.example.Ecomerce.users.dominio.port.PasswordEncodertPort;
import com.example.Ecomerce.users.dominio.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegisterUserHandler implements RequestHandler<RegisterUserRequest,RegisterUserResponse> {

    private  final UserRepository userRepository;
    private  final PasswordEncodertPort passwordEncoder;
    private  final AuthentificationPort authentificationPort;
    @Override
    public RegisterUserResponse handle(RegisterUserRequest request) {
        boolean existByEmail = userRepository.existByEmail(request.getEmail());
        if (existByEmail){
            throw  new RuntimeException("email ya existe");
        }

        String encode = passwordEncoder.encode(request.getPassword());
        User user = User.builder()
                .email(request.getEmail()).
                password(encode).
                firtsname(request.getFirstname()).
                lastname(request.getLastname())
                .enable(true)
                .creatAt(LocalDateTime.now())
                .role(Role.USER)
                .build();
           userRepository.upsert(user);
        String token = authentificationPort.authentication(request.getEmail(), request.getPassword());

        return new RegisterUserResponse(token);
    }

    @Override
    public Class<RegisterUserRequest> getRequestType() {
        return RegisterUserRequest.class;
    }
}
