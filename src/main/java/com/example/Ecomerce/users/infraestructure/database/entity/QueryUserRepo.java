package com.example.Ecomerce.users.infraestructure.database.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface QueryUserRepo extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByemail(String email);
}
