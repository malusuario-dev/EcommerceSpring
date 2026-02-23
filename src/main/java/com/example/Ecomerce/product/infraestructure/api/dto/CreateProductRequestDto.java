package com.example.Ecomerce.product.infraestructure.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateProductRequestDto {
    private String nombre;
    private String descripcion;
    private Integer stock;
    private BigDecimal price;
    private List<String> imageUrls;
}
