package com.example.Ecomerce.users.dominio;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

@Data
@Builder
public class User  {

    Long id;
    String email;
    String password;
    String firtsname;
    String lastname;
     boolean enable;
     LocalDateTime creatAt;
     Role role;

}
