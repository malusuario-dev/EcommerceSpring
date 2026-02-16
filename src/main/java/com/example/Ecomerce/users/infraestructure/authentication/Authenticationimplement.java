package com.example.Ecomerce.users.infraestructure.authentication;

import com.example.Ecomerce.common.services.JwtServices;
import com.example.Ecomerce.users.dominio.port.AuthentificationPort;
import com.example.Ecomerce.users.infraestructure.database.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Authenticationimplement implements AuthentificationPort {
     private final JwtServices jwtServie;

     private  final AuthenticationManager authenticationManage;
    @Override
    public String authentication(String email, String password) {
        Authentication authenticate = authenticationManage.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                ));

        UserEntity entity = (UserEntity) authenticate.getPrincipal();

        return jwtServie.generateToken(entity);
    }
}
