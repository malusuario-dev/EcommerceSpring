package com.example.Ecomerce.product.infraestructure.database.mapper;

import com.example.Ecomerce.product.dominio.Product;
import com.example.Ecomerce.product.dominio.ProductImage;
import com.example.Ecomerce.product.infraestructure.database.entity.ProductImageEntity;
import com.example.Ecomerce.product.infraestructure.database.entity.ProductoEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ProductEntityMapper {

    public ProductoEntity mapToEntity(Product product) {
        ProductoEntity entity = new ProductoEntity();
        entity.setId(product.getId());
        entity.setNombre(product.getNombre());
        entity.setDescripcion(product.getDescripcion());
        entity.setStock(product.getStock());
        entity.setActive(product.isActive());
        entity.setPrice(product.getPrice());

        entity.clearImages();
        if (product.getImages() != null) {
            for (ProductImage image : product.getImages()) {
                ProductImageEntity imageEntity = new ProductImageEntity();
                imageEntity.setId(image.getId());
                imageEntity.setUrl(image.getUrl());
                entity.addImage(imageEntity);
            }
        }
        return entity;
    }

    public Product mapToProduct(ProductoEntity entity) {
        List<ProductImage> images;
        if (entity.getImages() == null) {
            images = Collections.emptyList();
        } else {
            images = new ArrayList<>();
            for (ProductImageEntity imageEntity : entity.getImages()) {
                images.add(ProductImage.builder()
                        .id(imageEntity.getId())
                        .url(imageEntity.getUrl())
                        .build());
            }
        }

        return Product.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .stock(entity.getStock())
                .active(entity.isActive())
                .price(entity.getPrice())
                .images(images)
                .build();
    }
}
