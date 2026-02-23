package com.example.Ecomerce.product.infraestructure.database.repository;

import com.example.Ecomerce.product.dominio.Product;
import com.example.Ecomerce.product.dominio.port.ProductRepository;
import com.example.Ecomerce.product.infraestructure.database.entity.ProductoEntity;
import com.example.Ecomerce.product.infraestructure.database.entity.QueryProductRepo;
import com.example.Ecomerce.product.infraestructure.database.mapper.ProductEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepoImplement implements ProductRepository {

    private final QueryProductRepo queryProductRepo;
    private final ProductEntityMapper productEntityMapper;

    @Override
    public Product upsert(Product product) {
        ProductoEntity entity = productEntityMapper.mapToEntity(product);
        ProductoEntity saved = queryProductRepo.save(entity);
        return productEntityMapper.mapToProduct(saved);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return queryProductRepo.findById(id).map(productEntityMapper::mapToProduct);
    }
}
