package com.example.Ecomerce.product.dominio;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class Product {
    private Long id;
    private String nombre;
    private String descripcion;
    private Integer stock;
    private boolean active;
    private BigDecimal price;
    private List<ProductImage> images;
}
