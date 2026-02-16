package com.example.Ecomerce.users.infraestructure.password;

import com.example.Ecomerce.users.dominio.port.PasswordEncodertPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordEncoderImple implements PasswordEncodertPort {
   private  final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
