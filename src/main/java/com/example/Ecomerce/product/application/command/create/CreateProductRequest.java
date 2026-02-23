package com.example.Ecomerce.product.application.command.create;

import com.example.Ecomerce.common.application.Request;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateProductRequest implements Request<CreateProductResponse> {
    private String nombre;
    private String descripcion;
    private Integer stock;
    private BigDecimal price;
    private List<String> imageUrls;
}
