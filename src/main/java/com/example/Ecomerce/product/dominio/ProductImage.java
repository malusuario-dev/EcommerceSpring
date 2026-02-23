package com.example.Ecomerce.product.dominio;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductImage {
    private Long id;
    private String url;
}
