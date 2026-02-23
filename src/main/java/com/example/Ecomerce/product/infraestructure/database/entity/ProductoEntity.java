package com.example.Ecomerce.product.infraestructure.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "products")
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Integer stock;
    private boolean active;
    private BigDecimal price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImageEntity> images = new ArrayList<>();

    public void addImage(ProductImageEntity image) {
        images.add(image);
        image.setProduct(this);
    }

    public void clearImages() {
        for (ProductImageEntity image : images) {
            image.setProduct(null);
        }
        images.clear();
    }
}
