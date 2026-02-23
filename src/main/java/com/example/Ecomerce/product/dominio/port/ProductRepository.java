package com.example.Ecomerce.product.dominio.port;

import com.example.Ecomerce.product.dominio.Product;

import java.util.Optional;

public interface ProductRepository {
    Product upsert(Product product);
    Optional<Product> findById(Long id);
}
