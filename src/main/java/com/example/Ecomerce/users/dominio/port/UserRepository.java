package com.example.Ecomerce.users.dominio.port;

import com.example.Ecomerce.users.dominio.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    boolean existByEmail ( String email);

    User upsert(User user);
}
