package com.example.Ecomerce.product.infraestructure.database.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryProductRepo extends JpaRepository<ProductoEntity, Long> {
}
